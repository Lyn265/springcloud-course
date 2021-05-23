<template>
  <div>
    <h3 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course"  class="pink">{{course.name}}</router-link>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter"  class="pink">{{chapter.name}}</router-link>
    </h3>
    <hr>
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
            <th>标题</th>
            <th>视频</th>
            <th>时长</th>
            <th>收费</th>
            <th>顺序</th>
            <th>vod</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <tr v-for="(section,index) in sections" :key="index">
          <td>{{section.id}}</td>
          <td>{{section.title}}</td>
          <td>{{section.video}}</td>
          <td>{{section.time | formatSecond}}</td>
          <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
          <td>{{section.sort}}</td>
          <td>{{section.vod}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">

              <button class="btn btn-xs btn-info" @click="showEdit(section)">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>

              <button class="btn btn-xs btn-danger" @click="remove(section.id)">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>
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
                  <label class="col-sm-2 control-label">标题</label>
                  <div class="col-sm-10">
                    <input v-model="section.title" type="text" class="form-control" placeholder="标题">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">课程</label>
                  <div class="col-sm-10">
                    <p class="form-control-static">{{course.name}}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">大章</label>
                  <div class="col-sm-10">
                    <p class="form-control-static">{{chapter.name}}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">视频</label>
                  <div class="col-sm-10">
                    <input v-model="section.video" type="text" class="form-control" placeholder="视频">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">时长</label>
                  <div class="col-sm-10">
                    <input v-model="section.time" type="text" class="form-control" placeholder="时长">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">收费</label>
                  <div class="col-sm-10">
                    <select v-model="section.charge" class="form-control">
                      <option v-for="s in SECTION_CHARGE" v-bind:value="s.key">{{s.value}}</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">顺序</label>
                  <div class="col-sm-10">
                    <input v-model="section.sort" type="text" class="form-control" placeholder="顺序">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">vod</label>
                  <div class="col-sm-10">
                    <input v-model="section.vod" type="text" class="form-control" placeholder="vod">
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
    name: "Section",
    components:{
      Pagination,
    },
    data(){
      return {
        section:{},
        sections:[],
        SECTION_CHARGE,
        chapter:{},
        course:{}
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
        let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
        let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
        _this.course = course;
        _this.chapter = chapter;
        _this.$api.post("/business/admin/section/list",
          {
            page:page,
            size:_this.$refs.pagination.size,
            courseId:_this.course.id,
            chapterId:_this.chapter.id
          }
        ).then(resp =>{
          Loading.hide();
          this.sections = resp.data.list;
          _this.$refs.pagination.render(page,resp.data.total);

        })
      },
      showAdd(){
        let _this = this;
        _this.section = {};
        $('#myModal').modal('show');
      },
      showEdit(section){
        let _this = this;
        _this.section = Object.assign({},section);
        $('#myModal').modal('show');
      },
      save(){
        let _this = this;
        // 保存校验
        if (1 != 1
          || !Validator.require(_this.section.title, "标题")
          || !Validator.length(_this.section.title, "标题", 1, 50)
          || !Validator.length(_this.section.video, "视频", 1, 200)
        ) {
          return;
        }
        _this.section.courseId = _this.course.id,
        _this.section.chapterId = _this.chapter.id,
        Loading.show();
        _this.$api.post("/business/admin/section/save",
          _this.section,
        ).then(resp =>{
          Loading.hide();
          if(resp){
          let response = resp.data;
          if(response.success){
            $('#myModal').modal('hide');
            _this.list(1);
            toast.success("保存成功");
          }else {
            toast.warning(response.msg);
          }
          }
        })
      },
      remove(id){
        let _this = this;
        Confirm.show('删除后不可恢复，确认删除？',function () {
          Loading.show();
          _this.$api.delete("/business/admin/section/delete/"+id
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
