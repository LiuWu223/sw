<template>
  <el-card class="sysUserRoleCases" shadow="never">
    <template #header>
      <el-form :inline="true" :model="queryParam">
        <div class="mt-4" style="width: 700px;">
          <el-form-item label="根据用户名称查询:">
            <el-input
              v-model="queryParam.userName"
              placeholder="用户名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="根据身份名称查询:">
            <el-input
              v-model="queryParam.roleName"
              placeholder="身份名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
          </div>
          <el-form-item style="display: block;">
          <el-button @click="queryUserRoleData">查询</el-button>
          </el-form-item>
      </el-form>
      </template>
    <div v-loading="loading">
      <el-table :data="userRoleData" border style="width: 100%">
        <el-table-column prop="userRoleId" label="用户角色ID" align="center" />
        <el-table-column prop="userName" label="用户名称" align="center" width="150" />
        <el-table-column prop="roleName" label="角色名称" align="center" width="150" />
        <el-table-column prop="createTime" label="创建时间" align="center" width="240" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" width="240" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column label="操作" width="150" align="center"/>
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
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref } from 'vue'
// 引入请求后端封装的API
import { userRoleApi, QueryParam } from '@/api/systemApi/sysUserRole-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'

// 数据分页总条数变量
const total = ref(0)
// 加载中进度条变量
const loading = ref(false)
// 定义分页查询请求参数
const queryParam = reactive<QueryParam>({
  currentPage: 1,
  pageSize: 8,
  userName: '',
  roleName: ''
})
// 定义接收Table表格数据
const userRoleData = ref([])
/**
 * 点击查询按钮或重新加载数据方法
 */
const queryUserRoleData = () => {
  queryParam.currentPage = 1
  queryParam.pageSize = 8
  // 执行查询方法
  loadUserRoleData()
}
/**
 * 分页查询表格数据
 */
const loadUserRoleData = async () => {
  const pageUserRoleList = {
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    roleName: queryParam.roleName,
    userName: queryParam.userName
  }
  const TestUseCasesRes = await userRoleApi.pageUserRole(pageUserRoleList)
  const data = TestUseCasesRes.data.data
  if (data && data.records) {
    userRoleData.value = data.records
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
  loadUserRoleData()
}
/**
 * 当前页改变事件
 * @param currentPage 新的当前页
 */
const handleCurrentChange = (currentPage: number) => {
  // 把新的当前页（currentPage）赋值给分页查询请求参数的当前页（queryParam.currentPage）
  queryParam.currentPage = currentPage
  // 重新执行查询方法
  loadUserRoleData()
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadUserRoleData()
})
</script>

<style lang="scss" scoped>
.sysUserRoleCases {
  .el-pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

<style lang="scss">
.sysUserRoleCases {
  .el-card__header {
    padding-bottom: 0 !important;
  }
}
.sysUserRoleCases-import-excel {
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
