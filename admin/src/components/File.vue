<template>
  <div>
      <button hidden type="button" class="btn btn-white btn-default btn-round" @click="selectFile()">
        <i class="ace-icon fa fa-upload"></i>
        {{text}}
      </button>
      <input class="hidden" ref="fileInput" type="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
  import {toast} from "../utils/toast";

  export default {
    name: "File",
      data(){
      return {

      }
    },
    props:{
      text:{
        default:"上传文件",
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
        let data = new FormData();
        data.append("file",file);
        data.append("use",_this.use);
        Loading.show();
        _this.$api.post("/file/admin/upload",data,{
          headers:{
            'Content-Type': 'multipart/form-data'
          }
        }).then(response =>{
          Loading.hide();
          let resp = response.data;
          _this.afterUpload(resp);
          $("#"+_this.inputId+'-input').val("");
        })
      },
      selectFile(){
        let _this = this;
        $("#"+_this.inputId+'-input').trigger('click');
      },
    }
  }
</script>

<style scoped>

</style>
