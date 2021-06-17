<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>
                <span class="">控台登录</span>
              </h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入用户名和密码
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" v-model="loginUser.loginName" class="form-control" placeholder="Username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                        </label>

                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" v-model="loginUser.password" class="form-control" placeholder="Password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                        </label>
                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
                            <div class="input-group">
                              <input type="text" v-model="imageCode" class="form-control" placeholder="验证码" aria-describedby="basic-addon2">
                              <span class="input-group-addon" id="basic-addon2">
                                  <img :src="imgCode" @click="loadImageCode" id="image-code" alt="验证码"/>
                              </span>
                            </div>
														</span>
                        </label>
                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input type="checkbox" v-model="remember" class="ace"/>
                            <span class="lbl"> 记住我</span>
                          </label>

                          <button type="button" class="width-35
                          pull-right btn btn-sm btn-primary"
                          @click="login">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.login-box -->
            </div><!-- /.position-relative -->
          </div>
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.main-content -->
  </div><!-- /.main-container -->
</template>

<script>
  import {toast} from "../utils/toast";
  import {Tool} from "../utils/tool";

  export default {
    name: "Login",
    mounted() {
      let _this = this;
      $('body').removeClass( 'class','no-skin');
      $('body').attr('class', 'login-layout light-login');
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if(rememberUser){
        _this.loginUser = rememberUser;
      }
      _this.loadImageCode();
    },
    data(){
      return {
        loginUser:{
          loginName:'',
          password:'',
        },
        imgCode:'',
        remember:true,
        imageCode:'',
        imageCodeToken:'',
      }
    },
    methods:{
      login(){
        let _this = this;
        Loading.show();
        //此时用户输入的新密码或者没动过的密文进行一次MD5加密，和缓存的MD5密码做对比
        let newMd5  = hex_md5(_this.loginUser.password);
        let ls = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
        if(newMd5 !== ls.md5){
          _this.loginUser.password = hex_md5(_this.loginUser.password + KEY);
        }
        _this.loginUser.imageCode = _this.imageCode;
        _this.loginUser.imageCodeToken = _this.imageCodeToken;
        _this.$api.post("/system/admin/user/login", _this.loginUser).then(response =>{
          Loading.hide();
          let resp = response.data;
          Tool.setLoginUser(resp.content);
          if(resp.success){
            let loginUser = resp.content;
            if(_this.remember){
              //对密文进行加密,来判断密码是否被修改过
              let md5  = hex_md5(_this.loginUser.password);
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER,
              {
                loginName: loginUser.loginName,
                password:_this.loginUser.password,
                md5:md5
              }
              );
            }else{
              LocalStorage.remove(LOCAL_KEY_REMEMBER_USER);
            }
            this.$router.push('/welcome');
          }else {
            toast.warning(resp.msg);
            _this.loginUser.password = '';
            _this.loadImageCode();
          }
        })
      },
      loadImageCode(){
        let _this = this;
        _this.imageCodeToken = Tool.uuid(8);
       _this.imgCode  = 'http://localhost:9000/system/admin/kaptcha/image-code/'+_this.imageCodeToken;
        // $('#image-code').attr('src', 'http://localhost:9000/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
        }
    }
  }
</script>

<style scoped>
.input-group-addon{
  padding: 0;
}
</style>
