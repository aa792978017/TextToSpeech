import Vue from 'vue'
import Router from 'vue-router'
import TextToSpeech from '../components/TextToSpeech'
import Home from '../components/Home'
import App from '../App'

Vue.use(Router)
// 新建路由地址routes，传入路由信息
const routes = [
  {
    path: '/tools',
    name: 'App',
    meta: {
      requireAuth: false,
      view: false
    },
    component: App,
    children: [
      {
        path: 'home',
        name: 'Home',
        component: Home,
        meta: {
          requireAuth: false,
          view: false
        },
        children: [
          {
            path: 'text-to-speech',
            name: 'TextToSpeech',
            component: TextToSpeech,
            meta: {
              requireAuth: false,
              view: true
            }
          }
        ]
      }
    ]
  }]
// 路由对象
const router = new Router({
  mode: 'history',
  routes: routes
})
// 路由可浏览判断
router.beforeEach((to, from, next) => {
  if (to.meta.view) { // 判断该路由是否为可浏览路由，如果是，则跳转
    next()
  } else {
    // 如果不是，则返回原路由
    next("/tools/home/text-to-speech")
  }
})
export default router
