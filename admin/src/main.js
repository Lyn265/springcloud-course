import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import axios from "axios";
import Swal from "sweetalert2";
import * as  filters  from "./filter/filter";
// If you don't need the styles, do not connect
Vue.config.productionTip = false;
Vue.prototype.$api = axios;
Vue.prototype.$swal = Swal;

axios.defaults.baseURL=process.env.VUE_APP_SERVER;
axios.defaults.timeout=30000;
axios.interceptors.request.use((config) =>{
    console.log("请求:",config);
    return config;
},error => {}
);
axios.interceptors.response.use((response) =>{
  console.log("返回结果:",response);
    return response;
},  error => {});

//全局过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key,filters[key]);
})


new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

console.log("server:"+process.env.VUE_APP_SERVER);
