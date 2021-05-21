<template>
  <div>
    <p>
      <button class="btn btn-white btn-default btn-round" @click="showAdd()">
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
      <table id="simple-table" class="table  table-bordered table-hover">
        <thead>
        <tr>
          <th>ID</th>
          <th>课程ID</th>
          <th>名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(chapter,index) in chapters" :key="index">
          <td>{{chapter.id}}</td>
          <td class="hidden-480">{{chapter.courseId}}</td>
          <td>{{chapter.name}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">

              <button class="btn btn-xs btn-info" @click="showEdit(chapter)">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>

              <button class="btn btn-xs btn-danger" @click="remove(chapter.id)">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
<!--            <div class="hidden-md hidden-lg">-->
<!--              <div class="inline pos-rel">-->
<!--                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">-->
<!--                  <i class="ace-icon fa fa-cog icon-only bigger-110"></i>-->
<!--                </button>-->

<!--                <ul-->
<!--                  class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">-->
<!--                  <li>-->
<!--                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">-->
<!--																			<span class="blue">-->
<!--																				<i class="ace-icon fa fa-search-plus bigger-120"></i>-->
<!--																			</span>-->
<!--                    </a>-->
<!--                  </li>-->

<!--                  <li>-->
<!--                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">-->
<!--																			<span class="green">-->
<!--																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>-->
<!--																			</span>-->
<!--                    </a>-->
<!--                  </li>-->

<!--                  <li>-->
<!--                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">-->
<!--																			<span class="red">-->
<!--																				<i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--																			</span>-->
<!--                    </a>-->
<!--                  </li>-->
<!--                </ul>-->
<!--              </div>-->
<!--            </div>-->
          </td>
        </tr>

        <tr class="detail-row">
          <td colspan="8">
            <div class="table-detail">
              <div class="row">
                <div class="col-xs-12 col-sm-2">
                  <div class="text-center">
                    <img height="150" class="thumbnail inline no-margin-bottom" alt="Domain Owner's Avatar" src="assets/images/avatars/profile-pic.jpg" />
                    <br />
                    <div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
                      <div class="inline position-relative">
                        <a class="user-title-label" href="#">
                          <i class="ace-icon fa fa-circle light-green"></i>
                          &nbsp;
                          <span class="white">Alex M. Doe</span>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-xs-12 col-sm-7">
                  <div class="space visible-xs"></div>

                  <div class="profile-user-info profile-user-info-striped">
                    <div class="profile-info-row">
                      <div class="profile-info-name"> Username </div>

                      <div class="profile-info-value">
                        <span>alexdoe</span>
                      </div>
                    </div>

                    <div class="profile-info-row">
                      <div class="profile-info-name"> Location </div>

                      <div class="profile-info-value">
                        <i class="fa fa-map-marker light-orange bigger-110"></i>
                        <span>Netherlands, Amsterdam</span>
                      </div>
                    </div>

                    <div class="profile-info-row">
                      <div class="profile-info-name"> Age </div>

                      <div class="profile-info-value">
                        <span>38</span>
                      </div>
                    </div>

                    <div class="profile-info-row">
                      <div class="profile-info-name"> Joined </div>

                      <div class="profile-info-value">
                        <span>2010/06/20</span>
                      </div>
                    </div>

                    <div class="profile-info-row">
                      <div class="profile-info-name"> Last Online </div>

                      <div class="profile-info-value">
                        <span>3 hours ago</span>
                      </div>
                    </div>

                    <div class="profile-info-row">
                      <div class="profile-info-name"> About Me </div>

                      <div class="profile-info-value">
                        <span>Bio</span>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-xs-12 col-sm-3">
                  <div class="space visible-xs"></div>
                  <h4 class="header blue lighter less-margin">Send a message to Alex</h4>

                  <div class="space-6"></div>

                  <form>
                    <fieldset>
                      <textarea class="width-100" resize="none" placeholder="Type something…"></textarea>
                    </fieldset>

                    <div class="hr hr-dotted"></div>

                    <div class="clearfix">
                      <label class="pull-left">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"> Email me a copy</span>
                      </label>

                      <button class="pull-right btn btn-sm btn-primary btn-white btn-round" type="button">
                        Submit
                        <i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
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
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" type="text" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input v-model="chapter.courseId" type="text" class="form-control" placeholder="课程ID">
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
    name: "Chapter",
    components:{
      Pagination,
    },
    data(){
      return {
        chapter:{},
        chapters:[],
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
        _this.$api.post("/business/admin/chapter/list",
          {
            page:page,
            size:_this.$refs.pagination.size
          }
        ).then(resp =>{
          Loading.hide();
          this.chapters = resp.data.list;
          _this.$refs.pagination.render(page,resp.data.total);

        })
      },
      showAdd(){
        let _this = this;
        _this.chapter = {};
        $('#myModal').modal('show');
      },
      showEdit(chapter){
        let _this = this;
        _this.chapter = Object.assign({},chapter);
        $('#myModal').modal('show');
      },
      save(){
        let _this = this;
        if(!Validator.require(_this.chapter.name,'名称') ||
           !Validator.require(_this.chapter.name,'课程ID') ||
           !Validator.length(_this.chapter.name,'课程ID',1,8)){
              return ;
        }
        Loading.show();
        _this.$api.post("/business/admin/chapter/save",
          _this.chapter,
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
          _this.$api.delete("/business/admin/chapter/delete/"+id
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

    }
  }
</script>

<style scoped>

</style>
