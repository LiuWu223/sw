<template>
  <div class="projectHomeClass">
    <!-- 头部 -->
    <div class="ptHmHead">
      <h2 class="ptHmHeadH">项目，<span style="color: #ff3232;">注意！！！非常重要：只能新建一个项目，点击开始按照最新下发任务进行测试，仅测试最新下发的测试任务</span></h2>
      <div style="margin-right: 20px;">
        <el-button type="primary" :icon="Plus" @click="projectAdd">新建项目</el-button>
      </div>
    </div>
    <!-- 内容 -->
    <div class="ptHmContent">
      <div class="ptHmConMenu">
        <div class="ptHmConMenuLabel">当前选择项目<el-icon style="margin-left: 5px;" size="16"><ArrowDown /></el-icon></div>
        <el-menu
          style="border: none;"
          unique-opened
          :default-active="msMenu"
          @open="ProjectOpen"
        >
          <el-menu-item v-for="project in projectMenus" :key="project.projectId" :index="project.projectId.toString()">
            <span class="menuTimeSpan"><el-icon><Folder /></el-icon>{{ project.projectName }}</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="taskList">
        <div class="taskListConLabel">
          <h4 class="labelH">
            <div>测试任务列表</div>
          </h4>
          <div class="labelIcon">
            <el-button type="primary" :icon="CaretRight" @click="cmPath">开始</el-button>
            <el-button type="primary" :icon="Plus" @click="taskAdd">新建任务</el-button>
          </div>
        </div>
        <!-- 任务列表 -->
        <el-scrollbar class="taskScrollbar" max-height="650px" style="width: 100% !important;">
          <!-- 当前执行任务 -->
          <div style="width: 10vh%;height: auto;">
            <div ref="taskRef" class="tasks" v-for="(tasks, index) in taskMenus" :key="index">
              <div class="taskOb">
                <div class="taskD">
                  <p>任务名称</p>
                  <p>{{ tasks.taskName }}</p>
                </div>
                <div class="taskD">
                  <p>任务类型</p>
                  <p>{{ tasks.taskType }}</p>
                </div>
                <div class="taskD">
                  <p>预估时间</p>
                  <p>{{ tasks.estimateTime }}</p>
                </div>
                <div class="taskD">
                  <p>实际时间</p>
                  <p>{{ tasks.realityTime }}</p>
                </div>
                <div class="taskD">
                  <p>执行人</p>
                  <p>{{ tasks.createBy }}</p>
                </div>
                <div class="taskD">
                  <p>优先级</p>
                  <p v-if="tasks.dictIdFirst === '低'" style="display: flex; align-items: center; justify-content: center;color: #67c23a;font-family: monospace;">{{ tasks.dictIdFirst }}<el-icon size="18"><Bottom /></el-icon></p>
                  <p v-if="tasks.dictIdFirst === '中'" style="display: flex; align-items: center; justify-content: center;color: #eda83c;font-family: monospace;">{{ tasks.dictIdFirst }}<el-icon size="18"><SemiSelect /></el-icon></p>
                  <p v-if="tasks.dictIdFirst === '高'" style="display: flex; align-items: center; justify-content: center;color: #ff3232;font-family: monospace;">{{ tasks.dictIdFirst }}<el-icon size="18"><Top /></el-icon></p>
                </div>
                <div class="taskD">
                  <p>状态</p>
                  <p v-if="tasks.dictIdType === '等待'"><el-tag class="ml-2" type="info">{{ tasks.dictIdType }}</el-tag></p>
                  <p v-if="tasks.dictIdType === '完成'"><el-tag class="ml-2" type="success">{{ tasks.dictIdType }}</el-tag></p>
                  <p v-if="tasks.dictIdType === '失败'"><el-tag class="ml-2" type="danger">{{ tasks.dictIdType }}</el-tag></p>
                  <p v-if="tasks.dictIdType === '暂停'"><el-tag class="ml-2" type="warning">{{ tasks.dictIdType }}</el-tag></p>
                </div>
                <div>
                  <p @click="taskShowDetails(index, tasks.taskId)" ref="buttonRef" style="color: rgb(64, 158, 255);cursor: pointer; ">展开</p>
                </div>
              </div>
              <transition name="el-zoom-in-top">
                <div v-show="tasks.show" style="width: 90%; height: 100%; margin-bottom: 10px;">
                  <div style="display: flex; justify-content: space-between; width: 60%;">
                    <p>测试任务名称：{{ taskUser.taskName }}</p>
                    <p>优先级：{{ taskUser.dictIdFirst }}</p>
                    <p>状态：{{ taskUser.dictIdType }}</p>
                    <p>下发人：{{ taskUser.createBy }}</p>
                  </div>
                  <div>
                    <p>测试类型：{{ taskUser.taskType }}</p>
                    <p>任务项:</p>
                    <el-table v-if="taskCases.length > 0" :data="taskCases" border style="width: 60%;height: 180px;">
                      <el-table-column align="center" prop="caseName" label="测试用例名称" />
                      <el-table-column align="center" prop="caseUrl" label="URL"/>
                      <el-table-column align="center" prop="caseType" label="状态" width="80" />
                    </el-table>
                    <p v-else>
                      <el-input disabled  size="large" style="width: 200px;" :suffix-icon="CaretRight" v-model="ttFrom.head" placeholder="始" />
                      <el-input disabled  size="large" style="width: 200px;margin-left: 20px;" :prefix-icon="CaretRight" v-model="ttFrom.tail" placeholder="终" />
                    </p>
                  </div>
                  <p>测试设备：</p>
                  <div style="display: flex;">
                    <div v-for="devi in taskUser.devices" :key="devi" class="testDeviceClass">
                      <p>机型：</p>
                      <p>SN：{{ devi }}</p>
                      <p>系统版本：</p>
                    </div>
                  </div>
                  <div style="margin-top: 20px; float: right;">
                    <el-row class="mb-4">
                      <el-button type="primary" link @click="taskEditor(tasks.taskId)">编辑</el-button>
                    </el-row>
                  </div>
                </div>
              </transition>
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
    <projectsAdd ref="projectAddRef" @reload="loadUserProjectData"></projectsAdd>
  </div>
</template>
<script lang="ts" setup>
import projectsAdd from './projectsAdd.vue'
import { ref, onMounted } from 'vue'
import {
  Edit,
  Plus,
  CaretRight
} from '@element-plus/icons-vue'
import { tsCaseApi } from '@/api/tsApi/projects-api'
import { tsTaskApi } from '@/api/tsApi/tsTask-api'
import jwtDecode from 'jwt-decode'
import axios from 'axios'
/** 引入防抖函数 */
import { useDebounce } from '@/utils/debounce'

const tokens:any = jwtDecode(localStorage.getItem('Authorization'))
const taskRef = ref(null)
const buttonRef = ref(null)
// 项目列表
const projectMenus = ref([])
// 任务列表
const taskMenus = ref([])
const taskUser:any = ref({})
const taskCases:any = ref([])
const ttFrom = {
  head: '',
  tail: ''
}
// 展开收起任务
const taskShowDetails = (clickIndex, taskId) => {
  taskMenus.value.forEach((data, index) => {
    if (index === clickIndex) {
      data.show = !data.show
      if (data.show) {
        buttonRef.value[index].innerText = '收起'
        taskRef.value.forEach((item, taskRefIndex) => {
          if (index === taskRefIndex) {
            item.style.height = '670px'
          }
        })
        tsTaskApi.selectTaskByTaskId(taskId).then(data => {
          if (data.data.data.task.tripartite !== '') {
            const ttList = data.data.data.task.tripartite.split(',')
            ttFrom.head = ttList[0]
            ttFrom.tail = ttList[1]
          }
          taskCases.value = data.data.data.taskCase
          if (data.data.data.task.devices !== '') {
            data.data.data.task.devices = data.data.data.task.devices.split(',')
          } else {
            data.data.data.task.devices = []
          }
          taskUser.value = data.data.data.task
        }).catch(err => {
          console.log(err)
        })
      } else {
        buttonRef.value[index].innerText = '展开'
        taskRef.value.forEach((item, taskRefIndex) => {
          if (index === taskRefIndex) {
            item.style.height = '95px'
          }
        })
      }
    }
  })
}
const projectAddRef = ref()
// 打开项目新增
const projectAdd = () => {
  projectAddRef.value.prjectAdd()
}
// 打开任务新增
const taskAdd = () => {
  projectAddRef.value.taskAdd()
}

// 启动任务
const cmPath = async () => {
  await axios.post('http://192.168.3.85:5000/gitTool/cmPath', {
    ip: tokens.user.ipv, fileName: 'auto_test', path: tokens.user.path
  }).then(data => {
    console.log(data)
  }).catch(err => {
    console.log(err)
  })
}

const msMenu = ref('')
// 查询用户项目
const loadUserProjectData = async () => {
  await tsCaseApi.selectProjectByUserId(tokens.user.id).then(data => {
    projectMenus.value = data.data.data
    msMenu.value = projectMenus.value[0].projectId.toString()
  }).catch(err => {
    console.log(err)
  })
}

// 调用子页面函数projectAddRef.value.方法名
// 选择用户项目时调用查询任务
const ProjectOpen = async (key: string) => {
  await tsCaseApi.selectTaskByProjectId(key).then(data => {
    localStorage.setItem(
      'Prs', key
    )
    taskMenus.value = data.data.data
    // 添加任务显示标识
    taskMenus.value.forEach((data, index) => {
      taskMenus.value[index].show = false
    })
  }).catch(err => {
    console.log(err)
  })
}
// 任务编辑
const taskEditor = (taskId) => {
  projectAddRef.value.taskEditor(taskId)
}
/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadUserProjectData()
})
</script>
<style>
.el-scrollbar__wrap--hidden-default{
  width: 100% !important;
}
</style>
<style scoped lang="scss">
.projectHomeClass{
  display: flex;
  flex-direction: column;
  .ptHmHead{
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: white;
    border-radius: 10px 10px 10px 10px;
    .ptHmHeadH{
      margin-left: 20px;
      font-size: 20px;
      color: #000;
    }
  }
  .ptHmContent{
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    border-radius: 10px;
    .ptHmConMenu{
      height: 740px;
      width: 20%;
      background: white;
      border-radius: 10px;
      .ptHmConMenuLabel{
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
          width: 100%;
          transition: height 250ms ease-in-out;
          height: 95px;
          display: flex;
          flex-direction: column;
          align-items: center;
          border-radius: 10px;
          margin-bottom: 20px;
          background: white;
        }
      }
    }
  }
}
.taskOb{
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 90%;
  height: 55px
}
</style>
<style scoped>
.testDeviceClass{
  width: 200px;
  margin: 10px;
  height: 85px;
  background: white;
  border-radius: 10px;
  box-shadow: rgb(239 239 239) 0px 0px 6px 1px;
  font-size: 12px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  padding: 15px;
}
.testDeviceClass p{
  margin: 0;
}
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
