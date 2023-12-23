import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import App from './App.vue'
import router from './router'
import '@/css/global.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
import axios from 'axios'

Vue.prototype.$axios = axios;
//Vue.prototype.$httpUrl = 'http://localhost:8080/';

new Vue({
    router,
    render: h=>h(App)
}).$mount('#app')
