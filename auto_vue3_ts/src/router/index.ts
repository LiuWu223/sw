import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import store from '../store/index'
import jwtDecode from 'jwt-decode'
import { userRoleApi } from '@/api/systemApi/sysUserRole-api'
import { ElNotification } from 'element-plus'
import { rolePermissionsApi } from '@/api/systemApi/sysRolePermissions-api'

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
        path: '/userNews',
        name: 'UserNews',
        component: () => import('@/views/home/UserNews.vue'),
        meta: {
          label: '个人信息'
        },
        children: [
          {
            path: '/existTestCases',
            name: 'ExistTestCases',
            component: () => import('@/views/home/usreNewsButtonList/ExistTestCases.vue'),
            meta: {
              label: '已有测试任务'
            }
          },
          {
            path: '/addTestCases',
            name: 'AddTestCases',
            component: () => import('@/views/home/usreNewsButtonList/AddTestCases.vue'),
            meta: {
              label: '新增测试用例'
            }
          },
          {
            path: '/collectTestCases',
            name: 'CollectTestCases',
            component: () => import('@/views/home/usreNewsButtonList/CollectTestCases.vue'),
            meta: {
              label: '收藏测试用例'
            }
          },
          {
            path: '/myManageTestCases',
            name: 'MyManageTestCases',
            component: () => import('@/views/home/usreNewsButtonList/MyManageTestCases.vue'),
            meta: {
              label: '管理测试用例'
            }
          },
          {
            path: '/taskOverview',
            name: 'TaskOverview',
            component: () => import('@/views/home/usreNewsButtonList/TaskOverview.vue'),
            meta: {
              label: '测试任务总览'
            }
          },
          {
            path: '/emptyState',
            name: 'EmptyState',
            component: () => import('@/views/home/usreNewsButtonList/EmptyState.vue'),
            meta: {
              label: '空状态'
            }
          }
        ]
      },
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

// 全局导航守卫
router.beforeEach((to, from, next) => {
  // 状态配置
  const isLoadRouters = store.state.asyncRoutesMark
  if (to.path === '/') return next()
  const token:any = jwtDecode(window.localStorage.getItem('Authorization'))
  console.log(token)

  // 如果token不存在token就跳转到登录页面
  if (!token) return next('/')
  // 解决刷新页面空白
  if (isLoadRouters) {
    //
    next()
  } else {
    // 查找当前登录用户的身份
    const ctrlP = async () => {
      const userRole = await userRoleApi.getUserIdByRoles(token.systemUser.userId)
      if (userRole.data.code === 1000) {
        // 根据角色id查询角色权限
        const permissionsList = await rolePermissionsApi.getRoleByPermissions(userRole.data.data.roleId)
        store.commit('setRouters', permissionsList.data.data)
        store.commit('setAsyncRoutesMark', true)
        next({ path: '/home' })
        // 添加路由
        permissionsList.data.data.forEach(item => {
          if (item.permissionsIsShow === '0') item.permissionsIsShow = true
          else item.permissionsIsShow = false
          if (item.permissionsHidden === '0') item.permissionsHidden = true
          else item.permissionsHidden = false
          // 子路由集合
          const uu = []
          if (item.permissionsHigher === '0') {
            const seft = {
              path: item.permissionsPath,
              name: item.permissionsName,
              component: () => import(`../views/${item.permissionsComponent}.vue`),
              meta: {
                label: item.permissionsLabel,
                isShow: item.permissionsIsShow,
                icon: item.permissionsIcon
              },
              children: uu
            }
            // 添加子路由
            item.children.forEach(element => {
              if (element.permissionsHidden === '0') item.children.permissionsHidden = true
              else item.children.permissionsHidden = false
              uu.push({
                path: element.permissionsPath,
                name: element.permissionsName,
                component: () => import(`../views/${element.permissionsComponent}.vue`),
                meta: {
                  hidden: element.permissionsHidden,
                  label: element.permissionsLabel
                }
              })
            })
            // console.log(uu)
            router.addRoute('HomeView', seft)
            // console.log(router.getRoutes())
          }
        })
      } else {
        ElNotification({
          title: 'Error',
          message: '获取用户权限失败',
          type: 'error'
        })
        return next('/')
      }
    }
    ctrlP()
  }
})

export default router
