import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import axios from "axios";
import Swal from "sweetalert2";
import * as  filters  from "./filter/filter";
import {toast} from "./utils/toast";
import {Tool} from "./utils/tool";
// If you don't need the styles, do not connect
Vue.config.productionTip = false;
Vue.prototype.$api = axios;
Vue.prototype.$swal = Swal;

axios.defaults.withCredentials=true;
axios.defaults.baseURL=process.env.VUE_APP_SERVER;
axios.defaults.timeout=30000;
axios.interceptors.request.use((config) =>{
    console.log("请求:",config);
    let token = Tool.getLoginUser().token;
    if(Tool.isNotEmpty(token)){
    config.headers.token = token;
    console.log("请求headers增加token:"+token);
    }
    return config;
},error => {}
);
axios.interceptors.response.use((response) =>{
  console.log("返回结果:",response);
    return response;
},  error => {
  if(error.response.status === 500){
    toast.error("系统异常");
  }
  return;
});

//全局过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key,filters[key]);
});
router.beforeEach((to, from, next) => {
  if(to.matched.some((item) =>{
    return item.meta.loginRequire;
  })){
    let loginUser = Tool.getLoginUser();
    if(Tool.isNotEmpty(loginUser)){
      next();
    }else{
      next('/login');
    }
  }else{
    next();
  }
});


new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

console.log("server:"+process.env.VUE_APP_SERVER);
