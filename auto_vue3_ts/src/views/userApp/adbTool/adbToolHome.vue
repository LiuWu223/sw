<template>
  <div class="testFramHomeClass">
    <!-- 头部 -->
    <div class="tfHmHead">
      <h2 class="tfHmHeadH">ADB功能</h2>
      <div style="margin-right: 20px;">
        <el-button type="primary" :icon="Plus" @click="testFramAdd">新建ADB功能</el-button>
      </div>
    </div>
    <!-- 内容 -->
    <div class="tfHmContent">
      <div class="tfHmConMenu">
        <div class="tfHmConMenuLabel">当前选择测试框架<el-icon style="margin-left: 5px;" size="16"><ArrowDown /></el-icon></div>
        <el-menu
          style="--bg-color:'#d43291'; --text-colo: '#d43291'; --active-color: '#9bcdff';border: none;"
          unique-opened
          default-active="1"
          @open="testFramOpen"
        >
          <el-menu-item index="1">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>三方自动化测试</span>
          </el-menu-item>
          <el-menu-item index="2">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>系统测试</span>
          </el-menu-item>
          <el-menu-item index="3">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>测试</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="taskList">
        <div class="taskListConLabel" style="justify-content: flex-start;">
          <h4 class="labelH">
            <div>功能列表</div>
          </h4>
          <div class="labelIcon" style="margin-left: 20px;width: 300px;">
            <el-input
              v-model="input1"
              class="w-50 m-2"
              placeholder="搜索功能"
              :suffix-icon="Search"
            />
          </div>
        </div>
        <div class="adbToolList">
          <el-scrollbar style="display: flex;align-items: center;">
            <div class="scrollbar-flex-content">
              <div v-for="item in 3" :key="item" >
                <el-checkbox v-model="checked4" label="Option2" border style="margin-right: 20px; width: 110px; height: 100px;">
                  <div style="font-size: 12px; width: 75px; height: 75px;    display: flex; flex-direction: column; align-items: center; justify-content: space-evenly;">应用版本获取<el-icon size="40"><EditPen /></el-icon></div>
                </el-checkbox>
              </div>
              <div class="mt-4">
              </div>
            </div>
          </el-scrollbar>
        </div>
        <div class="taskListConLabel">
          <h4 class="labelH">
            <div>设备列表</div>
          </h4>
        </div>
        <!-- 测试框架列表 -->
        <div style="width: 10vh%;height: 410px; display: flex; flex-wrap: wrap;">
          <div class="tasks" v-for="i in tasks" :key="i.id">
            <div style="
              display: flex;
              width: 100%;
              background-color: antiquewhite;
              border-radius: 10px;
              align-items: center;
              height: 175px;">
              <!-- <el-avatar shape="square" :size="80" :src="'auto_sw_admin_war'" @error="errorAvater" /> -->
            </div>
            <div style="
              height: 220px;
              width: 100%;
              display: flex;
              flex-direction: row;
              justify-content: space-between;">
              <div style="
                width: 46%;
                height: 91%;
                padding: 10px;
                word-wrap: break-word;
                font-size: 12px;">
                <el-scrollbar>
                  <p>型号: <span>XTW31-E</span></p>
                  <p style="margin-bottom: 0;">系统版本:</p>
                  <span>seewoOS_XTW31_20230200_V3.3.3</span>
                  <p>配置: <span>6+128</span></p>
                  <p>配置: <span>6+128</span></p>
                  <p>配置: <span>6+128</span></p>
                  <p>配置: <span>6+128</span></p>
                </el-scrollbar>
              </div>
              <div style="
                width: 46%;
                height: 91%;
                padding: 10px;
                word-wrap: break-word;
                font-size: 12px;word-wrap: break-word;">
                 <el-scrollbar>
                    <p>获取版本 <el-icon><Loading /></el-icon></p>
                    <p>关机 <el-icon><More /></el-icon></p>
                    <p>重启 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                    <p>熄屏 <el-icon><More /></el-icon></p>
                  </el-scrollbar>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <testFramAddView ref="testFramAddRef"></testFramAddView> -->
  </div>
</template>
<script lang="ts" setup>
// import testFramAddView from './testFramAdd.vue'
import { ref } from 'vue'
import {
  Edit,
  Plus,
  Search
} from '@element-plus/icons-vue'
const input1 = ref('')
const testFramAddRef = ref()
const testFramAdd = () => {
  testFramAddRef.value.testFramAdd()
}
const errorAvater = () => true
// 调用子页面函数testFramAddRef.value.方法名
const testFramOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const tasks = ref([
  {
    id: 1,
    taskName: '自动化测试',
    estimateTime: '1天2时',
    realityTime: '1天5时',
    userName: '自动化测试',
    first: '高',
    type: '完成'
  },
  {
    id: 2,
    taskName: '自动化测试',
    estimateTime: '1天2时',
    realityTime: '1天5时',
    userName: '自动化测试',
    first: '低',
    type: '失败'
  },
  {
    id: 3,
    taskName: '自动化测试',
    estimateTime: '1天2时',
    realityTime: '1天5时',
    userName: '自动化测试',
    first: '低',
    type: '失败'
  }
])
</script>
<style scoped lang="scss">
.testFramHomeClass{
  display: flex;
  flex-direction: column;
  .tfHmHead{
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: white;
    border-radius: 10px 10px 10px 10px;
    .tfHmHeadH{
      margin-left: 20px;
      font-size: 20px;
      color: #000;
    }
  }
  .tfHmContent{
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    border-radius: 10px;
    .tfHmConMenu{
      height: 740px;
      width: 20%;
      background: white;
      border-radius: 10px;
      .tfHmConMenuLabel{
        font-size: 14px;
        color: #2d2d2d;
        margin: 10px 5px 0px 20px;
        display: flex;
        align-items: center;
        height: 50px;
        .menuTimeSpan{
          font-size: 16px;
        }
      }
    }
    .taskList{
      width: 78%;
      height: 76vh;
      border-radius: 10px;
      .adbToolList{
        padding: 20px;
        margin-top: 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 100px;
        width: 97%;
        background: #fff;
        border-radius: 10px;
        margin-bottom: 20px;
      }
      .taskListConLabel{
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 50px;
        width: 100%;
        background: #fff;
        border-radius: 10px;
        .labelH{
          margin-left: 20px;
        }
        .labelIcon{
          margin-right: 20px;
        }
      }
      .tasks{
          height: 410px;
          width: 290px;
          background: white;
          padding: 20px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          border-radius: 10px;
          margin-bottom: 20px;
          margin-left: 20px;
          flex-direction: column;
          padding-bottom: 10px;
          margin-top: 20px;
        }
    }
  }
}
</style>
<style>
.el-checkbox__input {
  display: none !important;
}
.scrollbar-flex-content {
  display: flex;
  align-items: center;
}
.scrollbar-demo-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-danger-light-9);
  color: var(--el-color-danger);
}
.taskD{
  display: flex;
  flex-direction: column;
  height: 50px;
  justify-content: space-between;
  align-items: center;
}
.taskD p:nth-child(1){
  font-size: 12px;
  color: #aaaaaa;
  padding: 0;
  margin: 0;
}
.taskD p:nth-child(2){
  font-size: 14px;
  color: #000000;
  padding: 0;
  margin: 0;
}
</style>
