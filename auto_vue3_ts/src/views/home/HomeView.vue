<template>
  <el-container class="home">
    <el-aside class="home-aside" :width="asideWidth">
      <div class="home-logo">
        <img src="../../assets/images/icon1.png" />
        <span v-show="!isCollapseMenu"><p style="color: aliceblue;font-weight: 500;font-size: 14px;margin-left: 5px;">测试平台</p></span>
      </div>
      <el-menu
        router
        unique-opened
        :collapse="isCollapseMenu"
        :collapse-transition="false"
        class="home-menu"
        :default-active="$route.path"
      >
        <el-menu-item index="/home">
          <el-icon><DataAnalysis /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-sub-menu v-for="i in pathList" :key="i.path" :index=i.path>
          <template #title>
            <el-icon ><component :is='i.meta.icon'/></el-icon>
            <span>{{ i.meta.label }}</span>
          </template>
          <el-menu-item v-for="it in i.children" :key="it.path" :index="i.path + '/' + it.path">{{it.meta.label}}</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container class="home-container">
      <el-header class="home-header">
        <el-row justify="space-between" align="middle">
          <el-col :span="4">
            <el-button
              type="primary"
              :icon="collapseIcon"
              style="transform: rotate(180deg);background-color: #ffffff00;border: none;"
              @click="collapseMenu"
            />
          </el-col>
          <el-col :span="20" style="text-align: right">
            <el-button type="primary" :icon="Bell" />
            <el-button type="primary" :icon="Lock" />
            <el-dropdown>
              <el-button type="primary">
                <template #default>
                  <el-avatar :size="25" :src="'micro_question_answer_admin_war' + userAvatar" />&nbsp;&nbsp;
                  <span>{{ userName }}</span>
                </template>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :icon="Setting" @click="router.push({ path: '/userNews' })">账户设置</el-dropdown-item>
                  <el-dropdown-item :icon="EditPen">切换主题</el-dropdown-item>
                  <el-dropdown-item :icon="Refresh">清理缓存</el-dropdown-item>
                  <el-dropdown-item @click="exitLogin" :icon="SwitchButton"
                    >退出系统</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-button type="primary" :icon="Setting" />
          </el-col>
        </el-row>
      </el-header>
      <el-main class="home-main">
        <el-tabs
          type="card"
          v-model="$route.path"
          @tab-change="tabChange"
          @tab-remove="tabRemove"
        >
          <el-tab-pane
            v-for="(item, index) in tabs"
            :label="item.label"
            :name="item.name"
            :closable="item.closable"
            :key="index"
          ></el-tab-pane>
        </el-tabs>
        <el-scrollbar>
          <div>
            <router-view/>
          </div>
          <!-- <div>
            <router-view v-slot="{ Component }">
              <transition name="fang">
                <component :is="Component" />
              </transition>
            </router-view>
          </div> -->
        </el-scrollbar>
      </el-main>
      <el-footer class="home-footer">
        <el-divider class="home-footer-divider"></el-divider>
        <div class="home-footer-info">
          Copyright © 2020 - 2023 Liu Wu Corporation, Seewo Auto Test
        </div>
      </el-footer>
    </el-container>
  </el-container>
</template>
<script lang="ts" setup>
import { ref, shallowRef, watch } from 'vue'
import { useRouter } from 'vue-router'
import jwtDecode from 'jwt-decode'
import {
  Setting,
  DataAnalysis,
  Fold,
  Expand,
  Bell,
  Lock,
  EditPen,
  Refresh,
  SwitchButton
} from '@element-plus/icons-vue'

const asideWidth = ref('200px')
const isCollapseMenu = ref(false)
const collapseIcon = shallowRef(Fold)
// 创建home的tab路由对象
interface HomeTab {
  label: unknown;
  name: string;
  closable: boolean;
}
// 定义tab初始路由，colosable标识是是否能够删除
const tabs = ref<HomeTab[]>([
  {
    label: '首页',
    name: '/home',
    closable: false
  }
])
const router = useRouter()
// 解析localStorage里面的token
const tokens:any = jwtDecode(localStorage.getItem('Authorization'))
const userName = ref(tokens.systemUser.userName)
const userAvatar = ref(tokens.systemUser.userAvatar)
// filter过滤出子路由中对页面渲染的结果
const pathList = router.getRoutes().filter(v => v.meta.isShow)
// 监听器 用来监听路由是否发生变化
watch(
  () => router.currentRoute.value,
  (newValue) => {
    // 过虑器
    const arrTabs = tabs.value.filter((item) => {
      return item.name === newValue.path
    })
    if (arrTabs.length === 0) {
      // 对象创建赋值
      const tab = {
        label: newValue.meta.label,
        name: newValue.path,
        closable: true
      }
      tabs.value.push(tab)
    }
  },
  { immediate: true }
)
// 菜单栏收起
const collapseMenu = () => {
  if (isCollapseMenu.value) {
    isCollapseMenu.value = false
    asideWidth.value = '250px'
    collapseIcon.value = Fold
  } else {
    isCollapseMenu.value = true
    asideWidth.value = '64px'
    collapseIcon.value = Expand
  }
}
// 保存tab的所有页面路径
const arr: string[] = ['/home']
// tab添加页面
const tabChange = (name: string) => {
  // 跳转到页面name
  router.push({ path: name })
  if (arr.indexOf(name) === -1) {
    // 添加一个页面到tab，列表
    arr.push(name)
  }
}
// 删除tab
const tabRemove = (name: string) => {
  // currIndex初始化为-1标识不存在，用来判断是否存在
  let currIndex = -1
  // 遍历路由里面的vue
  tabs.value.forEach((item, index) => {
    // 判断传过的来name拿到下标
    if (item.name === name) {
      // 将需要删除的name下标赋值给currIndex
      currIndex = index
    }
  })
  // 判断currIndex是否为-1,-1标识不存在
  if (currIndex !== -1) {
    // 删除tabs中的curIndex的下标元素  1：删除一个元素
    tabs.value.splice(currIndex, 1)
    // 遍历删除元素后的tabs
    tabs.value.forEach((item, index) => {
      // 判断上次删除后的下标-1进行跳转上一页
      if ((currIndex - 1) === index) {
        // 跳转到删除的前一个页面
        router.push({ path: item.name }).catch(err => {
          console.log(err)
        })
      }
    })
  }
}
const exitLogin = () => {
  router.push({
    name: 'LoginView',
    replace: true
  }).catch(err => {
    console.log(err)
  })
  // 删除localStorage中的token对象
  localStorage.removeItem('Authorization')
}

</script>

<style scoped lang="scss">
.container {
  position: relative;
  padding: 0;
}
.home {
  height: 100vh;
  .home-aside {
    .home-logo {
      height: 50px;
      background-color: var(--el-color-primary);
      display: flex;
      justify-content: center;
      align-items: center;
      overflow: hidden;
      white-space: nowrap;
      img {
        width: 25px;
      }
      span {
        color: var(--el-color-white);
        font-size: 18px;
        font-weight: bold;
      }
    }
    .home-menu {
      height: calc(100vh - 50px);
    }
  }
  .home-container {
    .home-header {
      height: 50px;
      background-color: var(--el-color-primary);
      padding: 0;
      .el-button {
        font-size: 20px;
        height: 50px;
        border-radius: 0;
        margin-left: 0;
        span {
          font-size: 14px;
        }
      }
      .el-row {
        height: 50px;
      }
    }
    .home-main {
      padding: 0;
      .el-scrollbar {
        height: calc(100% - 81px);
        padding: 20px;
      }
    }
    .home-footer {
      height: 40px;
      text-align: center;
      font-size: 12px;
      padding: 0;
      .home-footer-divider {
        margin: 0;
      }
      .home-footer-info {
        line-height: 38px;
      }
    }
  }
}
</style>

<style lang="scss">
.home-main {
  .el-tabs__header {
    margin: 0 !important;
  }
  .el-tabs--card > .el-tabs__header .el-tabs__nav {
    border-radius: 0 !important;
  }
}
.fang-enter-from{
  transform: translateX(-100%);
}
.fang-enter-to{
  transform: translateX(0);
}
.fang-enter-active{
  transition: 0.5s ease;
}
</style>
