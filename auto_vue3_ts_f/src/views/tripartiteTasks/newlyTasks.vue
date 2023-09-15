<template>
  <div class="newlyTasksClass">
    <div class="newlyTasks1">
      <div class="ks1Form">
        <el-form :inline="true" style="width: 100%;">
          <div class="mt-4" style="display: flex; justify-content: space-between;">
            <el-form-item label="SN" style="display: flex; justify-content: center; align-items: center;">
              <el-input
                v-model="deviceFrom.deviceName"
                placeholder="SN"
                class="input-with-select"
                size="large"
                clearable
              >
              </el-input>
            </el-form-item>
            <el-form-item label="机型" style="display: flex; justify-content: center; align-items: center;">
              <el-select v-model="deviceFrom.deviceType" placeholder="机型" class="input-with-select" style="width: 80px;">
                <el-option label="" value="" />
                <el-option label="XPV11" value="XPV11" />
                <el-option label="XP32A" value="XP32A" />
                <el-option label="XTW31" value="XTW31" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" style="display: flex; justify-content: center; align-items: center;">
              <el-select v-model="deviceFrom.deviceState" placeholder="状态" class="input-with-select" style="width: 120px;">
                <el-option label="" value="" />
                <el-option label="在线-运行" value="在线-运行" />
                <el-option label="在线-空闲" value="在线-空闲" />
              </el-select>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <el-switch
        v-model="ifUser"
        class="mb-2"
        active-text="显示我的设备"
        inactive-text="显示全员设备"
        @change="inactiveClick()"
      />
      <div v-loading="loading">
        <el-table
        :data="deviceData"
        border
        style="width: 500px"
        height="512px"
        @select="selectUserRow"
        @select-all="selectAll"
        >
          <el-table-column fixed="left" type="selection" width="50" align="center" :selectable="checkSelectable">
          </el-table-column>
          <el-table-column prop="deviceId" label="ID" align="center" style="color: aqua;" />
          <el-table-column prop="deviceType" label="机型" align="center" />
          <el-table-column prop="deviceName" label="device" align="center" width="140" />
          <el-table-column prop="deviceNotes" label="备注" align="center" />
          <el-table-column prop="createBy" label="创建人" align="center" width="120"/>
          <el-table-column prop="deviceState" label="状态" align="center" width="120">
            <template v-slot="scope">
              <span :style="scope.row.deviceState === '在线空闲' ? {color:'#3ced6a'}:{color:'#ff0000'}">{{scope.row.deviceState}}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="toUserEdit(scope.row.deviceId)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="display: flex;margin-top: 10px;justify-content: space-between;width: 85%;">
        <el-button type="primary" @click="refresh()" style="width: 50px;height: 25px;margin-top: 5px;">刷新</el-button>
        <el-button type="primary" @click="deviceAdd()" style="width: 50px;height: 25px;margin-top: 5px;">添加</el-button>
        <el-pagination
          :current-page="queryParam.currentPage"
          :page-size="queryParam.pageSize"
          :page-sizes="[8, 50, 100, 500]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          small
        />
      </div>
    </div>
    <div class="newlyTasks2">
      <div class="tasks2-input">
        <div class="optionList">
          <el-form-item label="测试应用" style="display: flex; justify-content: center; align-items: center;">
            <el-select v-model="testApp" placeholder="应用选择" multiple collapse-tags clearable class="input-with-select">
              <el-option v-for="(item) in selctOptinList" :key="item.label" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="测试内容" style="display: flex; justify-content: center; align-items: center;">
            <el-select v-model="testAppType" placeholder="测试内容" class="input-with-select">
              <el-option label="下载安装" value="下载安装" />
              <el-option label="下载安装+基本功能测试" value="下载安装+基本功能测试" />
            </el-select>
          </el-form-item>
          <el-form-item label="测试备注" style="display: flex; justify-content: center; align-items: center;">
            <el-input
              maxlength="11"
              v-model="testNameAndrem"
              placeholder="测试项目名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
        </div>
      </div>
      <el-dialog
        v-model="dialogtf"
        title="添加设备"
        width="530px"
        align-center
      >
      <el-form :model="deviceFromRef" label-width="120px">
        <el-form-item label="device">
          <el-input placeholder="Device" style="width: 300px" v-model="deviceFromRef.deviceName" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input placeholder="备注" style="margin-top: 10px;width: 300px;" v-model="deviceFromRef.deviceNotes" />
        </el-form-item>
        <el-form-item label="机型">
          <el-select v-model="deviceFromRef.deviceType" placeholder="机型" style="width: 100px;margin-top: 10px;">
          <el-option label="V1" value="V1" />
          <el-option label="W1" value="W1" />
          <el-option label="W2" value="W2" />
          <el-option label="W3" value="W3" />
        </el-select>
        </el-form-item>
      </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogtf = false">取消</el-button>
            <el-button type="primary" @click="yesAdd()">添加</el-button>
          </span>
        </template>
      </el-dialog>
      <div style="height: auto;width: 97%;">
        <div class="tasks2-button">
          <div class="okTxt">
            <p>测试应用:&nbsp;&nbsp;&nbsp;<span>{{ testApp }}</span></p>
            <p>测试内容:&nbsp;&nbsp;&nbsp;<span>{{ testAppType }}</span></p>
            <p>测试备注:&nbsp;&nbsp;&nbsp;<span>{{ testNameAndrem }}</span></p>
          </div>
          <div class="okTxt" style="margin-top: 3px;">
            <p>提测用户:&nbsp;&nbsp;&nbsp;<span>{{ adminName }}</span></p>
            <p>用户已提测数量:&nbsp;&nbsp;&nbsp;<span>11</span></p>
          </div>
        </div>
        <el-button type="primary" @click="snAdd(null)" style="width: 100%;height: 40px;margin-top: 30px;">提交下发</el-button>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { ref, onMounted } from 'vue'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
import jwtDecode from 'jwt-decode'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import { ElMessage, ElNotification } from 'element-plus'
import { userApi } from '@/api/systemApi/sysUser-api'

const checkSelectable = (row) => {
  return row.deviceState !== '测试中'
}
const ifUser = ref(true)
const deviceIdRef = ref('')
const deviceFromRef = ref({
  deviceName: '',
  deviceType: '',
  deviceNotes: ''
})
const deviceFrom = ref({
  deviceName: '',
  deviceType: '',
  deviceState: ''
})
const testApp = ref([])
const testAppType = ref('下载安装')
const testNameAndrem = ref('测试')
const deviceType = ref('')
const deviceName = ref('')
const deviceNotes = ref('')
const dialogtf = ref(false)
const userac = ref('')

// 定义分页查询总条数变量
const total = ref(0)
const queryParam = {
  currentPage: 1,
  pageSize: 8,
  deviceName: '',
  deviceType: '',
  userAccount: ''
}
const inactiveClick = () => {
  loadingData()
}
// 定义加载中进度条变量
const loading = ref(true)
interface User {
  deviceName: string
}
const selctOptinList = [
  { label: 'TOP01-100', value: 'TOP01-100' },
  { label: 'TOP100-200', value: 'TOP100-200' },
  { label: 'TOP200-300', value: 'TOP200-300' },
  { label: 'TOP300-400', value: 'TOP300-400' },
  { label: 'TOP400-500', value: 'TOP400-500' },
  { label: 'TOP500-600', value: 'TOP500-600' },
  { label: 'TOP600-700', value: 'TOP600-700' }
]
const deviceAdd = () => {
  dialogtf.value = true
  deviceFromRef.value = {
    deviceName: '',
    deviceType: '',
    deviceNotes: ''
  }
  deviceIdRef.value = ''
}
const yesAdd = async () => {
  dialogtf.value = false
  let deviceFrom = {}
  const userAccount:any = jwtDecode(localStorage.getItem('Authorization'))
  if (deviceIdRef.value !== null) {
    deviceFrom = {
      deviceId: deviceIdRef.value,
      deviceName: deviceFromRef.value.deviceName,
      deviceType: deviceFromRef.value.deviceType,
      deviceState: '在线空闲',
      deviceNotes: deviceFromRef.value.deviceNotes,
      userAccount: userAccount.systemUser.userAccount
    }
  } else {
    deviceFrom = {
      deviceName: deviceFromRef.value.deviceName,
      deviceType: deviceFromRef.value.deviceType,
      deviceState: '在线空闲',
      deviceNotes: deviceFromRef.value.deviceNotes,
      userAccount: userAccount.systemUser.userAccount
    }
  }
  const sOrU = await userApi.saveOrUpdateDevice(deviceFrom)
  if (sOrU.data.code === 1004) {
    ElNotification({
      title: 'Success',
      message: '刷新设备成功',
      type: 'success'
    })
    deviceType.value = ''
    deviceName.value = ''
    deviceNotes.value = ''
    loadingData()
  } else if (sOrU.data.code === 1002) {
    ElNotification({
      title: 'Success',
      message: '添加设备成功' + '\ndevices:' + sOrU.data.data.deviceName + '\n机型:' + sOrU.data.data.deviceType,
      type: 'success'
    })
    deviceType.value = ''
    deviceName.value = ''
    deviceNotes.value = ''
    loadingData()
  } else {
    ElNotification({
      title: 'Error',
      message: '添加设备失败，您想要添加的设备存在列表中！',
      type: 'error'
    })
    deviceType.value = ''
    deviceName.value = ''
    deviceNotes.value = ''
    loadingData()
  }
}

// 定义接收Table表格数据
const deviceData = ref([])
const handleSizeChange = (pageSize: number) => {
  queryParam.pageSize = pageSize
  loadingData()
}

const handleCurrentChange = (currentPage: number) => {
  queryParam.currentPage = currentPage
  loadingData()
}
// 定义接收用户ID集合（用于删除数据）
let deviceNames: string[] = []
const selectUserRow = (selection: User[]) => {
  deviceNames = []
  selection.forEach(userRow => {
    deviceNames.push(userRow.deviceName)
  })
}
// 全选触发
const selectAll = (val) => {
  if (val.length <= 0) {
    deviceNames = []
  }
  val.forEach(userRow => {
    deviceNames.push(userRow.deviceName)
  })
}
// 刷新设备
const refresh = () => {
  loadingData()
  loading.value = true
}
const loadingData = async () => {
  loading.value = true
  const deviceFromList = ref({
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    deviceName: deviceFrom.value.deviceName,
    deviceType: deviceFrom.value.deviceType,
    userAccount: ''
  })
  if (ifUser.value) {
    deviceFromList.value = {
      currentPage: queryParam.currentPage,
      pageSize: queryParam.pageSize,
      deviceName: deviceFrom.value.deviceName,
      deviceType: deviceFrom.value.deviceType,
      userAccount: userac.value
    }
  }
  const pageDevices = await userApi.pageDevice(deviceFromList.value)
  if (pageDevices.data.code !== 1000) {
    ElNotification({
      title: 'Error',
      message: '获取设备失败，请刷新重试！',
      type: 'error'
    })
    loading.value = false
    return
  }
  deviceData.value = pageDevices.data.data.records
  total.value = pageDevices.data.data.total
  loading.value = false
}
// 任务新增
const snAdd = async (userId: string | null) => {
  // 判断是否为单条数据
  if (userId) {
    deviceNames = [userId]
  } else {
    if (deviceNames.length <= 0) {
      ElMessage({
        message: '请选择测试设备.',
        type: 'error'
      })
      return
    }
    if (testApp.value.length <= 0) {
      ElMessage({
        message: '请选择测试应用范围.',
        type: 'error'
      })
      return
    }
  }
  // 输入框表单
  const taskForm = ref({
    taskTop: '',
    taskContent: '',
    taskState: '',
    deviceList: '',
    taskNotes: '',
    userAccount: ''
  })
  taskForm.value.taskTop = testApp.value.join(',')
  taskForm.value.taskContent = testAppType.value
  taskForm.value.taskNotes = testNameAndrem.value
  taskForm.value.deviceList = deviceNames.join(',')
  taskForm.value.taskState = '新建完成'
  taskForm.value.userAccount = userac.value
  console.log(userac.value)
  const taskAdd = await userApi.save(taskForm.value)
  if (taskAdd.data.code === 1002) {
    ElMessage({
      message: '添加任务成功，可在任务列表查看任务排序.',
      type: 'success'
    })
  } else {
    ElMessage({
      message: '任务添加失败.',
      type: 'error'
    })
  }
}
const toUserEdit = async (deviceId: string) => {
  if (!ifUser.value) {
    ElNotification({
      title: 'Error',
      message: '次列表无法修改，请切换至个人设备列表修改',
      type: 'error'
    })
    return
  }
  deviceIdRef.value = deviceId
  const getDeviceId = await userApi.getDeviceId(deviceId)
  if (getDeviceId.data.code === 1000) {
    deviceFromRef.value = getDeviceId.data.data
    dialogtf.value = true
  }
}
const getTaskListByDeviceName = async () => {
  const tasks = await userApi.selectTask()
  if (tasks.data.code === 1000) {
    tasks.data.data.forEach(value => {
      console.log(value.deviceList)
    })
  }
}
const adminName = ref('admin')
onMounted(() => {
  getTaskListByDeviceName()
  if (localStorage.getItem('Authorization') !== null) {
    const tokens:any = jwtDecode(localStorage.getItem('Authorization'))
    adminName.value = tokens.systemUser.userName
    userac.value = tokens.systemUser.userAccount
  }
  loadingData()
})
</script>

<style lang="scss">
.newlyTasksClass{
  display: flex;
  justify-content: space-between;
  width: 100%;
  flex-direction: row;
  height: auto;
}
// 左
.newlyTasks1{
  display: flex;
  align-items: center;
  width: 55%;
  flex-direction: column;
  height: auto;
}
.ks1Form{
  height: 60px;
  width: 80%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.ks1Form el-form el-form--default el-form--label-right el-form--inline{
  width: 100%;
}
// 右
.newlyTasks2{
  width: 45%;
  height: auto;
}
.tasks2-input{
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.tasks2-button{
  background: white;
  box-shadow: 0px 3px 18px 1px #d2d2d2;
  border-radius: 10px 10px 10px 10px;
  height: auto;
  display: flex;
  justify-content: space-evenly;
  flex-direction: column;
  padding: 10px;
}
.el-form--inline .el-form-item{
  margin: 0;
}
.input-with-select{
  width: 180px;
}
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
.el-input__wrapper {
  width: 300px;
}
.el-input__wrapper{
  height: 35px;
  width: 200px;
}
.okTxt{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.okTxt p{
  margin-left: 15px;
  // width: 33%;
  // white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.optionList{
  height: 25%;
  display: flex;
  justify-content: inherit;
  flex-direction: row;
  flex-wrap: wrap;
}
.okTxt span{
  color: #bbbbbb;
}
.el-select .el-select__tags>span{
  display: flex;
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
// .el-checkbox__inner{
//   width: 28px;
//   height: 28px;
// }
// .el-table__body-wrapper .el-table-column--selection>.cell, .el-table__header-wrapper .el-table-column--selection>.cell{
//   height: 28px;
// }
</style>
