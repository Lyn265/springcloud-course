<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
    </h4>
    <hr>

    <File v-bind:input-id="'content-file-upload'"
          v-bind:text="'上传文件'"
          v-bind:suffixs="['jpg', 'jpeg', 'png', 'mp4']"
          v-bind:use="FILE_USE.COURSE.key"
          v-bind:after-upload="afterUploadContentFile"></File>
    <br>
    <table id="file-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>名称</th>
        <th>地址</th>
        <th>大小</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(f, i) in files" v-bind:key="f.id">
        <td>{{f.name}}</td>
        <td>{{f.url}}</td>
        <td>{{f.size | formatFileSize}}</td>
        <td>
          <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
            <i class="ace-icon fa fa-times red2"></i>
            删除
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <form class="form-horizontal">
      <div class="form-group">
        <div class="col-lg-12">
          {{saveContentLabel}}
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-12">
          <div id="content"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-12">
          {{saveContentLabel}}
        </div>
      </div>
    </form>
    <p>
      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">
        <i class="ace-icon fa fa-plus blue"></i>
        保存
      </button>&nbsp;
      <router-link to="/business/course" type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
        <i class="ace-icon fa fa-times"></i>
        返回课程
      </router-link>
    </p>
  </div>
</template>

<script>
  import {Confirm} from "../../utils/confirm";
  import {toast} from "../../utils/toast";
  import {Tool} from "../../utils/tool";
  import File from "../../components/File";

  export default {
    name: "Content",
    components:{File},
    data() {
      return {
        course: {},
        FILE_USE: FILE_USE,
        saveContentLabel: "",
        files: [],
        saveContentInterval: {},
      }
    },
    mounted() {
      let _this = this;
      let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
      if (Tool.isEmpty(course)) {
        _this.$router.push("/welcome");
      }
      _this.course = course;

      _this.init();
      // sidebar激活样式方法一
      this.$parent.activeSidebar("business-course-sidebar");

    },
    destroyed() {
      let _this = this;
      console.log("组件销毁");
      clearInterval(_this.saveContentInterval);
    },

    methods:{
      init(){
        let _this = this;
        let course = _this.course;
        let id = course.id;
        //初始化富文本编辑框
        $("#content").summernote({
          focus:true,
          height:300
        });
        //初始化文本内容
        $("#content").summernote('node','');
        _this.saveContentLabel = '';
        _this.getCourseContentFile(id);

        Loading.show();
        //获取当前课程id的富文本内容
        _this.$api.get("/business/admin/course/find-content/"+id).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            $('#course-content-modal').modal({backdrop:'static',keyboard:false});
            if(resp.content){
              $("#content").summernote('node',resp.content);
              //每5分钟执行一次保存
              let saveContentInterval =  setInterval(() =>{
                _this.saveContent();
              },5000);
              $('#course-content-modal').on('hidden.bs.modal',(e) =>{
                clearInterval(saveContentInterval);
              })
            }
          }else{
            toast.warning(resp.msg);
          }

        });
      },
      saveContent(){
        let _this = this;
        let content = $("#content").summernote("code");
        let id = _this.course.id;
        Loading.show();
        _this.$api.post("/business/admin/course/save-content",{
          id,
          content,
        }).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            // toast.success("内容保存成功.");
            _this.saveContentLabel = "最后保存时间: " +Tool.dateFormat('hh:mm:ss');
          }else{
            toast.warning(resp.msg);
          }
        })
      },
      delFile(file){
        let _this = this;
        Confirm.show('删除后不可恢复，确认删除？',function () {
          Loading.show();
          _this.$api.delete("/business/admin/course-content-file/delete/"+file.id
          ).then(response =>{
            Loading.hide();
            let resp = response.data;
            if(resp.success){
              toast.success("删除成功");
              Tool.removeObj(_this.files,file);
            }
          });
        })

      },
      getCourseContentFile(courseId){
        let _this = this;
        Loading.show();
        _this.$api.get("/business/admin/course-content-file/list/"+courseId,
        ).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            _this.files = resp.content;
          }
        })
      },
      /**
       * 通过文件上传组件获取到上传文件，客户端拿到数据后，在请求一次服务端，保存到course-content-file表里
       * @param response
       */
      afterUploadContentFile(response){
        let _this = this;
        let file = response.content;
        let contentFile = {
          courseId:_this.course.id,
          url:file.path,
          name:file.name,
          size:file.size
        };
        _this.$api.post("/business/admin/course-content-file/save",contentFile).then(response =>{
          let resp = response.data;
          if(resp.success){
            _this.files.push(resp.content);
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
