import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import store from '../store/index'
import jwtDecode from 'jwt-decode'
import { ElMessage } from 'element-plus'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/login/LoginView.vue'),
    meta: {
      label: '登录'
    }
  },
  {
    path: '/home',
    name: 'HomeView',
    component: () => import('@/views/home/HomeView.vue'),
    meta: {
      label: 'Web窗口'
    },
    children: [
      {
        path: '/home',
        name: 'IndexView',
        component: () => import('@/views/home/IndexView.vue'),
        meta: {
          label: '首页'
        }
      }
    ]
  },
  {
    path: '/:catchAll(.*)',
    name: '404',
    component: () => import('@/views/wrong/fourHtml.vue')
  }
]
const router = createRouter({
  history: createWebHashHistory(),
  routes
})
// 处理登录用户角色路由
const getRoleRoute = async (token) => {
  // console.log(roleRoute)
  // // <将路由添加进Vuex,并且修改参数状态为True>
  store.commit('setRouters', token.roleRoute)
  store.commit('setAsyncRoutesMark', true)

  // 添加获取的路由放入首页子路由
  // 添加路由
  token.roleRoute.forEach(item => {
    // 子路由集合
    const routeSon = []
    const routeList = {
      path: item.routePath,
      name: item.routeName,
      component: () => import(`../views/${item.routeComponent}.vue`),
      meta: {
        label: item.routeLabel,
        isShow: item.routeHidden,
        icon: item.routeIcon
      },
      children: routeSon
    }
    // 添加子路由
    item.children.forEach(element => {
      routeSon.push({
        path: element.routePath,
        name: element.routeName,
        component: () => import(`../views/${element.routeComponent}.vue`),
        meta: {
          hidden: element.routeHidden,
          label: element.routeLabel,
          icon: element.routeIcon
        }
      })
    })
    if (item.routeId === 12) {
      // 添加路由
      router.addRoute(routeList)
    } else {
      // 添加路由
      router.addRoute('HomeView', routeList)
    }
  })

  // console.log('已添加路由如下:')
  // console.log(router.getRoutes())
}
// 检验token是否超时
const exitsTokenTime = (tokneTime) => {
  const thisTime = new Date().getTime()
  const dTime = (thisTime - tokneTime) / 1000 / 60 / 60
  if (dTime > 24) {
    // token大于24小时
    return false
  }
  return true
}
// 全局导航守卫
router.beforeEach((to, from, next) => {
  // vuex中的状态值
  const isLoadRouters = store.state.asyncRoutesMark
  let token:any

  // 每次跳转路由检查是否存在token并且计算是否超时
  if (!isLoadRouters) {
    // 查看浏览器是否存在token
    try {
      token = jwtDecode(window.localStorage.getItem('Authorization'))
    } catch (error) {
      ElMessage({
        message: 'token不存在，请登录！',
        type: 'warning'
      })
      // 当前不是在登录页面则跳转到登录页面
      if (to.path !== '/') { return next('/') }
      // 如果是登录页面
      return next()
    }

    // 检查token是否过期
    if (!exitsTokenTime(token.currentTime)) {
      if (to.path !== '/') { return next('/') }
      // 如果是登录页面
      return next()
    } else {
      // console.log('没有路由，添加路由')
      getRoleRoute(token)
      if (to.path === '/') {
        if (token.user.roleId < 2) {
          return next('/home')
        } else {
          return next('/ts/userAppHome')
        }
      }
      return next(to.path)
    }
  } else {
    // 没过期添加路由权限
    // <将路由添加进Vuex,并且修改参数状态为True>
    // console.log('有路由，有token，正常跳转。')
    return next()
  }
})

export default router
