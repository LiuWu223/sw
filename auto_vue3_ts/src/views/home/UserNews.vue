<template>
  <div class="user-news-main">
    <div class="user-news-main-persional-data">
      <div class="user-news-main-persional-data-tile">
        <div style="width: 60px;height: 60px;border-radius: 50%;background-color: aqua;display: flex;justify-content: center;align-items: center;">头像</div>
        <div style="margin-left: 20px;width: 50%;">
          <p style="margin-top: 10px;font-size: 18px;color: #333333;margin-bottom: 0px;">名称:<span>{{ newsForm.userName }}</span></p>
          <p id="input1" style="display: initial;">自述:<span>{{ newsForm.userName }}</span></p>
        </div>
        <div>
          <el-tag style="margin-left: 20px;margin-bottom: 60px;"  size="small">超级管理员</el-tag>
        </div>
      </div>
      <div class="user-news-main-persional-data-test-data">
        <div class="user-news-main-persional-data-test-data-button">
          <el-button sizi="large" @click="router.push({ path: '/existTestCases' })">已有测试任务</el-button>
          <el-button sizi="large" @click="router.push({ path: '/addTestCases' })">新增测试任务</el-button>
          <el-button sizi="large" @click="router.push({ path: '/myManageTestCases' })">我的管理用例</el-button>
          <el-button sizi="large" @click="router.push({ path: '/collectTestCases' })">收藏修改用例</el-button>
          <el-button sizi="large" @click="router.push({ path: '/taskOverview' })">测试任务总览</el-button>
        </div>
      </div>
    </div>
    <div class="user-news-main-test-data">
      <el-empty v-if="isPath" description="description" />
      <div style="width: 100%;height: 100%;" v-if="!isPath">
        <router-view v-slot="{ Component }">
          <transition name="fang">
            <div><component :is="Component" /></div>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, defineExpose, watch } from 'vue'
import { useRouter } from 'vue-router'

const isPath = ref(true)

const router = useRouter()
watch(
  () => router.currentRoute.value,
  async (newValue) => {
    console.log(newValue.path)
    if (newValue.path !== '/existTestCases' && newValue.path !== '/addTestCases' && newValue.path !== '/myManageTestCases' && newValue.path !== '/collectTestCases' && newValue.path !== '/taskOverview') {
      isPath.value = true
    } else {
      isPath.value = false
    }
  }
)
const newsShow = ref(false)
const newsForm = ref({
  userName: '',
  userAccount: '',
  userAvatar: '',
  userIntroduce: ''
})

const openDialog = async (userId?: string) => {
  newsShow.value = true
  console.log(userId)
}
const saveNewsUser = async () => {
  console.log(1)
}
const closeDialog = () => {
  // 初始化form表单的双向绑定的值
  newsForm.value = {
    userName: '',
    userAccount: '',
    userAvatar: '',
    userIntroduce: ''
  }
}
// 向外暴露组件的属性或函数
defineExpose({
  openDialog
})
</script>
<style lang="scss">
.user-news-main{
  display:flex;background-color: #fbfbfb;height: auto;flex-direction: column;
  align-items: center;
}
.user-news-main-persional-data{
  background-color: #ffffff;
  box-shadow: 0px 0px 10px 0px #c6c6c6;
  height: 200px;
  display: flex;
  width: 96%;
  margin-top: 20px;
  border-radius: 10px;
  display: flex;
  align-items: center;
}
.user-news-main-persional-data-tile{
  display: flex;
  align-items: center;
  width: 45%;
  justify-content: center;
}
.user-news-main-persional-data-test-data{
  width: 55%;
  display: flex;
  flex-wrap: wrap;
  height: 70%;
}
.user-news-main-persional-data-test-data-button{
  display: flex;
  flex-wrap: wrap;
  width: 93%;
  justify-content: right;
  align-items: center;
}
.user-news-main-test-data{
  background-color: #ffffff;
  box-shadow: 0px 0px 10px 0px #c6c6c6;
  height:auto;
  display: flex;
  width: 96%;
  margin-top: 20px;
  border-radius: 10px;
  align-items: center;
  margin-bottom: 20px;
  justify-content: center;
}
.elColClass{
  display: flex;
  width: auto;
  height: auto;
  align-items: center;
}
.colCard{
  display: flex;
  width: 430px;
  height: 140px;
  align-items: center;
  margin: 20px;
}
#input1{
  color: #d1d1d1;
  font-size: 14px;
  border: 0;
  resize: none;
  padding: 5px 0;
  overflow-y: hidden;
  padding: 0px;
  border: none !important;
  box-shadow: none !important;
}
#input2{
  border: none !important;
  box-shadow: none !important;
  padding: 0px;
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
