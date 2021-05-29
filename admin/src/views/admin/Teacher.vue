<template>
  <div>
    <p>
      <button class="btn btn-white btn-default btn-round" @click="add()">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button class="btn btn-white btn-default btn-round" @click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <Pagination ref="pagination" v-bind:list="list" v-bind:itemCount="5"></Pagination>
    <div class="row">
      <div v-for="teacher in teachers" class="col-md-3">
        <div>
          <span class="profile-picture">
            <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/讲师头像/头像1.jpg" v-bind:title="teacher.intro"/>
            <img v-show="teacher.image" class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
          </span>

          <div class="space-4"></div>

          <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">{{teacher.position}}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="space-6"></div>

        <div class="text-center">
          <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
            <i class="ace-icon fa fa-user"></i>
            {{teacher.name}}【{{teacher.nickname}}】
          </a>
        </div>

        <div class="space-6"></div>
        <div class="profile-social-links align-center">
          <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          &nbsp;
          <button v-on:click="remove(teacher.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>

        <div class="hr hr16 dotted"></div>
      </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">课程</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-2 control-label">姓名</label>
                  <div class="col-sm-10">
                    <input v-model="teacher.name" type="text" class="form-control" placeholder="姓名">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">昵称</label>
                  <div class="col-sm-10">
                    <input v-model="teacher.nickname" type="text" class="form-control" placeholder="昵称">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">头像</label>
                  <div class="col-sm-10">
                    <input ref="fileInput" type="file" v-on:change="uploadImage()">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">职位</label>
                  <div class="col-sm-10">
                    <input v-model="teacher.position" type="text" class="form-control" placeholder="职位">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">座右铭</label>
                  <div class="col-sm-10">
                    <input v-model="teacher.motto" type="text" class="form-control" placeholder="座右铭">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">简介</label>
                  <div class="col-sm-10">
                    <input v-model="teacher.intro" type="text" class="form-control" placeholder="简介">
                  </div>
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="save()">保存</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Pagination from "../../components/Pagination";
  import {toast} from "../../utils/toast";
  import {Confirm} from "../../utils/confirm";
  import {Validator} from "../../utils/validator";

  export default {
    name: "Teacher",
    components:{
      Pagination,
    },
    data(){
      return {
        teacher:{},
        teachers:[],
      }
    },
    mounted() {
      let _this = this;
      _this.list(1);

    },
    methods:{
      list(page){
        let _this = this;
        Loading.show();
        _this.$api.post("/business/admin/teacher/list",
          {
            page:page,
            size:_this.$refs.pagination.size
          }
        ).then(resp =>{
          Loading.hide();
          this.teachers = resp.data.list;
          _this.$refs.pagination.render(page,resp.data.total);

        })
      },
      add(){
        let _this = this;
        _this.teacher = {};
        $('#myModal').modal('show');
      },
      edit(teacher){
        let _this = this;
        _this.teacher = Object.assign({},teacher);
        $('#myModal').modal('show');
      },
      save(){
        let _this = this;
        // 保存校验
        if (1 != 1
          || !Validator.require(_this.teacher.name, "姓名")
          || !Validator.length(_this.teacher.name, "姓名", 1, 50)
          || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
          || !Validator.length(_this.teacher.image, "头像", 1, 100)
          || !Validator.length(_this.teacher.position, "职位", 1, 50)
          || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
          || !Validator.length(_this.teacher.intro, "简介", 1, 500)
        ) {
          return;
        }

        Loading.show();
        _this.$api.post("/business/admin/teacher/save",
          _this.teacher,
        ).then(resp =>{
          Loading.hide();
          let response = resp.data;
          if(response.success){
            $('#myModal').modal('hide');
            _this.list(1);
            toast.success("保存成功");
          }else {
            toast.warning(response.msg);
          }
        })
      },
      remove(id){
        let _this = this;
        Confirm.show('删除后不可恢复，确认删除？',function () {
          Loading.show();
          _this.$api.delete("/business/admin/teacher/delete/"+id
          ).then(resp =>{
            Loading.hide();
            let response = resp.data;
            if(response.success){
              _this.list(1);
              toast.success("删除成功");
            }else{
              toast.warning(response.msg);
            }
          });
        })
      },
      uploadImage(){
        let _this = this;
        let file = _this.$refs.fileInput.files[0];
        let data = new FormData();
        data.append("file",file);
        Loading.show();
        _this.$api.post("/file/admin/upload",data,{
          headers:{
            'Content-Type': 'multipart/form-data'
          }
        }).then(response =>{
          Loading.hide();
          let resp = response.data;
          console.log(resp.content);
        })

      }

    }
  }
</script>

<style scoped>

</style>
