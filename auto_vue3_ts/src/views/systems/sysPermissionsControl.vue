<template>
  <el-card class="sysPermissionsCases" shadow="never">
    <template #header>
      <el-form :inline="true" :model="queryParam">
        <div class="mt-4" style="width: 700px;">
          <el-form-item label="根据权限code查询:">
            <el-input
              v-model="permissionsName"
              placeholder="权限code"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="mt-4" style="width: 700px;">
          <el-form-item label="根据权限名称查询:">
            <el-input
              v-model="permissionsCode"
              placeholder="权限名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
        </div>
        <el-form-item style="display: block;">
        <el-button @click="loadPermissionsData">查询</el-button>
        <el-button type="primary" @click="toPermissionsAdd">新增</el-button>
        <el-button type="success">占位按钮1</el-button>
        <el-button type="warning">占位按钮2</el-button>
        <el-button type="danger" @click="removePermissionsByIds(null)">批量删除</el-button>
        </el-form-item>
      </el-form>
      <p style="font-size: 12px;color: #7e7e7e;">· code是唯一标识不能存在相同元素</p>
    </template>
    <div v-loading="loading">
      <el-table :data="permissionsData" border style="width: 100%" height="510px" @select="selectPermissionsRow">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="permissionsId" label="权限编号" align="center" />
        <el-table-column prop="permissionsHigher" label="父ID" align="center" />
        <el-table-column prop="permissionsName" label="权限名称" align="center" />
        <el-table-column prop="permissionsPath" label="权限Path" align="center" />
        <el-table-column prop="permissionsComponent" label="文件路径" align="center" />
        <el-table-column prop="permissionsIcon" label="权限Icon" align="center" />
        <el-table-column prop="permissionsIsShow" label="是否显示" align="center" />
        <el-table-column prop="permissionsHidden" label="需要隐藏" align="center" />
        <el-table-column prop="createTime" label="创建时间" align="center" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column prop="deleteFlag" width="100" label="删除状态" align="center" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="toPermissionsEdit(scope.row.permissionsId)">编辑</el-button>
            <el-button style="color: #ff4400;" link type="primary" size="small" @click="removePermissionsByIds(scope.row.permissionsId)">删除</el-button>
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
    <permissions-edit-view ref="permissionsEditRef" @reload="queryPermissionsData"></permissions-edit-view>
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, ElMessageBox } from 'element-plus'
// 引入请求后端封装的API
import { permissionsApi, QueryParam } from '@/api/systemApi/sysPermissions-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import permissionsEditView from './sysPermissionsControlAddView.vue'

const permissionsName = ref()
const permissionsCode = ref()
// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(true)
// 默认加载没有出错
const error = ref(false)
// 新增/编辑组件ref
const permissionsEditRef = ref()
interface Permissions {
  permissionsId: string
}
// 定义分页查询请求参数
const queryParam = reactive<QueryParam>({
  // 当前页
  currentPage: 1,
  // 一页显示条数
  pageSize: 8,
  permissionsCode: '',
  // 权限名称
  permissionsName: ''
})
// 定义接收Table表格数据
const permissionsData = ref([])
// 定义接收用户ID集合（用于删除数据）
let permissionsIds: string[] = []
/**
 * 点击查询按钮或重新加载数据方法
 */
const queryPermissionsData = () => {
  // 令分页查询参数的当前页初始化为1
  queryParam.currentPage = 1
  // 令分页查询参数的一页显示条数初始化为1
  queryParam.pageSize = 8
  // 执行查询方法
  loadPermissionsData()
}
/**
 * 点击选择框触发事件
 * @param selection 选择的集合
 */
const selectPermissionsRow = (selection: Permissions[]) => {
  // 初始化需要上传的用户Id集合
  permissionsIds = []
  // 遍历选择的集合
  selection.forEach(permissionsRow => {
    // 把获取到的主键ID设置进permissionsIds集合里面
    permissionsIds.push(permissionsRow.permissionsId)
  })
}
/**
 * 根据主键ID删除用户信息
 * @param permissionsId 用户ID（点击列表删除按钮必须传）
 */
const removePermissionsByIds = (permissionsId: string | null) => {
  // 提示用户是否删除
  ElMessageBox.confirm('是否删除点击或选择的用户信息数据?', '提示消息', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  }).then(async () => {
    console.log(permissionsId)
    // 判断是否为单条数据
    if (permissionsId) {
      // 如果为单条数据直接装载进去
      permissionsIds = [permissionsId]
    } else {
      if (permissionsIds.length === 0) {
        // 提示请选择需要删除的数据
        ElMessage({
          type: 'warning',
          message: '请选择需要删除的数据'
        })
        return
      }
    }
    // 请求后端API删除数据
    console.log(permissionsIds)
    const removeRes = await permissionsApi.removePermissionsIds({ permissionsIds: permissionsIds.join(',') })
    // 判断是否删除成功
    if (removeRes.data.success) {
      // 清空删除集合
      permissionsIds = []
      // 重新加载数据
      queryPermissionsData()
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
const loadPermissionsData = async () => {
  // 调用请求后端API
  const pagePermissionsList = {
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    permissionsName: permissionsName.value,
    permissionsCode: permissionsCode.value
  }
  const TestUseCasesRes = await permissionsApi.pagePermissions(pagePermissionsList)
  // 把接收到的数据赋值给data变量
  const data = TestUseCasesRes.data.data
  // 判断data和data.records是否有值
  if (data && data.records) {
    // 把查询出来的数据赋值给TestUseCasesData变量
    permissionsData.value = data.records
    // permissionsData.value.forEach(function (value, key) {
    //   if (value.permissionsType) {
    //     permissionsData.value[key].permissionsType = 1
    //   } else {
    //     permissionsData.value[key].permissionsType = 0
    //   }
    // })
    // 把查询出来的总条数赋值给total变量
    total.value = Number(data.total)
    loading.value = false
  } else {
    error.value = true
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
  loadPermissionsData()
}
/**
 * 当前页改变事件
 * @param currentPage 新的当前页
 */
const handleCurrentChange = (currentPage: number) => {
  // 把新的当前页（currentPage）赋值给分页查询请求参数的当前页（queryParam.currentPage）
  queryParam.currentPage = currentPage
  // 重新执行查询方法
  loadPermissionsData()
}
/**
 * 打开新增页面方法
 */
const toPermissionsAdd = () => {
  // 通过testUseCasesEditRef执行显示新增页面方法
  permissionsEditRef.value.openDialog()
}
/**
 * 打开编辑页面方法
 * @param permissionsId
 */
const toPermissionsEdit = (permissionsId: string) => {
  // 通过TestUseCasesEditRef执行显示修改页面及绑定数据方法
  permissionsEditRef.value.openDialog(permissionsId)
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadPermissionsData()
})
</script>

<style lang="scss" scoped>
.sysPermissionsCases {
  .el-pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

<style lang="scss">
.sysPermissionsCases {
  .el-card__header {
    padding-bottom: 0 !important;
  }
}
.sysPermissionsCases-import-excel {
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
