// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
const baseIp = '127.0.0.1'
Vue.use(ElementUI)
Vue.prototype.$axios = axios.create({
  baseURL: 'http://' + baseIp + ':8888',
  timeout: 60000
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
