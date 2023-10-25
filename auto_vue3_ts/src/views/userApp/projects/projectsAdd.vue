<template>
  <div class="projectAddClass">
    <el-drawer size="900" v-model="projectShow" :direction="direction" :show-close=false>
      <template #header>
        <h1>新增项目</h1>
      </template>
      <template #default>
        <el-form :model="projectForm">
          <div style="display: flex; flex-direction: column;">
            <el-form-item style="width: 300px;" label="项目名称" prop="projectName">
              <el-input :prefix-icon="Reading" size="large" v-model="projectForm.projectName" :maxlength="20" :show-word-limit="true" />
            </el-form-item>
            <el-form-item style="width: 300px;" label="项目描述" prop="projectRefer">
              <el-input :prefix-icon="Reading" size="large" v-model="projectForm.projectRefer" :maxlength="20" :show-word-limit="true" />
            </el-form-item>
          </div>
        </el-form>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="projectShow = false">取消</el-button>
          <el-button type="primary" @click="savePrject()">保存</el-button>
        </div>
      </template>
    </el-drawer>
    <el-drawer size="1000" v-model="taskShow" :direction="direction" :show-close=false>
      <template #header>
        <h1>新增任务</h1>
      </template>
      <template #default>
        <el-form :model="taskForm">
          <div style="display: flex;align-items: center;">
            <el-form-item style="width: 300px;" label="任务名称" prop="projectName">
              <el-input :prefix-icon="Reading" size="large" v-model="taskForm.taskName" :maxlength="20" :show-word-limit="true" />
            </el-form-item>
            <el-form-item style="width: 170px;margin-left: 30px;" label="优先级" prop="dictIdFirst">
              <el-select
              v-model="taskForm.dictIdFirst"
              placeholder="优先级"
              @focus="selectFirst"
              size="large"
              >
                <el-option
                  v-for="item in firstList"
                  :key="item.id"
                  :label="item.dictContent"
                  :value="item.dictContent"
                />
              </el-select>
            </el-form-item>
          </div>
        </el-form>
        <div>
          <div style="display: flex;align-items: center;">
            <p style="height: 26px;">测试内容:</p>
          </div>
          <div>
            <div style="display: flex; flex-direction: initial; height: 400px;">
              <div>
                <el-scrollbar height="100%" style="width: 150px;">
                  <div class="caseModuleName">用例模块</div>
                  <el-menu
                    style="border: none;"
                    unique-opened
                    :default-active="caseModuleActive"
                    @open="moduleOpen"
                  >
                    <el-menu-item :disabled="disableds" v-for="item in caseTableDataRef" :key="item.id" :index="item.id.toString()" style="display: flex;align-items: center;">
                      <el-icon><Discount /></el-icon>
                      <p>{{ item.moduleName }}</p>
                    </el-menu-item>
                  </el-menu>
                </el-scrollbar>
              </div>
              <el-table
                v-loading="loading"
                v-if="taskShow"
                :data="caseTableDataList"
                style="margin-bottom: 20px; height: 100%;width: 600px"
                @select="tsModuleChange"
                @select-all="tsModuleSeleceAll"
                @selection-change="stopClick"
                :row-key="rowKeyFunc"
                :cell-style="cellStyle"
                highlight-current-row
                ref="tablcref"
                border
                :indent=50>
                <el-table-column align="center" type="selection" reserve-selection/>
                <el-table-column v-if="!tripartiteTask" align="center" prop="id" editor="input" label="ID"/>
                <el-table-column v-if="!tripartiteTask" align="center" prop="name" label="测试项/测试用例"/>
                <el-table-column v-if="!tripartiteTask" align="center" prop="url" label="描述/url"/>
                <el-table-column v-if="!tripartiteTask" align="center" prop="createTime" label="创建时间" />
                <el-table-column v-if="tripartiteTask" align="center" prop="createTime" label="输入开始 / 结束编号">
                  <el-input controls="false" size="large" type="number" style="width: 200px;" :suffix-icon="CaretRight" v-model="ttFrom.head" placeholder="始" />
                  <el-input controls="false" size="large" type="number" style="width: 200px;margin-left: 20px;" :prefix-icon="CaretRight" v-model="ttFrom.tail" placeholder="终" />
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>
        <div style="border-bottom: 1px solid #e8e8e8;">
          <p>测试设备:<el-button @click="getDevice" style="font-size: 12px;margin-left: 20px;" size="small">刷新<img style="width: 15px;height: 15px;" src="../../../assets/images/ss.png" /></el-button></p>
          <!-- <el-button @click="cmPath">cd一下</el-button> -->
          <div class="scrollbar-flex-content" ref="sreollber">
            <el-checkbox-group @change="tsTaskDeviceClick" v-model="tsTaskDevices" size="large" style="display: flex; width: 630px; flex-wrap: wrap;">
              <el-checkbox v-for="item in tsDevicesData" :key="item.device" :label="item.device" border
              style="background: #fff; box-shadow: 0px 0px 6px 1px #e7e7e7; border-radius: 10px; display: flex; justify-content: center;
                align-items: center; width: 270px; height: 120px; padding: 10px; overflow-wrap: break-word; font-size: 12px; margin: 20px;">
                <el-scrollbar>
                  <p style="font-size: 12px;">型号: <span>{{ item.type }}</span></p>
                  <p style="margin-bottom: 0;font-size: 12px;">系统版本:</p>
                  <span style="font-size: 12px;">{{ item.systemVersion }}</span>
                  <p style="font-size: 12px;">SN: <span>{{ item.device }}</span></p>
                </el-scrollbar>
              </el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="taskShow = false">取消</el-button>
          <el-button type="primary" @click="saveTask()">保存</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import { defineExpose, ref, watch } from 'vue'
import { Reading, CaretRight } from '@element-plus/icons-vue'
import { tsCaseApi } from '@/api/tsApi/projects-api'
import jwtDecode from 'jwt-decode'
import { syDictApi } from '@/api/syApi/syDict-api'
import { tsModuleApi } from '@/api/tsApi/tsModule-api'
import { tsTaskApi } from '@/api/tsApi/tsTask-api'
import { ElMessage } from 'element-plus'
import axios from 'axios'
/** 引入防抖函数 */
import { useDebounce } from '@/utils/debounce'

const loading = ref(true)
const caseModuleActive = ref('1')
const tablcref = ref(null)
// 测试用例表数据
let caseTableData = []
const caseTableDataList = ref([])
// 测试用例选择列表
const caseTableDataRef = ref([])
// 给数据表设置UUID
const rowKeyFunc = (row) => {
  return row.id + row.name
}
const ttFrom = ref({
  head: '',
  tail: ''
})
// 切换三方测试和系统测试
const caseTypeClick = async () => {
  // 查询系统数据
  await tsModuleApi.selectModulesAndSectionAndCase().then(data => {
    // 添加模块
    caseTableData = data.data.data
    // 添加菜单
    caseTableDataRef.value = caseTableData
    loading.value = false
  }).catch(err => {
    console.log(err)
  })
}
const cellStyle = (data) => {
  if (!data.row.children) {
    return {
      background: '#ecffe5'
    }
  }
}
// 定义是否选择的三方测试
const tripartiteTask = ref(false)
// 定义用户选择的测试任务
let userTaskFroms = []
const disableds = ref(false)
// 点击选择测试的模块
const moduleOpen = async (key: number) => {
  tripartiteTask.value = false
  caseTableDataList.value = []
  caseTableData.forEach(itme => {
    if (itme.id.toString() === key.toString()) {
      taskForm.value.taskType = itme.moduleName
      itme.section.forEach(itme => {
        const cases = {
          id: itme.id,
          name: itme.sectionName,
          url: itme.sectionRefer,
          createTime: itme.createTime,
          children: []
        }
        itme.children.forEach(itme => {
          const caseCs = {
            id: itme.id,
            name: itme.caseName,
            url: itme.caseUrl,
            createTime: itme.createTime
          }
          cases.children.push(caseCs)
        })
        caseTableDataList.value.push(cases)
      })
    }
  })
  if (caseTableDataList.value.length !== 0 && caseTableDataList.value[0].name === '三方测试') {
    tripartiteTask.value = true
  }
}
const stopClick = () => {
  if (userTaskFroms.length > 0) {
    disableds.value = true
  } else {
    disableds.value = false
  }
  if (caseTableDataList.value[0].name === '三方测试') {
    disableds.value = true
  }
}
const sreollber = ref(null)
// 通过子case查询module
const caseByModule = (is) => {
  const caset = caseTableDataList.value.filter(vars => vars.children.filter(ch => ch === is))
  for (var i = 0; i <= caset.length; i++) {
    for (var y = 0; y < caset[i].children.length; y++) {
      if (caset[i].children[y] === is) {
        return caset[i]
      }
    }
  }
}
// 全选模块
const tsModuleSeleceAll = () => {
  // 重置用户选择的测试任务
  userTaskFroms = []
  userTaskFroms = tablcref.value.getSelectionRows().filter(vars => vars.children)
  // 禁用测试类型选择
  stopClick()
}
// 当用户手动选择时
const tsModuleChange = (row) => {
  userTaskFroms = []
  // 获取用户选择的测试模块
  row.filter(vars => vars.children).forEach(itme => {
    const values = {
      id: itme.id,
      name: itme.name,
      url: itme.url,
      createTime: itme.createTime,
      children: []
    }
    userTaskFroms.push(values)
  })

  row.filter(vars => !vars.children).forEach(itme => {
    userTaskFroms.forEach(itme2 => {
      try {
        if (itme2.id === caseByModule(itme).id) {
          itme2.children.push(itme)
        }
      } catch (error) {
        tablcref.value.toggleRowSelection(itme, false)
        return ElMessage.error('不能选择多个模块')
      }
    })
  })
}
const tsDevicesData = ref([])
const tsTaskDevices = ref([])
let tsTaskDeviceList = []
const tsTaskDeviceClick = () => {
  tsTaskDeviceList = JSON.parse(JSON.stringify(tsTaskDevices.value))
}
// 显示抽屉方向
const direction = ref('rtl')
// 标题
// const dialogTile = ref('新增项目')
const tokens:any = jwtDecode(localStorage.getItem('Authorization'))
// 获取优先级List
const firstList = ref([])
const selectFirst = async () => {
  const dictType = '等级'
  await syDictApi.selectDictType(dictType).then(data => {
    firstList.value = data.data.data
  }).catch(err => {
    console.log(err)
  })
}
// ======================================项目模块
// 显示抽屉
const projectShow = ref(false)
// 输入框表单
const projectForm = ref({
  projectId: '',
  projectName: '',
  projectRefer: ''
})
watch(
  () => projectShow.value,
  async (newValue) => {
    if (newValue === false) {
      projectForm.value = {
        projectId: '',
        projectName: '',
        projectRefer: ''
      }
    }
  }
)
// 输入框表单
const taskForm = ref({
  id: null,
  taskProjectId: null,
  taskId: '',
  taskName: '',
  taskType: '',
  dictIdFirst: '',
  dictIdType: '等待'
})
// 保存项目
const savePrject = async () => {
  const userOrUserRoleFrom = {
    tsProject: {
      id: projectForm.value.projectId,
      projectName: projectForm.value.projectName,
      projectRefer: projectForm.value.projectRefer
    },
    tsUserProject: {
      id: '',
      userId: tokens.user.id,
      projectId: ''
    }
  }
  await tsCaseApi.tsProjectSaveOrUp(userOrUserRoleFrom).then(data => {
    if (data.data.code === 1014) {
      ElMessage.error(data.data.message)
    }
    window.location.reload()
  }).catch(err => {
    console.log(err)
  })
  projectShow.value = false
}
// 用来判断字符串是否为空
const exitsString = (vars: string) => {
  if (vars === '' || vars === ' ') { return false }
  return true
}
// 用来判断数组是否为空
const exitsArrar = (vars: any) => {
  if (vars.length === 0) { return false }
  return true
}
// 打开窗口
const prjectAdd = async (id?: string) => {
  projectShow.value = true
}
// ======================================任务模块
// 显示抽屉
const taskShow = ref(false)
// 关闭任务新增栏
watch(
  () => taskShow.value,
  async (newValue) => {
    if (newValue === false) {
      tsTaskDevices.value = []
      // 清空用户输入
      taskForm.value = {
        id: null,
        taskProjectId: null,
        taskId: '',
        taskName: '',
        taskType: '',
        dictIdFirst: '',
        dictIdType: '等待'
      }
      // 清空用户选择测试用例
      userTaskFroms = []
      disableds.value = false
      // 清空选择的机器
      tsTaskDeviceList = []
    } else {
      getDevice()
    }
  }
)
const taskProjectId = ref()
// 任务编辑
const taskEditor = (taskId) => {
  tsTaskApi.selectTaskByTaskId(taskId).then(data => {
    // 显示输入框
    taskShow.value = true
    // 填充任务信息
    taskForm.value = data.data.data.task
    taskProjectId.value = taskForm.value.taskProjectId
    // 填充三方数据
    if (data.data.data.task.tripartite !== '') {
      const tts = data.data.data.task.tripartite.split(',')
      ttFrom.value.head = tts[0]
      ttFrom.value.tail = tts[1]
    }
    // 添加选择设备
    const devices = data.data.data.task.devices.split(',')
    tsTaskDevices.value = devices
    tsTaskDeviceList = devices
    // 去掉设备选择时的框框
    setTimeout(function () {
      sreollber.value.childNodes[0].childNodes.forEach(data => {
        if (data.nodeName !== '#text') {
          data.children[0].style = 'display: none !important;'
        }
      })
    }, 100)
    // 添加用户已经选择的测试用例
    caseTypeClick()
    // 选中用户选择的case
    setTimeout(function () {
      // 创建字段组合变量Y
      let exX = ''
      caseTableData.forEach(itme => {
        if (itme.moduleName !== '三方') {
          itme.section.forEach(itme2 => {
            itme2.children.forEach(itme3 => {
              if (data.data.data.taskCase[0] !== undefined) {
                const taskCase = data.data.data.taskCase[0]
                const v = itme3.id + itme3.caseName + itme3.createTime
                const b = taskCase.caseId + taskCase.caseName + taskCase.createTime
                if (v === b) {
                  // 选择用户选择的测试模块
                  caseModuleActive.value = itme.id.toString()
                  moduleOpen(itme.id)
                }
              }
            })
          })
        } else {
          // 选中三方用例
          caseModuleActive.value = itme.id.toString()
          moduleOpen(itme.id)
          disableds.value = false
          tablcref.value.toggleRowSelection(caseTableDataList.value[0], true)
        }
      })
      const caset = caseTableDataList.value.filter(vars => vars.children)
      // 选中用户选择的变量
      data.data.data.taskCase.forEach(itme => {
        exX = itme.caseId + itme.caseName + itme.createTime
        // 创建字段组合变量Y
        let exY = ''
        for (var i = 0; i < caset.length; i++) {
          for (var y = 0; y < caset[i].children.length; y++) {
            const cas = caset[i].children[y]
            exY = cas.id + cas.name + cas.createTime
            if (exY === exX) {
              // 获取当前选择的module
              const thisModule = tablcref.value.getSelectionRows().filter(vars => vars.children)
              const a = thisModule.filter(vars => vars === caset[i])
              // 判断次用测试用例的module是否已选中，非选中则选中，选中即跳过
              if (a.length <= 0) {
                tablcref.value.toggleRowSelection(caset[i], true)
              }
            }
          }
        }
      })
      // 获取用户真实选择测试用例，取消全选时多余的选项
      const thisCase = tablcref.value.getSelectionRows().filter(vars => !vars.children)
      const userCase = data.data.data.taskCase
      thisCase.forEach(thCase => {
        const exUserCase = userCase.filter(i => i.caseId === thCase.id)
        if (exUserCase.length <= 0) { tablcref.value.toggleRowSelection(thCase, false) }
      })
      // 如果有数据则禁用测试类型选择
      tsModuleChange(tablcref.value.getSelectionRows())
      stopClick()
    }, 300)
    loading.value = false
    // console.log(data)
  }).catch(err => {
    console.log(err)
  })
}
// 保存任务
const saveTask = async () => {
  if (!exitsString(taskForm.value.taskName)) { return ElMessage.error('任务名称不能为空') }
  if (!exitsString(taskForm.value.dictIdFirst)) { return ElMessage.error('优先级不能为空') }
  if (!exitsArrar(userTaskFroms)) { return ElMessage.error('测试类型与任务或测试用例不能为空') }
  if (userTaskFroms.length >= 1 && userTaskFroms[0].name === '三方测试') {
    if (!exitsString(ttFrom.value.head)) { return ElMessage.error('开始数值不能为空！') }
    if (!exitsString(ttFrom.value.tail)) { return ElMessage.error('结束数值不能为空！') }
    if (parseInt(ttFrom.value.head) >= parseInt(ttFrom.value.tail)) { return ElMessage.error('结束编号不能大于开始编号') }
    const ttTaskFrom = {
      tsTask: {
        id: taskForm.value.id,
        taskName: taskForm.value.taskName,
        taskType: taskForm.value.taskType,
        estimateTime: '1',
        realityTime: '1',
        devices: tsTaskDeviceList.join(','),
        dictIdFirst: taskForm.value.dictIdFirst,
        dictIdType: taskForm.value.dictIdType,
        tripartite: ttFrom.value.head + ',' + ttFrom.value.tail
      },
      tsProjectTask: {
        id: taskProjectId.value,
        projectId: window.localStorage.getItem('Prs'),
        taskId: null
      }
    }
    tsTaskApi.taskSaveOrUp(ttTaskFrom).then(data => {
      if (data.data.success) {
        ElMessage.success('新增任务成功')
        window.location.reload()
        taskShow.value = false
      }
    }).catch(err => {
      console.log(err)
      taskShow.value = false
    })
  } else {
    if (!exitsArrar(tsTaskDeviceList)) { return ElMessage.error('测试机器不能为空') }
    const uptFrom = {
      tsProjectTask: {
        id: taskProjectId.value,
        projectId: window.localStorage.getItem('Prs'),
        taskId: null
      },
      tsTaskDetails: {
        taskId: null,
        casId: null
      },
      tsTask: {
        id: taskForm.value.id,
        taskName: taskForm.value.taskName,
        taskType: taskForm.value.taskType,
        estimateTime: '1',
        realityTime: '1',
        devices: tsTaskDeviceList.join(','),
        dictIdFirst: taskForm.value.dictIdFirst,
        dictIdType: taskForm.value.dictIdType
      },
      caseIds: []
    }
    userTaskFroms.forEach(itme => {
      itme.children.forEach(itme2 => {
        uptFrom.caseIds.push(itme2.id)
      })
    })
    tsTaskApi.taskSaveOrUpTaskCase(uptFrom).then(async data => {
      if (data.data.code === 1002) {
        ElMessage.success('新增任务成功')
        window.location.reload()
        taskShow.value = false
      }
    }).catch(err => {
      console.log(err)
      taskShow.value = false
    })
  }
}
// 获取adb设备
const getDeviceDebugce = async () => {
  await axios.post('http://192.168.3.85:5000/adbTool/adbDevices', {
    ip: tokens.user.ipv
  }).then(data => {
    tsDevicesData.value = data.data.data
    // 去掉设备选择时的框框
    setTimeout(function () {
      sreollber.value.childNodes[0].childNodes.forEach(data => {
        if (data.nodeName !== '#text') {
          data.children[0].style = 'display: none !important;'
        }
      })
    }, 100)
  }).catch(err => {
    ElMessage.error('设备获取失败：' + err)
  })
}
const getDevice = useDebounce(getDeviceDebugce, 1000)
// 打开窗口
const taskAdd = async (id?: string) => {
  taskShow.value = true
  caseTypeClick()
  // 去掉设备选择时的框框
  setTimeout(function () {
    sreollber.value.childNodes[0].childNodes.forEach(data => {
      if (data.nodeName !== '#text') {
        data.children[0].style = 'display: none !important;'
      }
    })
  }, 100)
}
// onMounted(() => {
//   console.log(divs)
// })
defineExpose({
  prjectAdd,
  taskAdd,
  taskEditor
})
</script>
<style>
.el-checkbox__inner{
  width: 25px;
  height: 23px;
}
.el-checkbox__input.is-checked .el-checkbox__inner::after{
  margin-left: 5px;
  margin-top: 4px;
}
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}
.caseModuleName{
  font-size: 15px;
  width: 147px;
  height: 42px;
  background: #56acff;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  border-radius: 0px 0px 10px 10px;
}
</style>
