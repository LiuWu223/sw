<template>
  <div class="testFramHomeClass">
    <!-- 头部 -->
    <div class="tfHmHead">
      <h2 class="tfHmHeadH">测试框架</h2>
      <div style="margin-right: 20px;">
        <el-button type="primary" :icon="Plus" @click="testFramAdd">新建测试框架</el-button>
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
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>测试框架分类1</span>
          </el-menu-item>
          <el-menu-item index="2">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>测试框架分类2</span>
          </el-menu-item>
          <el-menu-item index="3">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>测试框架分类3</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="taskList">
        <div class="taskListConLabel">
          <h4 class="labelH">
            <div>测试框架列表</div>
          </h4>
          <div class="labelIcon">
            <el-icon size="20"><Filter /></el-icon>
          </div>
        </div>
        <!-- 测试框架列表 -->
        <el-scrollbar
          class="taskScrollbar"
          width="100%"
          max-height="650px"
          height="650px">
          <!-- 当前执行任务 -->
          <div style="width: 10vh%;height: auto; display: flex; flex-wrap: wrap;">
            <div class="tasks" v-for="ts in tasks" :key="ts.id">
              <div class="gitName">
                <el-avatar shape="square" :size="35" :src="'auto_sw_admin_war'" @error="errorAvater" />
                <div style="
                  font-size: 15px;
                  margin-left: 15px;
                  color: black;">{{ ts.gitName }}</div>
                <el-tag v-if="ts.ifDow" style="margin-left: 50px;" class="ml-2" type="success">已下载</el-tag>
                <el-tag v-else style="margin-left: 50px;" class="ml-2" type="info">未下载</el-tag>
              </div>
              <div class="gitRefer">
                描述：{{ ts.gitRefer }}
              </div>
              <div class="gitcCeate">
                <div style="font-size: 12px; margin-right: 12px; color: #c2c2c2;">{{ ts.createTime }}</div>
                <el-button key="primary" type="primary" link >查看使用文档</el-button>
                <el-button key="primary" type="primary" link @click="gitClone(ts.gitUrl)">下载</el-button>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
    <testFramAddView ref="testFramAddRef"></testFramAddView>
  </div>
</template>
<script lang="ts" setup>
import testFramAddView from './testFramAdd.vue'
import { ref, onMounted } from 'vue'
import { tolGitApi } from '@/api/tolApi/tolGit-api'
import jwtDecode from 'jwt-decode'
import {
  Plus
} from '@element-plus/icons-vue'
import axios from 'axios'
const testFramAddRef = ref()
const testFramAdd = () => {
  testFramAddRef.value.testFramAdd()
}
const errorAvater = () => true
// 调用子页面函数testFramAddRef.value.方法名
const testFramOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const tokens:any = jwtDecode(localStorage.getItem('Authorization'))
const tasks = ref([])
const pageGitFrom = {
  // 当前页
  currentPage: 1,
  // 页大小
  pageSize: 10,
  // GIT名称
  gitName: ''
}
// 下载测试框架
const gitClone = async (url) => {
  await axios.post('http://192.168.3.85:5000/gitTool/gitClone', {
    ip: tokens.user.ipv, gitPath: url
  }).then(data => {
    console.log(data)
  }).catch(err => {
    console.log(err)
  })
}
// 检查项目是否下载
const dirExit = () => {
  let ifdows = false
  tasks.value.forEach(async itme => {
    await axios.post('http://192.168.3.85:5000/gitTool/assertGitFile', {
      ip: tokens.user.ipv, fileName: itme.gitName
    }).then(data => {
      ifdows = data.data.success
    }).catch(err => {
      console.log(err)
    })
    const ifDow = 'ifDow'
    if (ifdows) {
      itme[ifDow] = true
    } else {
      itme[ifDow] = false
    }
  })
}
const loadGitData = async () => {
  await tolGitApi.pageGit(pageGitFrom).then(data => {
    // 遍历查询下载好的项目
    tasks.value = data.data.data.records
    dirExit()
  }).catch(err => {
    console.log(err)
  })
}
onMounted(() => {
  // 加载表格数据
  loadGitData()
})
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
      .taskListConLabel{
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 70px;
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
      .taskScrollbar{
        width: 100%;
        margin-top: 20px;
        height: 650px;
        display: flex;
        justify-content: center;
        .tasks{
          height: 100px;
          width: 250px;
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
          .gitName{
            display: flex;
            width: 100%;
            align-items: center;
            height: 50px;
          }
          .gitRefer{
            width: 100%;
            height: 40px;
            font-size: 12px;
            color: #8d8d8d;
          }
          .gitcCeate{
            width: 100%;
            height: 20px;
            display: flex;
            align-items: center;
            justify-content: flex-end;
          }
        }
      }
    }
  }
}
</style>
<style scoped>
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
  width: 300px;
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
