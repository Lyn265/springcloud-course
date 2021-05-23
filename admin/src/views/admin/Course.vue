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
    <div class="row">
      <div class="col-md-4" v-for="(course,index) in courses" :key="index"  >
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
          <img v-show="course.image" class="media-object" v-bind:src="course.image" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
              {{COURSE_LEVEL | optionKV(course.level)}}
              </span>
              <span class="pull-right label label-primary info-label">
              {{COURSE_CHARGE | optionKV(course.charge)}}
              </span>
              <span class="pull-right label label-primary info-label">
              {{COURSE_STATUS | optionKV(course.status)}}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>
            <p>
              <span class="blue bolder bigger-150">
                {{course.price}}&nbsp;<i class="fa fa-rmb"></i>
              </span>
            </p>
            <p>{{course.summary}}</p>
            <p>
              <span class="badge badge-info">
                ID:{{course.id}}
              </span>&nbsp;
              <span class="badge badge-info">
                排序:{{course.sort}}
              </span>&nbsp;
              <span class="badge badge-info">
                时长:{{course.time | formatSecond}}
              </span>
            </p>
            <p>
              <button class="btn btn-white btn-xs btn-info btn-round " @click="toChapter(course)">
                大章
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round" @click="showEdit(course)">
                编辑
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-danger btn-round" @click="remove(course.id)">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
<!--      <table id="simple-table" class="table  table-bordered table-hover">-->
<!--        <thead>-->
<!--          <tr>-->
<!--            <th></th>-->
<!--            <th>名称</th>-->
<!--            <th>概述</th>-->
<!--            <th>时长</th>-->
<!--            <th>价格（元）</th>-->
<!--            <th>封面</th>-->
<!--            <th>级别</th>-->
<!--            <th>收费</th>-->
<!--            <th>状态</th>-->
<!--            <th>报名数</th>-->
<!--            <th>顺序</th>-->
<!--            <th>讲师</th>-->
<!--            <th>操作</th>-->
<!--          </tr>-->
<!--        </thead>-->
<!--        <tbody>-->
<!--        <tr v-for="(course,index) in courses" :key="index">-->
<!--              <td>{{course.id}}</td>-->
<!--              <td>{{course.name}}</td>-->
<!--              <td>{{course.summary}}</td>-->
<!--              <td>{{course.time}}</td>-->
<!--              <td>{{course.price}}</td>-->
<!--              <td>{{course.image}}</td>-->
<!--              <td>{{COURSE_LEVEL | optionKV(course.level)}}</td>-->
<!--              <td>{{COURSE_CHARGE | optionKV(course.charge)}}</td>-->
<!--              <td>{{COURSE_STATUS | optionKV(course.status)}}</td>-->
<!--              <td>{{course.enroll}}</td>-->
<!--              <td>{{course.sort}}</td>-->
<!--              <td>{{course.teacherId}}</td>-->
<!--          <td>-->
<!--            <div class="hidden-sm hidden-xs btn-group">-->

<!--              <button class="btn btn-xs btn-info" @click="showEdit(course)">-->
<!--                <i class="ace-icon fa fa-pencil bigger-120"></i>-->
<!--              </button>-->

<!--              <button class="btn btn-xs btn-danger" @click="remove(course.id)">-->
<!--                <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--              </button>-->
<!--            </div>-->
<!--&lt;!&ndash;            <div class="hidden-md hidden-lg">&ndash;&gt;-->
<!--&lt;!&ndash;              <div class="inline pos-rel">&ndash;&gt;-->
<!--&lt;!&ndash;                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">&ndash;&gt;-->
<!--&lt;!&ndash;                  <i class="ace-icon fa fa-cog icon-only bigger-110"></i>&ndash;&gt;-->
<!--&lt;!&ndash;                </button>&ndash;&gt;-->

<!--&lt;!&ndash;                <ul&ndash;&gt;-->
<!--&lt;!&ndash;                  class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">&ndash;&gt;-->
<!--&lt;!&ndash;                  <li>&ndash;&gt;-->
<!--&lt;!&ndash;                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">&ndash;&gt;-->
<!--&lt;!&ndash;																			<span class="blue">&ndash;&gt;-->
<!--&lt;!&ndash;																				<i class="ace-icon fa fa-search-plus bigger-120"></i>&ndash;&gt;-->
<!--&lt;!&ndash;																			</span>&ndash;&gt;-->
<!--&lt;!&ndash;                    </a>&ndash;&gt;-->
<!--&lt;!&ndash;                  </li>&ndash;&gt;-->

<!--&lt;!&ndash;                  <li>&ndash;&gt;-->
<!--&lt;!&ndash;                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">&ndash;&gt;-->
<!--&lt;!&ndash;																			<span class="green">&ndash;&gt;-->
<!--&lt;!&ndash;																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>&ndash;&gt;-->
<!--&lt;!&ndash;																			</span>&ndash;&gt;-->
<!--&lt;!&ndash;                    </a>&ndash;&gt;-->
<!--&lt;!&ndash;                  </li>&ndash;&gt;-->

<!--&lt;!&ndash;                  <li>&ndash;&gt;-->
<!--&lt;!&ndash;                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">&ndash;&gt;-->
<!--&lt;!&ndash;																			<span class="red">&ndash;&gt;-->
<!--&lt;!&ndash;																				<i class="ace-icon fa fa-trash-o bigger-120"></i>&ndash;&gt;-->
<!--&lt;!&ndash;																			</span>&ndash;&gt;-->
<!--&lt;!&ndash;                    </a>&ndash;&gt;-->
<!--&lt;!&ndash;                  </li>&ndash;&gt;-->
<!--&lt;!&ndash;                </ul>&ndash;&gt;-->
<!--&lt;!&ndash;              </div>&ndash;&gt;-->
<!--&lt;!&ndash;            </div>&ndash;&gt;-->
<!--          </td>-->
<!--        </tr>-->
<!--        <tr class="detail-row">-->
<!--          <td colspan="8">-->
<!--            <div class="table-detail">-->
<!--              <div class="row">-->
<!--                <div class="col-xs-12 col-sm-2">-->
<!--                  <div class="text-center">-->
<!--                    <img height="150" class="thumbnail inline no-margin-bottom" alt="Domain Owner's Avatar" src="assets/images/avatars/profile-pic.jpg" />-->
<!--                    <br />-->
<!--                    <div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">-->
<!--                      <div class="inline position-relative">-->
<!--                        <a class="user-title-label" href="#">-->
<!--                          <i class="ace-icon fa fa-circle light-green"></i>-->
<!--                          &nbsp;-->
<!--                          <span class="white">Alex M. Doe</span>-->
<!--                        </a>-->
<!--                      </div>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                </div>-->

<!--                <div class="col-xs-12 col-sm-7">-->
<!--                  <div class="space visible-xs"></div>-->

<!--                  <div class="profile-user-info profile-user-info-striped">-->
<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> Username </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <span>alexdoe</span>-->
<!--                      </div>-->
<!--                    </div>-->

<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> Location </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <i class="fa fa-map-marker light-orange bigger-110"></i>-->
<!--                        <span>Netherlands, Amsterdam</span>-->
<!--                      </div>-->
<!--                    </div>-->

<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> Age </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <span>38</span>-->
<!--                      </div>-->
<!--                    </div>-->

<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> Joined </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <span>2010/06/20</span>-->
<!--                      </div>-->
<!--                    </div>-->

<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> Last Online </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <span>3 hours ago</span>-->
<!--                      </div>-->
<!--                    </div>-->

<!--                    <div class="profile-info-row">-->
<!--                      <div class="profile-info-name"> About Me </div>-->

<!--                      <div class="profile-info-value">-->
<!--                        <span>Bio</span>-->
<!--                      </div>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                </div>-->

<!--                <div class="col-xs-12 col-sm-3">-->
<!--                  <div class="space visible-xs"></div>-->
<!--                  <h4 class="header blue lighter less-margin">Send a message to Alex</h4>-->

<!--                  <div class="space-6"></div>-->

<!--                  <form>-->
<!--                    <fieldset>-->
<!--                      <textarea class="width-100" resize="none" placeholder="Type something…"></textarea>-->
<!--                    </fieldset>-->

<!--                    <div class="hr hr-dotted"></div>-->

<!--                    <div class="clearfix">-->
<!--                      <label class="pull-left">-->
<!--                        <input type="checkbox" class="ace" />-->
<!--                        <span class="lbl"> Email me a copy</span>-->
<!--                      </label>-->

<!--                      <button class="pull-right btn btn-sm btn-primary btn-white btn-round" type="button">-->
<!--                        Submit-->
<!--                        <i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>-->
<!--                      </button>-->
<!--                    </div>-->
<!--                  </form>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--          </td>-->
<!--        </tr>-->
<!--        </tbody>-->
<!--      </table>-->
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
                    <input v-model="course.name" type="text" class="form-control" placeholder="名称">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">概述</label>
                  <div class="col-sm-10">
                    <input v-model="course.summary" type="text" class="form-control" placeholder="概述">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">时长</label>
                  <div class="col-sm-10">
                    <input v-model="course.time" type="text" class="form-control" placeholder="时长">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">价格（元）</label>
                  <div class="col-sm-10">
                    <input v-model="course.price" type="text" class="form-control" placeholder="价格（元）">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">封面</label>
                  <div class="col-sm-10">
                    <input v-model="course.image" type="text" class="form-control" placeholder="封面">
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-sm-2 control-label">级别</label>
                  <div class="col-sm-10">
                    <select v-model="course.level" class="form-control">
                      <option v-for="s in COURSE_LEVEL" v-bind:value="s.key">{{s.value}}</option>
                    </select>
                  </div>
                  </div>
                  <div class="form-group">
                  <label class="col-sm-2 control-label">收费</label>
                  <div class="col-sm-10">
                    <select v-model="course.charge" class="form-control">
                      <option v-for="s in COURSE_CHARGE" v-bind:value="s.key">{{s.value}}</option>
                    </select>
                  </div>
                  </div>
                  <div class="form-group">
                  <label class="col-sm-2 control-label">状态</label>
                  <div class="col-sm-10">
                    <select v-model="course.status" class="form-control">
                      <option v-for="s in COURSE_STATUS" v-bind:value="s.key">{{s.value}}</option>
                    </select>
                  </div>
                  </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">报名数</label>
                  <div class="col-sm-10">
                    <input v-model="course.enroll" type="text" class="form-control" placeholder="报名数">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">顺序</label>
                  <div class="col-sm-10">
                    <input v-model="course.sort" type="text" class="form-control" placeholder="顺序">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">讲师</label>
                  <div class="col-sm-10">
                    <input v-model="course.teacherId" type="text" class="form-control" placeholder="讲师">
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
    name: "Course",
    components:{
      Pagination,
    },
    data(){
      return {
        course:{},
        courses:[],
      COURSE_LEVEL,
      COURSE_CHARGE,
      COURSE_STATUS,
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
        _this.$api.post("/business/admin/course/list",
          {
            page:page,
            size:_this.$refs.pagination.size
          }
        ).then(resp =>{
          Loading.hide();
          this.courses = resp.data.list;
          _this.$refs.pagination.render(page,resp.data.total);

        })
      },
      toChapter(course){
        let _this = this;
        SessionStorage.set(SESSION_KEY_COURSE,course);
        _this.$router.push("/business/chapter");
      },
      showAdd(){
        let _this = this;
        _this.course = {};
        $('#myModal').modal('show');
      },
      showEdit(course){
        let _this = this;
        _this.course = Object.assign({},course);
        $('#myModal').modal('show');
      },
      save(){
        let _this = this;
        // 保存校验
        if (1 != 1
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
        ) {
          return;
        }

        Loading.show();
        _this.$api.post("/business/admin/course/save",
          _this.course,
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
          _this.$api.delete("/business/admin/course/delete/"+id
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
