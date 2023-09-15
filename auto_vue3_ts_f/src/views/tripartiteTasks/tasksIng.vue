<template>
  <div class="optionList">
    <div style="height: 650px;margin-top: 20px;">
      <el-table
        :data="tableData"
        style="width: 1200px;height: 650px;"
        :row-class-name="tableRowClassName"
      >
          <el-table-column type="expand">
          <template #default="props">
            <div m="4">
              <p m="t-0 b-2">任务名称(备注): {{ props.row.taskNotes }}</p>
              <p m="t-0 b-2">下发任务用户: {{ props.row.createBy }}</p>
              <p m="t-0 b-2">任务状态: {{ props.row.taskState }}</p>
              <p m="t-0 b-2">测试设备: {{ props.row.deviceList }}</p>
              <p m="t-0 b-2">固件版本: {{ props.row.gjVersion }}</p>
              <p m="t-0 b-2">测试用例: {{ props.row.taskContent }}</p>
              <p m="t-0 b-2">应用列表: {{ props.row.taskTop }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测试应用数量：{{ props.row.taskTop.split(',').length }}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="taskId" label="任务ID" align="center"  />
        <el-table-column prop="taskTop" label="测试应用" width="180" align="center"  />
        <el-table-column prop="taskContent" label="测试内容" width="180" align="center"  />
        <el-table-column prop="deviceList" label="测试设备" width="180" align="center"  />
        <el-table-column prop="taskNotes" label="测试备注" align="center"  />
        <el-table-column
          prop="createTime"
          label="下发时间"
          sortable
          width="180"
          column-key="date"
          align="center"
        />
        <el-table-column
          prop="createBy"
          label="下发用户"
          width="180"
          column-key="date"
          align="center"
        />
        <el-table-column
            prop="taskState"
            label="任务状态"
            width="100"
            :filters="[
              { text: '测试失败', value: '测试失败' },
              { text: '正在测试', value: '正在测试' },
              { text: '新建完成', value: '新建完成' },
              { text: '测试完成', value: '测试完成' }
            ]"
            :filter-method="filterTag"
            filter-placement="bottom-end"
            align="center"
          >
          </el-table-column>
          <!-- <el-table-column label="服务器排名" width="120" align="center">
            <template #default="scope">
                <span>{{ scope.row.tasksId }}</span>
              </template>
          </el-table-column> -->
          <el-table-column fixed="right" label="操作" align="center">
          <template #default="scope">
            <el-button style="color: #ff4400;" link type="primary" size="small" @click="removeTaskByIds(scope.row.taskId,scope.row.taskState)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { userApi } from '@/api/systemApi/sysUser-api'
import { ElMessage } from 'element-plus'

interface User {
  taskId: string
  testTop: string
  testContent: string
  testDevices: string
  taskState: string
}
// 定义接收用户ID集合（用于删除数据）
const filterTag = (value: string, row: User) => {
  return row.taskState === value
}

const tableRowClassName = ({
  row
}: {
  row: User
  rowIndex: number
}) => {
  if (row.taskState === '测试成功') {
    return 'success-row'
  } else if (row.taskState === '正在测试') {
    return 'warning-row'
  } else if (row.taskState === '测试失败') {
    return 'danger-row'
  }
  return ''
}
const tableData = ref([{}])
// 定义接收用户ID集合（用于删除数据）
let taskIds: string[] = []
const getTaskList = async () => {
  const tasks = await userApi.selectTask()
  if (tasks.data.code === 1000) {
    tableData.value = tasks.data.data
  }
}
/**
 * 组件加载完成时执行的钩子函数
 */
const removeTaskByIds = async (taskId: string | null, taskState: string | null) => {
  if (taskState === '正在测试') {
    ElMessage({
      message: taskState + '不可删除',
      type: 'error'
    })
    return
  }
  taskIds = [taskId]
  // 请求后端API删除数据
  const removeRes = await userApi.removeTaskIds({ taskIds: taskIds.join(',') })
  // 判断是否删除成功
  if (removeRes.data.success) {
    // 清空删除集合
    taskIds = []
    // 重新加载数据
    getTaskList()
    ElMessage({
      message: '删除成功',
      type: 'success'
    })
  } else {
    // 提示删除失败
    ElMessage({
      message: '删除失败',
      type: 'error'
    })
  }
}
getTaskList()
// onMounted(() => {
//   getTaskList()
// const getCaseModuleClassifyIdRes = userApi.getTaskIdUpdata('15', '测试失败')
// })
</script>

<style lang="scss">
.el-table .danger-row{
  background-color: #ffeaea;
}
.optionDiv{
  width: 200px;
  height: 250px;
  background: white;
  border-radius: 10px;
  box-shadow: 1px 1px 10px -2px #bfbfbf;
  margin: 20px;
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
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
.el-scrollbar{
  display: flex;
  justify-content: center;
}
</style>
