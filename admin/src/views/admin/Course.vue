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
              <button class="btn btn-white btn-xs btn-info btn-round " @click="toEditContent(course)">
                内容
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round" @click="showEdit(course)">
                编辑
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-info btn-round " @click="showSortModal(course)">
                排序
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-danger btn-round" @click="remove(course.id)">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="courseModal" tabindex="-1" role="dialog" aria-labelledby="courseModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="courseModalLabel">课程</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">分类</label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>
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
                  <label class="col-sm-2 control-label">讲师</label>
                  <div class="col-sm-10">
                    <input v-model="course.teacherId" type="text" class="form-control" placeholder="讲师">
                  </div>
                </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="course.sort" type="text" class="form-control" placeholder="顺序" disabled>
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
    <div class="modal fade" id="sortModal" tabindex="-1" role="dialog" aria-labelledby="courseModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="sortModalLabel">课程排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-lg-3 control-label">当前顺序</label>
                <div class="col-lg-9">
                  <input v-model="sort.oldSort" type="text" class="form-control" disabled name="oldSort">
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label">新顺序</label>
                <div class="col-lg-9">
                  <input v-model="sort.newSort" type="text" class="form-control" placeholder="新顺序" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="updateSort()">更新排序</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="course-content-modal" tabindex="-1" role="dialog" aria-labelledby="courseModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">内容编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <div class="col-lg-12">
                  <div>{{saveContentLabel}}</div>
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-12">
                  文本编辑
                  <div id="content"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveContent()">保存</button>
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
  import {Tool} from "../../utils/tool";

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
        categorys:[],
        tree:{},
        saveContentLabel:"",
        sort:{
          id:'',
          oldSort:0,
          newSort:0,
        }
      }
    },
    mounted() {
      let _this = this;
      _this.allCatogery();
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
      toEditContent(course){
        let _this = this;
        let id = course.id;
        _this.course = course;
        //初始化富文本编辑框
        $("#content").summernote({
          focus:true,
          height:300
        });
        //初始化文本内容
          $("#content").summernote('node','');
          _this.saveContentLabel = '';
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
      toChapter(course){
        let _this = this;
        SessionStorage.set(SESSION_KEY_COURSE,course);
        _this.$router.push("/business/chapter");
      },
      showAdd(){
        let _this = this;
        _this.tree.checkAllNodes(false);
        _this.tree.expandAll(false);
        _this.course = {};
        _this.course.sort =  _this.$refs.pagination.total+1;
        $('#courseModal').modal('show');
      },
      showSortModal(course){
        let _this = this;
        _this.sort = {
          id:course.id,
          oldSort: course.sort,
          newSort: course.sort
        };
        $('#sortModal').modal('show');
      },
      showEdit(course){
        let _this = this;
        _this.listCategory(course.id);
        _this.course = Object.assign({},course);
        $('#courseModal').modal('show');
      },
      updateSort(){
        let _this = this;
        if(_this.sort.newSort === _this.sort.oldSort){
          toast.warning("排序没有变化。");
          return;
        }
        Loading.show();
        _this.$api.post("/business/admin/course/sort",
          _this.sort
        ).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            $('#sortModal').modal('hide');
            toast.success("更新排序成功");
            this.list(1);
          }else{
            toast.error("更新排序失败");
          }
        })

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
       let categorys = _this.tree.getCheckedNodes();
        if(Tool.isEmpty(categorys)){
          toast.warning("请选择分类");
          return;
        }
        //把选中的节点放到course里
        _this.course.categorys = categorys;
        Loading.show();
        _this.$api.post("/business/admin/course/save",
          _this.course
        ).then(resp =>{
          Loading.hide();
          let response = resp.data;
          if(response.success){
            $('#courseModal').modal('hide');
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
      allCatogery(){
        let _this = this;
        Loading.show();
        _this.$api.post("/business/admin/category/all",
        ).then(resp =>{
          Loading.hide();
          _this.categorys = resp.data.content;
          _this.initTree();
        })
      },
      listCategory(courseId){
        let _this = this;
        Loading.show();
        _this.$api.get("/business/admin/course/list-category/"+courseId,
        ).then(resp =>{
          Loading.hide();
          let listCategory = resp.data.content;
          console.log(listCategory);
          _this.tree.checkAllNodes(false);
          for (let i = 0; i <listCategory.length ; i++) {
              let category = listCategory[i];
              let node = _this.tree.getNodeByParam('id',category.categoryId);
              _this.tree.checkNode(node,true);
          }
        })
      },
      initTree(){
        let _this = this;
        let setting={
          check:{
            enable:true,
          },
          data:{
            simpleData:{
              enable: true,
              idKey: "id",
              pIdKey: "parent",
              rootPId: '00000000'
            }
          }
        };
        let zNodes =_this.categorys;
        _this.tree= $.fn.zTree.init($("#tree"),setting,zNodes);
      }

    }
  }
</script>

<style scoped>

</style>
