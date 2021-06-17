 <template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <p>
          <button class="btn btn-white btn-default btn-round" @click="add1()">
            <i class="ace-icon fa fa-edit"></i>
            新增一级
          </button>
          &nbsp;
          <button class="btn btn-white btn-default btn-round" @click="all()">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <table id="level1-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(category,index) in level1" :key="index" v-on:click="onClickLevel1(category,index)" v-bind:class="{'active':active.id === category.id}">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">

                <button class="btn btn-xs btn-info" @click="showEdit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button class="btn btn-xs btn-danger" @click="remove(category.id)">
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
      </div>
      <div class="col-md-6">
        <p>
          <button class="btn btn-white btn-default btn-round" @click="add2()">
            <i class="ace-icon fa fa-edit"></i>
            新增二级
          </button>
        </p>
        <table id="level2-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(category,index) in level2" :key="index">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">

                <button class="btn btn-xs btn-info" @click="showEdit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button class="btn btn-xs btn-danger" @click="remove(category.id)">
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
                  <label class="col-sm-2 control-label">父分类</label>
                  <div class="col-sm-10">
                    <p class="form-control-static">{{active.name || '无'}}</p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">名称</label>
                  <div class="col-sm-10">
                    <input v-model="category.name" type="text" class="form-control" placeholder="名称">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">顺序</label>
                  <div class="col-sm-10">
                    <input v-model="category.sort" type="text" class="form-control" placeholder="顺序">
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
  import {toast} from "../../utils/toast";
  import {Confirm} from "../../utils/confirm";
  import {Validator} from "../../utils/validator";
  import {Tool} from "../../utils/tool";

  export default {
    name: "Category",
    components:{
    },
    data(){
      return {
        category:{},
        categorys:[],
        level1:[],
        level2:[],
        activeClass:-1,
        active:{},
      }
    },
    mounted() {
      let _this = this;
      _this.all();

    },
    methods:{
      all(){
        let _this = this;
        Loading.show();
        _this.$api.post("/business/admin/category/all",
        ).then(resp =>{
          Loading.hide();
          _this.categorys = resp.data.content;
          _this.level1 = [];
          for (let i = 0; i <_this.categorys.length ; i++) {
              let c = _this.categorys[i];
              if(c.parent === '00000000'){
                //设置父级分类
                _this.level1.push(c);
                for (let j = 0; j <_this.categorys.length ; j++) {
                  let child = _this.categorys[j];
                  if(child.parent === c.id){
                    if(Tool.isEmpty(c.children)){
                    c.children = [];
                    }
                    c.children.push(child);
                  }
                }
              }
          }
          _this.level2=[];
          setTimeout(() =>{
            $('tr.active').trigger('click')
          },100);

        })
      },
      onClickLevel1(category,index){
        let _this = this;
        _this.active  = category;
        _this.activeClass = index;
        _this.level2 = category.children;
      },
      add1(){
        let _this = this;
        _this.active = {};
        _this.level2=[];
        _this.category = {
          parent:'00000000',
        };
        $('#myModal').modal('show');
      },
      showEdit(category){
        let _this = this;
        _this.category = Object.assign({},category);
        $('#myModal').modal('show');
      },
      add2(){
        let _this = this;
        if(Tool.isEmpty(_this.active)){
          toast.warning("请先选择父级分类");
          return;
        }
        _this.category = {
          parent:_this.active.id
        };
        $('#myModal').modal('show');
      },
      save(){
        let _this = this;
        // 保存校验
        if (1 != 1
          || !Validator.require(_this.category.parent, "父id")
          || !Validator.require(_this.category.name, "名称")
          || !Validator.length(_this.category.name, "名称", 1, 50)
        ) {
          return;
        }

        Loading.show();
        _this.$api.post("/business/admin/category/save",
          _this.category,
        ).then(resp =>{
          Loading.hide();
          let response = resp.data;
          if(response.success){
            $('#myModal').modal('hide');
            _this.all();
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
          _this.$api.delete("/business/admin/category/delete/"+id
          ).then(resp =>{
            Loading.hide();
            let response = resp.data;
            if(response.success){
              _this.all();
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
  .active td{
    font-weight: bolder;
    background-color: #d6e9c6 !important;
  }
</style>
