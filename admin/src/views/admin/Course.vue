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
        $('#myModal').modal('show');
      },
      showEdit(course){
        let _this = this;
        _this.listCategory(course.id);
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
       let categorys = _this.tree.getCheckedNodes();
        if(Tool.isEmpty(categorys)){
          toast.warning("请选择分类");
          return;
        }
        console.log(categorys);
        //把选中的节点放到course里
        _this.course.categorys = categorys;
        Loading.show();
        _this.$api.post("/business/admin/course/save",
          _this.course
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
