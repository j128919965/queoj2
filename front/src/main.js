import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import AntD from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import router from './router'
import store from './store'

Vue.use(AntD)
Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 路由跳转
Vue.prototype.$goRoute = function (index) {
  this.$router.push(index)
}

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
