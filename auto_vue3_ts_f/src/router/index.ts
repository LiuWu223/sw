import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/home/HomeView.vue'),
    meta: {
      label: '测试任务'
    },
    children: [
      {
        path: '/',
        name: 'NewlyTasks',
        component: () => import('@/views/tripartiteTasks/newlyTasks.vue'),
        meta: {
          label: '新增测试任务'
        }
      },
      {
        path: '/tasksIng',
        name: 'TasksIng',
        component: () => import('@/views/tripartiteTasks/tasksIng.vue'),
        meta: {
          label: '测试任务列表'
        }
      },
      {
        path: '/fallTacks',
        name: 'FallTacks',
        component: () => import('@/views/tripartiteTasks/fallTacks.vue'),
        meta: {
          label: '测试失败任务'
        }
      },
      {
        path: '/passTacks',
        name: 'PassTacks',
        component: () => import('@/views/tripartiteTasks/passTacks.vue'),
        meta: {
          label: '测试成功任务'
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

export default router
