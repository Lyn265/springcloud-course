<template>
  <div>
      <button hidden type="button" class="btn btn-white btn-default btn-round" @click="selectFile()">
        <i class="ace-icon fa fa-upload"></i>
        {{text}}
      </button>
      <input class="hidden" ref="fileInput" type="file"
             v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
  import {toast} from "../utils/toast";
  import {Tool} from "../utils/tool";

  export default {
    name: "BigFile",
      data(){
      return {

      }
    },
    props:{
      text:{
        default:"上传大文件",
        type:String,
      },
      inputId:{
        default:"file-upload",
        type:String,
      },
      suffixs:{
        default:() =>[],
        type:Array
      },
      use:{
        default:"",
      },
      afterUpload:{
        default: null,
        type:Function
      }
    },
    methods:{
       uploadFile(){
        let _this = this;
        let file = _this.$refs.fileInput.files[0];
         console.log(JSON.stringify(file));
         //检验图片格式
        let fileName = file.name;
        let suffixs = _this.suffixs;
        let suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        let validFile = false;
        for (let i = 0; i <suffixs.length ; i++) {
          if(suffixs[i].toLowerCase() === suffix){
            validFile = true;
            break;
          }
        }
        if(!validFile){
          toast.warning("图片格式不正确");
          $("#"+_this.inputId+'-input').val("");
          return;
        }
        //**分片上传**
        //通过文件原信息来获取唯一key
        let key = hex_md5(file.name + file.size + file.type);
        let key10 = parseInt(key,16);
        let key62 = Tool._10to62(key10);
        //设置分片文件大小
        let shardSize = 20 * 1024 * 1024;
        //分片索引
        let shardIndex = 1;//1表示第一个分片
        //文件大小
        let size = file.size;
        //分片总数
        let shardTotal = Math.ceil(size/shardSize);
        //全局参数对象
        let param = {
          "shardIndex":shardIndex,
          "shardTotal":shardTotal,
          "shardSize":shardSize,
          "name":fileName,
          "suffix":suffix,
          "size":size,
          "use":_this.use,
          "key":key62
        };
        //分片检查
         _this.checkShared(param);
      },
      upload: function (param) {
        let _this = this;
        let shardIndex = param.shardIndex;
        let shardTotal = param.shardTotal;
        let shardSize = param.shardSize;
        let fileShard = _this.getFileShard(shardIndex, shardSize);
        //第一次上传进度条应该显示是0
        Progress.show(parseInt((shardIndex-1)*100/shardTotal));
        let fileReader = new FileReader();
        //先监听事件，再读数据
        fileReader.onload = function(e){
          let base64 = e.target.result;
          param.shard = base64;
          _this.$api.post("/file/admin/upload", param).then(response => {
            let resp = response.data;
              Progress.show(parseInt(shardIndex * 100/ shardTotal));
            if (shardIndex < shardTotal) {
              param.shardIndex = param.shardIndex + 1;
              _this.upload(param);
            } else {
              Progress.hide();
              _this.afterUpload(resp);
            $("#" + _this.inputId + '-input').val("");
            }
          })
        };
        fileReader.readAsDataURL(fileShard);
      },
      getFileShard: function (shardIndex, shardSize) {
         let _this = this;
        //分片起始
        let file = _this.$refs.fileInput.files[0];
        let start = (shardIndex - 1) * shardSize;
        //分片结束 (当文件大小小于分片单位大小时)
        let end = Math.min(file.size, start + shardSize);
        let fileShard = file.slice(start, end);
        return fileShard;
      },
      selectFile(){
        let _this = this;
        $("#"+_this.inputId+'-input').trigger('click');
      },
      checkShared(param) {
        let _this = this;
        this.$api.get("/file/admin/check/" + param.key).then(response => {
          let resp = response.data;
          if (resp.success) {
            let obj = resp.content;
            if (!obj) {
              param.shardIndex = 1;
              console.log("从分片1开始上传");
              _this.upload(param);
            }else if(obj.shardIndex === obj.shardTotal){
              toast.success("急速秒传成功。");
              _this.afterUpload(resp);
              $("#" + _this.inputId + '-input').val("");
            }else {
              param.shardIndex = obj.shardIndex + 1;
              console.log("从分片" + param.shardIndex + "开始上传");
              _this.upload(param);
            }
          }else{
            toast.warning("文件上传失败");
            $("#" + _this.inputId + '-input').val("");
          }
        })
      }

    }
  }
</script>

<style scoped>

</style>
