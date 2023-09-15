<template>
  <el-card class="sysRoleCases" shadow="never">
    <template #header>
      <el-form :inline="true" :model="queryParam">
        <div class="mt-4" style="width: 700px;">
          <el-form-item label="根据身份名称查询:">
            <el-input
              v-model="queryParam.roleName"
              placeholder="角色名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
          </div>
          <el-form-item style="display: block;">
          <el-button @click="loadRoleData">查询</el-button>
          <el-button type="primary" @click="toRoleAdd">新增</el-button>
          <el-button type="danger" @click="removeRoleByIds(null)">批量删除</el-button>
          </el-form-item>
      </el-form>
      </template>
    <div v-loading="loading">
      <el-table :data="roleData" border style="width: 100%" @select="selectRoleRow">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="roleId" width="240" label="角色编号" align="center" />
        <el-table-column prop="roleCode" label="角色Code" align="center" width="150" />
        <el-table-column prop="roleName" label="角色名称" align="center" width="150" />
        <el-table-column prop="createTime" label="创建时间" align="center" width="240" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column prop="deleteFlag" width="100" label="删除状态" align="center" />
        <el-table-column label="操作" width="150" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="toRoleEdit(scope.row.roleId)">编辑</el-button>
            <el-button link type="primary" size="small" @click="removeRoleByIds(scope.row.roleId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="queryParam.currentPage"
        :page-size="queryParam.pageSize"
        :page-sizes="[8, 50, 100, 500]"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <role-edit-view ref="roleEditRef" @reload="queryRoleData"></role-edit-view>
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, ElMessageBox } from 'element-plus'
// 引入请求后端封装的API
import { roleApi, QueryParam } from '@/api/systemApi/sysRole-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import roleEditView from './sysRoleControlAddView.vue'

// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(false)
// 新增/编辑组件ref
const roleEditRef = ref()
// console.log(testUseCasesEditRef.value.openDialog)
interface Role {
  roleId: string
}
// 定义分页查询请求参数
const queryParam = reactive<QueryParam>({
  // 当前页
  currentPage: 1,
  // 一页显示条数
  pageSize: 8,
  roleName: ''
})
// 定义接收Table表格数据
const roleData = ref([])
// 定义接收角色ID集合（用于删除数据）
let roleIds: string[] = []
/**
 * 点击查询按钮或重新加载数据方法
 */
const queryRoleData = () => {
  // 令分页查询参数的当前页初始化为1
  queryParam.currentPage = 1
  // 令分页查询参数的一页显示条数初始化为1
  queryParam.pageSize = 8
  // 执行查询方法
  loadRoleData()
}
/**
 * 点击选择框触发事件
 * @param selection 选择的集合
 */
const selectRoleRow = (selection: Role[]) => {
  // 初始化需要上传的角色Id集合
  roleIds = []
  // 遍历选择的集合
  selection.forEach(roleRow => {
    // 把获取到的主键ID设置进roleIds集合里面
    roleIds.push(roleRow.roleId)
  })
}
/**
 * 根据主键ID删除角色信息
 * @param roleId 角色ID（点击列表删除按钮必须传）
 */
const removeRoleByIds = (roleId: string | null) => {
  // 提示用户是否删除
  ElMessageBox.confirm('是否删除点击或选择的角色信息数据?', '提示消息', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  }).then(async () => {
    console.log(roleId)
    // 判断是否为单条数据
    if (roleId) {
      // 如果为单条数据直接装载进去
      roleIds = [roleId]
    } else {
      if (roleIds.length === 0) {
        // 提示请选择需要删除的数据
        ElMessage({
          type: 'warning',
          message: '请选择需要删除的数据'
        })
        return
      }
    }
    // 请求后端API删除数据
    console.log(roleIds)
    const removeRes = await roleApi.removeRoleIds({ roleIds: roleIds.join(',') })
    // 判断是否删除成功
    if (removeRes.data.success) {
      // 清空删除集合
      roleIds = []
      // 重新加载数据
      queryRoleData()
      // 提示删除成功
      await ElMessageBox.alert(removeRes.data.message, '提示信息', {
        type: 'success',
        confirmButtonText: '确定'
      })
    } else {
      // 提示删除失败
      await ElMessageBox.alert(removeRes.data.message, '提示信息', {
        type: 'error',
        confirmButtonText: '确定'
      })
    }
  })
}
/**
 * 分页查询学校信息数据
 */
const loadRoleData = async () => {
  // 调用请求后端API
  const pageRoleList = {
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    roleName: queryParam.roleName
  }
  const TestUseCasesRes = await roleApi.pageRole(pageRoleList)
  // 把接收到的数据赋值给data变量
  const data = TestUseCasesRes.data.data
  // 判断data和data.records是否有值
  if (data && data.records) {
    // 把查询出来的数据赋值给TestUseCasesData变量
    roleData.value = data.records
    // 把查询出来的总条数赋值给total变量
    total.value = Number(data.total)
  }
}
/**
 * 一页显示条数改变事件
 * @param pageSize 新的一页显示条数
 */
const handleSizeChange = (pageSize: number) => {
  // 把新的一页显示条数（val）赋值给分页查询请求参数的一页显示条数（queryParam.pageSize）
  queryParam.pageSize = pageSize
  // 重新执行查询方法
  loadRoleData()
}
/**
 * 当前页改变事件
 * @param currentPage 新的当前页
 */
const handleCurrentChange = (currentPage: number) => {
  // 把新的当前页（currentPage）赋值给分页查询请求参数的当前页（queryParam.currentPage）
  queryParam.currentPage = currentPage
  // 重新执行查询方法
  loadRoleData()
}
/**
 * 打开新增页面方法
 */
const toRoleAdd = () => {
  // 通过testUseCasesEditRef执行显示新增页面方法
  console.log(roleEditRef.value.openDialog())
  roleEditRef.value.openDialog()
}
/**
 * 打开编辑页面方法
 * @param roleId
 */
const toRoleEdit = (roleId: string) => {
  // 通过TestUseCasesEditRef执行显示修改页面及绑定数据方法
  roleEditRef.value.openDialog(roleId)
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadRoleData()
})
</script>

<style lang="scss" scoped>
.sysRoleCases {
  .el-pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

<style lang="scss">
.sysRoleCases {
  .el-card__header {
    padding-bottom: 0 !important;
  }
}
.sysRoleCases-import-excel {
  .el-dialog__body {
    border-top: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
.el-input__wrapper {
  width: 400px;
}
</style>
