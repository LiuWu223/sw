<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="header-title">
          <el-text tag="b" style="font-size: medium;color: #409EFF;">
            希倍思智能硬件测试平台
            <el-text tag="sup" size="small" style="font-style: oblique;color: #F56C6C;">V0.1</el-text>
          </el-text>
        </div>
        <div class="header-a">
          <div style="display: flex;">
            <el-avatar shape="square" :size="35" :src="'auto_sw_admin_war' + token.user.userAvatar" @error="errorHandler" />
            <div class="userName" style="margin-left: 10px;">
              <el-tag small>{{ token.user.roleName }}</el-tag>
              <el-text tag="b" style="margin-left: 5px;color: #000000; font-size: 17px; font-family: cursive;">{{ token.user.userName }}</el-text>
            </div>
          </div>
          <svg
          @click="userMessage = !userMessage"
          t="1696409936344"
          class="icon"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="887"
          width="25"
          height="25">
          <path d="M746.496 149.504c94.208 0 170.496 76.288 170.496 170.496v341.504c0 94.208-76.288 170.496-170.496 170.496h-222.72l-253.952 139.776c-5.632 3.072-12.8 3.072-18.944 0.512-10.752-4.608-15.872-17.408-10.752-28.16l51.2-112.128h-13.824c-94.208 0-170.496-76.288-170.496-170.496V320c0-94.208 76.288-170.496 170.496-170.496h468.992zM277.504 213.504c-58.88 0-107.008 47.616-107.008 106.496v341.504c0 58.88 47.616 106.496 106.496 106.496h113.152l-38.912 85.504 141.312-77.824L506.88 768h239.616c58.88 0 106.496-47.616 106.496-106.496V320c0-58.88-47.616-106.496-106.496-106.496H277.504z m447.488 287.744c0 29.184-23.552 53.248-53.248 53.248-29.184 0-53.248-23.552-53.248-53.248 0-29.184 23.552-53.248 53.248-53.248 29.696 0 53.76 24.064 53.248 53.248z m-170.496 0c0 29.184-23.552 53.248-53.248 53.248-29.184 0-53.248-23.552-53.248-53.248 0-29.184 23.552-53.248 53.248-53.248 29.696 0 53.248 24.064 53.248 53.248z m-170.496 0c0 29.184-23.552 53.248-53.248 53.248-29.184 0-53.248-23.552-53.248-53.248 0-29.184 23.552-53.248 53.248-53.248 29.184 0 53.248 24.064 53.248 53.248z" fill="#000000" p-id="888"></path></svg>
          <el-icon @click="exitLogin" :size="23" style="color: #000000;"><SwitchButton /></el-icon>
          <transition name="el-zoom-in-top">
            <el-row :gutter="12" class="messageRow" v-if="userMessage">
              <el-col :span="8" class="messageCol">
                <el-card class="messageCard">
                  <el-tag class="ml-2" type="info" style="margin-bottom: 10px; margin-left: 30px;" effect="plain">消息</el-tag>
                  <div style="overflow-y: scroll;width: 432px;height: 400px;">
                    <div class="mes">
                      <!-- 标题 -->
                      <div class="mes-title">
                        <span style="font-size: large;color: #6ab76e;">修改测试用例<span class="mesUser">张炼武</span></span>
                        <div class="flex justify-space-between mb-4 flex-wrap gap-4">
                          <el-button key="primary" type="primary" link >同意</el-button>
                          <el-button key="danger" type="danger" link >拒绝</el-button>
                        </div>
                      </div>
                      <div style="width: 100%; width: 100%;color: #000000;font-size: 16px;">新增一个测试用例,系统测试用例中有一个问题存在，我们需要修改为最新用例</div>
                      <div v-show="applyFroUnfold" style="font-size: 14px; color: #8a8a8a;">我会更桂林市解放asdf我会更桂林市解放asdf我会更桂林市解放asdf我会更桂林市解放asdf我会更桂林市解放asdf我会更桂林市解放asdf我会更桂林市解放asdf</div>
                      <div class="looks" @click="applyFroUnfold = !applyFroUnfold">
                        <span style="position: relative; right: 115px; top: 16px;">2023-11-11 13:23</span>
                        <div v-show="!applyFroUnfold">
                          <span>点击展开查看更多</span><span><el-icon><ArrowDownBold /></el-icon></span>
                        </div>
                        <div v-show="applyFroUnfold">
                          <span>点击收起内容</span><span><el-icon><ArrowUpBold /></el-icon></span>
                        </div>
                      </div>
                      <!-- 用户名 -->
                      <!-- 操作 -->
                      <!-- 内容 -->
                      <!-- 展开内容 -->
                    </div>
                    <div class="mes">2</div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </transition>
        </div>
      </el-header>
      <el-container>
        <div style="display: flex;flex-direction: row;">
          <el-aside class="left">
            <el-menu
              router
              unique-opened
              :default-active="$route.path"
              class="el-menu-vertical-demo"
              :collapse="menuSwitchStyleCollapse"
              @open="handleOpen"
              @close="handleClose"
            >
              <el-menu-item  v-for="i in userPathList[0].children" :key="i.path" :index="'/ts/' + i.path">
                <el-icon ><component :is='i.meta.icon'/></el-icon>
                <span>{{ i.meta.label }}</span>
              </el-menu-item>
            </el-menu>
          </el-aside>
          <!-- 给可滑动提供条件div -->
          <div class="menuSwitchHidd" ref="menuSwitchHiddRef">
            <!-- 可滑动div -->
            <div
              ref="menuSwitchRef"
              class="menuSwitchClass"
              @mouseenter="menuSwitchStyleOver"
              @mouseleave="menuSwitchStyleOut"
              @mousedown="startDrag"
              @mousemove="drag"
              @mouseup="endDrag"
              >
              <!-- 状态控制器所有功能 -->
              <div ref="lickListaaRef" class="lickListaa">
                <!-- 功能菜单 -->
                <div class="lickList" v-show="menuSwitchIconShow" @mouseup="menuSwitchIconClick">
                  <!-- 返回按钮 -->
                  <el-button
                    type="primary"
                    link
                    :icon="menuSwitchIcon"
                    style="border: none;"
                  />
                </div>
                <!-- 用的功能 -->
                <div v-for="i in routelist" :key="i" class="lickList" v-show="menuSwitchIconShow">{{ i }}</div>
              </div>
            </div>
          </div>
        </div>
        <el-main >
          <el-scrollbar>
            <div>
              <router-view/>
            </div>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
import { ref, shallowRef } from 'vue'
import {
  ArrowLeftBold,
  ArrowRightBold
} from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import jwtDecode from 'jwt-decode'
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
// 防抖函数
const delay = (function () {
  let timer = 0
  return function (callback, ms) {
    clearTimeout(timer)
    timer = setTimeout(callback, ms)
  }
})()
// token
const token:any = jwtDecode(window.localStorage.getItem('Authorization'))
// console.log(token.user.roleName)
// 路由
const router = useRouter()
const pathList = router.getRoutes().filter(v => v.meta.isShow)
const userPathList = pathList.filter(v => v.meta.label === '用户路由')
// Hdaer
const errorHandler = () => true
// 消息列表
const userMessage = ref(false)
// 点击查看更多信息展开
const applyFroUnfold = ref(false)
// 菜单控制器中的路由
const routelist = ref([1, 2, 3, 4, 5])
const lickListaaRef = ref(null)
// 菜单栏控制器标签
const menuSwitchHiddRef = ref(null)
// 菜单栏控制器标签子标签
const menuSwitchRef = ref(null)
// 菜单展开识别
const menuSwitchStyleCollapse = ref(true)
// 菜单Iconstyle
const menuSwitchIconShow = ref(false)
// 菜单Icon
const menuSwitchIcon = shallowRef(ArrowRightBold)
// 控制是否可以收起菜单栏控制器
const ctrlMenu = ref(true)
// 菜单开关移入
const menuSwitchStyleOver = () => {
  // 判断菜单栏是否打开，更换对应icon
  if (menuSwitchStyleCollapse.value) {
    // 替换展开图标
    menuSwitchIcon.value = ArrowRightBold
  } else {
    // 替换收回图标
    menuSwitchIcon.value = ArrowLeftBold
  }
  // 将控制器长度设置为60px
  menuSwitchRef.value.style.width = '60px'
  // 将高度设置为auot，方便根据子元素个数调节高度
  menuSwitchRef.value.style.height = 'auto'
  lickListaaRef.value.style.height = `${routelist.value.length * 58 + 58}px`
  delay(() => {
    // 显示控制器icon
    menuSwitchIconShow.value = true
  }, 50)
  // 设置时候可以收起菜单
  ctrlMenu.value = false
}
// 菜单开关移出
const menuSwitchStyleOut = () => {
  // console.log('等待3s后收起菜单控制器')
  ctrlMenu.value = true
  delay(() => {
    if (!ctrlMenu.value) { return }
    // console.log('收起菜单')
    menuSwitchIcon.value = ArrowLeftBold
    menuSwitchRef.value.style.width = '10px'
    menuSwitchRef.value.style.height = '58px'
    // 子菜单高度
    lickListaaRef.value.style.height = '0px'
    // 显示控制器icon
    menuSwitchIconShow.value = false
  }, 3000)
}
// 点击开关菜单
const menuSwitchIconClick = () => {
  menuSwitchStyleCollapse.value = !menuSwitchStyleCollapse.value
  // 判断菜单栏是否打开，更换对应icon
  if (menuSwitchStyleCollapse.value) {
    // 替换展开图标
    menuSwitchIcon.value = ArrowRightBold
  } else {
    // 替换收回图标
    menuSwitchIcon.value = ArrowLeftBold
  }
}
const dragging = ref(false)
const startY = ref(0)
const top = ref(0)
// 按下获取
const startDrag = (event) => {
  // 先获取菜单是否打开true为收起
  if (menuSwitchStyleCollapse.value) {
    menuSwitchHiddRef.value.style.left = '63px'
  } else {
    menuSwitchHiddRef.value.style.left = '250px'
  }
  // 打开/关闭 给背景设置left打开或关闭的值
  // console.log('按下')
  dragging.value = true
  startY.value = event.clientY - top.value
  delay(() => {
    // console.log('启动遮罩')
    menuSwitchRef.value.style.top = `${menuSwitchRef.value.style.top - 100}px`
  }, 200)
}
// 滑动方法
const drag = (event) => {
  // console.log('获取当前元素信息')
  // 判断是否按下的控制器
  if (!dragging.value) return
  top.value = event.clientY - startY.value
  if (top.value < 0) {
    return
  }
  menuSwitchRef.value.style.top = `${top.value}px`
  // console.log('设置top值' + top.value)
  // 超过三秒就设置一个遮罩
}
// 抬起方法
const endDrag = () => {
  // console.log('抬起')
  // 鼠标移动不触发事件
  dragging.value = false
}
// 全局监控对象
document.addEventListener('mouseup', endDrag)
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style scoped lang="scss">
.lickList{
  height: 58px;
  width: 58px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.menuSwitchHidd{
  // width: 100vw;
  // background: #8f9ca88a;
  height: 100%;
  position: inherit;
  // 子元素和自己不可被鼠标选择
  user-select: none;
}
.menuSwitchClass{
  position: sticky;
  top: 30px;
  transition: width 300ms ease-in-out;
  width: 10px;
  height: 58px;
  background: white;
  margin-top: 5px;
  border-radius: 0px 10px 10px 0px;
  box-shadow: 0 2px 8px 0 rgba(7, 12, 20, 0.12);
  z-index: 9999;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.lickListaa{
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 0px;
  transition: height 400ms ease-in-out;
}
.el-menu-vertical-demo{
  height: calc(100vh - 100px);
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
}
.common-layout{
  background-color: #f9f9f9;
}
.el-container{
  height: calc(100vh - 50px);
}
.header {
  width: auto;
  height: 50px;
  background: #FFF;
  color: #000;
  z-index: 999;
  box-shadow: 0 4px 6px 0 rgba(8, 14, 26, 0.04), 0 1px 10px 0 rgba(8, 14, 26, 0.05), 0 2px 4px -1px rgba(8, 14, 26, 0.06);
  display: flex;
  align-items: center;
  .header-title{
    width: 30%;
  }
  .header-a{
    width: 70%;
    height: auto;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .header-a > * {
    margin-right: 15px;
  }
  .userName{
    margin-left: 10px;
    display: flex;
    flex-direction: row;
    align-items: center;
    width: auto;
    height: auto;
  }
  .messageRow{
      position: fixed;
      right: 20px;
      top: 70px;
    .messageCol{
      width: auto;
      height: auto;
      .messageCard{
        height: 500px;
        width: 400px;
        display: flex;
        justify-content: center;
        overflow: hidden;
        .mes{
          width: 330px;
          height: auto;
          display: flex;
          align-items: center;
          padding: 20px 20px 5px 20px;
          display: flex;
          flex-direction: column;
          border: 1px solid #ededed;
          border-radius: 10px 10px 10px 10px;
          transition: height 400ms ease-in-out;
          margin-bottom: 10px;
          margin-left: 30px;
          .mes-title{
            display: flex;
            justify-content: space-between;
            width: 100%;
            .mesUser{
              margin-left: 20px;
              font-size: 14px;
              color: #8a8a8a;
            }
          }
          .looks{
            margin: 10px 0px 0px 0px;
            font-size: 12px;
            color: #b0b0b0;
            display: flex;
            flex-direction: column;
            align-items: center;
            cursor: pointer;
          }
        }
        .mes > * {
          margin-bottom: 10px;
        }
      }
    }
  }
}
.left{
  // width: 180px;
  box-shadow: 0 2px 8px 0 rgba(7,12,20,0.12);
  // overflow-y: scroll;
}
.el-aside{
  width: auto;
}
.el-divider__text.is-left {
  color: #d1d1d1;
}
</style>
