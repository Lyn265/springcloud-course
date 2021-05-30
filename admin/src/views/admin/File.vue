<template>
  <div>
    <p>
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
            <th>相对路径</th>
            <th>文件名</th>
            <th>后缀</th>
            <th>大小</th>
            <th>用途</th>
<!--            <th>已上传分片</th>-->
<!--            <th>分片大小</th>-->
<!--            <th>分片总数</th>-->
<!--            <th>文件标识</th>-->
<!--            <th>vod</th>-->
          </tr>
        </thead>
        <tbody>
        <tr v-for="(file,index) in files" :key="index">
              <td>{{file.id}}</td>
              <td>{{file.path}}</td>
              <td>{{file.name}}</td>
              <td>{{file.suffix}}</td>
              <td>{{file.size | formatFileSize}}</td>
              <td>{{FILE_USE | optionKV(file.use)}}</td>
<!--              <td>{{file.shardIndex}}</td>-->
<!--              <td>{{file.shardSize}}</td>-->
<!--              <td>{{file.shardTotal}}</td>-->
<!--              <td>{{file.key}}</td>-->
<!--              <td>{{file.vod}}</td>-->
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
</template>

<script>
  import Pagination from "../../components/Pagination";

  export default {
    name: "File",
    components:{
      Pagination,
    },
    data(){
      return {
        files:[],
        FILE_USE,
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
        _this.$api.post("/file/admin/file/list",
          {
            page:page,
            size:_this.$refs.pagination.size
          }
        ).then(resp =>{
          Loading.hide();
          this.files = resp.data.list;
          _this.$refs.pagination.render(page,resp.data.total);

        })
      },
    }
  }
</script>

<style scoped>

</style>
