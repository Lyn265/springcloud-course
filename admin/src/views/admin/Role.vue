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
            <th>id</th>
            <th>角色</th>
            <th>描述</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <tr v-for="(role,index) in roles" :key="index">
              <td>{{role.id}}</td>
              <td>{{role.name}}</td>
              <td>{{role.desc}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button class="btn btn-xs btn-info" @click="editUser(role)">
                <i class="ace-icon fa fa-user bigger-120"></i>
              </button>
              <button class="btn btn-xs btn-info" @click="editResource(role)">
                <i class="ace-icon fa fa-list bigger-120"></i>
              </button>
              <button class="btn btn-xs btn-info" @click="showEdit(role)">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button class="btn btn-xs btn-danger" @click="remove(role.id)">
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
                  <label class="col-sm-2 control-label">角色</label>
                  <div class="col-sm-10">
                    <input v-model="role.name" type="text" class="form-control" placeholder="角色">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">描述</label>
                  <div class="col-sm-10">
                    <input v-model="role.desc" type="text" class="form-control" placeholder="描述">
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
    <div class="modal fade" id="user-model" tabindex="-1" role="dialog" aria-labelledby="user-model">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="user-model-label">角色用户关联配置</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-6">
                <table id="user-table" class="table table-hover">
                  <tbody>
                  <tr v-for="user in users">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a v-on:click="addUser(user)" href="javascript:;" class="">
                        <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
              <div class="col-md-6">
                <table id="role-user-table" class="table table-hover">
                  <tbody>
                  <tr v-for="user in roleUsers">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a v-on:click="deleteUser(user)" href="javascript:;" class="">
                        <i class="ace-icon fa fa-trash blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveUser()">保存</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="resource-model" tabindex="-1" role="dialog" aria-labelledby="resource-model">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="resource-model-label">角色资源关联配置</h4>
          </div>
          <div class="modal-body">
            <ul id="tree" class="ztree"></ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveResource()">保存</button>
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
    name: "Role",
    components:{
      Pagination,
    },
    data(){
      return {
        role:{},
        roles:[],
        ztree:{},
        resources:[],
        users:[],
        roleUsers:[]
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
        _this.$api.post("/system/admin/role/list",
          {
            page:page,
            size:_this.$refs.pagination.size
          }
        ).then(response =>{
          Loading.hide();
          let resp = response.data;
          this.roles = resp.content.list;
          _this.$refs.pagination.render(page,resp.content.total);

        })
      },
      showAdd(){
        let _this = this;
        _this.role = {};
        $('#myModal').modal('show');
      },
      showEdit(role){
        let _this = this;
        _this.role = Object.assign({},role);
        $('#myModal').modal('show');
      },
      editUser(role){
        let _this = this;
        _this.role = Object.assign({},role);
        _this.listUser();
        $('#user-model').modal('show');
      },
      /**
       * 查询所有用户
       */
      listUser() {
        let _this = this;
        _this.$api.post('/system/admin/user/list', {
          page: 1,
          size: 9999
        }).then((response)=>{
          let resp = response.data;
          if (resp.success) {
            _this.users = resp.content.list;
            _this.listRoleUser();
          } else {
            toast.warning(resp.message);
          }
        })
      },
      addUser(user){
        let _this = this;
        let roleUsers = _this.roleUsers;
        for (let i = 0; i <roleUsers.length ; i++) {
            if(user === roleUsers[i]){
              toast.warning("已添加该用户");
              return ;
            }
        }
          _this.roleUsers.push(user);
      },
      deleteUser(user){
        let _this = this;
        Tool.removeObj(_this.roleUsers,user);
      },
      listRoleUser(){
        let _this = this;
        _this.roleUsers = [];
        _this.$api.get("/system/admin/role/list-user/"+_this.role.id).then(response =>{
          let resp = response.data;
          let userIds = resp.content;
          for (let i = 0; i <userIds.length ; i++) {
            for (let j = 0; j <_this.users.length ; j++) {
              if(userIds[i] === _this.users[j].id){
                _this.roleUsers.push(_this.users[j]);
              }
            }
          }
        })
      },
      saveUser(){
        let _this = this;
        let roleId = _this.role.id;
        let userIds = [];
        Loading.show();
        for (let i = 0; i <_this.roleUsers.length ; i++) {
            userIds.push(_this.roleUsers[i].id);
        }
        _this.$api.post("/system/admin/role/save-user",{
          id:roleId,
          userIds:userIds
        }).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            toast.success("保存成功");
            $('#resource-model').modal('hide');
          }else {
            toast.warning(resp.msg);
          }
        })

      },
      editResource(role){
        let _this = this;
        _this.role = $.extend({},role);
        _this.loadResource(role);
        $('#resource-model').modal('show');

      },
      loadResource(){
        let _this = this;
        Loading.show();
        _this.$api.get("/system/admin/resource/load-tree").then(response =>{
          Loading.hide();
          let resp = response.data;
          this.resources = resp.content;
          _this.initTree();
          _this.listRoleResource();
        })

      },
      initTree(){
        let _this = this;
        let setting = {
          check:{
            enable:true
          },
          data: {
            simpleData: {
              idKey: "id",
              pIdKey: "parent",
              rootPId: "",
              enable: true
            }
          }
        };
        _this.ztree = $.fn.zTree.init($("#tree"), setting, _this.resources);
        _this.ztree.expandAll(true);
      },
      listRoleResource(){
        let _this = this;
        _this.$api.post("/system/admin/role/list-resource/"+_this.role.id
        ).then(response =>{
          let resp = response.data;
          if(resp.success){
            let resources = resp.content;
            _this.ztree.checkAllNodes(false);
            for (let i = 0; i <resources.length ; i++) {
             let node =  _this.ztree.getNodeByParam('id',resources[i]);
              _this.ztree.checkNode(node,true);
            }
          }else {
            toast.warning(resp.msg);
          }
        })
      },
      saveResource(){
        let _this = this;
        let roleId = _this.role.id;
        const resources = _this.ztree.getCheckedNodes();
        let resourceIds = [];
        for (let i = 0; i <resources.length ; i++) {
          resourceIds.push(resources[i].id);
        }
        console.log("勾选的资源:",resources);
        Loading.show();
        _this.$api.post("/system/admin/role/save-resource",{
          id:roleId,
          resourceIds:resourceIds
        }).then(response =>{
          Loading.hide();
          let resp = response.data;
          if(resp.success){
            toast.success("保存成功");
            $('#resource-model').modal('hide');
          }else {
            toast.warning(resp.msg);
          }
        })
      },
      save(){
        let _this = this;
        // 保存校验
        if (1 != 1
          || !Validator.require(_this.role.name, "角色")
          || !Validator.length(_this.role.name, "角色", 1, 50)
          || !Validator.require(_this.role.desc, "描述")
          || !Validator.length(_this.role.desc, "描述", 1, 100)
        ) {
          return;
        }

        Loading.show();
        _this.$api.post("/system/admin/role/save",
          _this.role,
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
          _this.$api.delete("/system/admin/role/delete/"+id
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
