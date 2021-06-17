<template>
  <div>
  <p>
    <button v-on:click="list()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-refresh"></i>
      刷新
    </button>
  </p>
  <div class="row">
    <div class="col-md-6">
      <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>
      <br>
    <button type="button" id="save-btn" class="btn btn-primary" v-on:click="save">保存</button>
    </div>
    <div class="col-md-6">
      <ul id="tree" class="ztree"></ul>
    </div>
  </div>
  </div>
</template>

<script>
  import {toast} from "../../utils/toast";
  import {Tool} from "../../utils/tool";

  export default {
    name: "Resource",
    components:{
    },
    data(){
      return {
        resource:{},
        resources:[],
        resourceStr:'',
        tree:{}
      }
    },
    mounted() {
      let _this = this;
      _this.list();

    },
    methods:{
      list(){
        let _this = this;
        Loading.show();
        _this.$api.get("/system/admin/resource/load-tree").then(response =>{
          Loading.hide();
          let resp = response.data;
          this.resources = resp.content;
          _this.initTree();
        })
      },
      save(){
        let _this = this;
        if(Tool.isEmpty(_this.resourceStr)){
          toast.warning("不能为空");
          return;
        }
        // 保存校验
        let json = JSON.parse(_this.resourceStr);
        Loading.show();
        _this.$api.post("/system/admin/resource/save", json).then(resp =>{
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
      initTree(){
        let _this = this;
        let setting = {
          data: {
            simpleData: {
              idKey: "id",
              pIdKey: "parent",
              rootPId: "",
              // enable: true
            }
          }
        };
        _this.tree = $.fn.zTree.init($("#tree"), setting, _this.resources);
        _this.tree.expandAll(true);
      }
    }
  }
</script>

<style scoped>

</style>
