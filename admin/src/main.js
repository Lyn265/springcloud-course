import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import axios from "axios";
import Swal from "sweetalert2";
// If you don't need the styles, do not connect
Vue.config.productionTip = false;
Vue.prototype.$api = axios;
Vue.prototype.$swal = Swal;


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
