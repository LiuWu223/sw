<template>
  <el-card shadow="never">
    <template #header>
      <el-form :inline="true" :model="pageUserParamC">
        <div class="mt-4" style="width: 960px; display: flex; padding: 20px; justify-content: space-between;">
          <el-form-item label="用户名查找" style="display: flex; justify-content: center; align-items: center;">
            <el-input
              v-model="pageUserParamC.userName"
              placeholder="用户名称"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="账号查找" style="display: flex; justify-content: center; align-items: center;">
            <el-input
              v-model="pageUserParamC.userAccount"
              placeholder="用户账号"
              class="input-with-select"
              size="large"
              clearable
            >
            </el-input>
          </el-form-item>
        </div>
        <el-form-item style="display: block;">
        <el-button @click="loadData">查询</el-button>
        <el-button type="primary" @click="upDateByUser()">新增</el-button>\
        <el-button type="danger" @click="removeRow(null)">批量删除</el-button>
        </el-form-item>
      </el-form>
    </template>
    <div v-loading="loading">
      <el-table :data="userData" border style="width: 100%; height:600px;" @select="selectUserRow">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="id" label="路由编号" align="center" />
        <el-table-column prop="routeLabel" label="路由标题" align="center" />
        <el-table-column prop="routeHigher" label="上一级" align="center" />
        <el-table-column prop="routeComponent" label="路由component" align="center" />
        <el-table-column prop="routeName" label="路由name" align="center" />
        <el-table-column prop="routePath" label="路由path" align="center" />
        <el-table-column prop="routeIcon" label="路由icon" align="center" />
        <el-table-column prop="routeHidden" label="是否遍历在第一列表" align="center" />
        <el-table-column prop="createTime" label="创建时间" align="center" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column prop="deleteFlag" width="100" label="删除状态" align="center" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="upDateByUser(scope.row.id)">编辑</el-button>
            <el-button style="color: #ff4400;" link type="primary" size="small" @click="removeRow(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="pageUserParamC.currentPage"
        :page-size="pageUserParamC.pageSize"
        :page-sizes="[8, 50, 100, 500]"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <!-- 添加或修改抽屉 -->
    <el-drawer v-model="drawer" :direction="direction" :show-close=false >
      <template #header>
        <h1>{{ dialogTile }}</h1>
      </template>
      <template #default>
        <el-form :model="userForm">

          <el-form-item label="路由标题" prop="routeLabel">
            <el-input :prefix-icon="User" size="large" v-model="userForm.routeLabel" :maxlength="20" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="上一级" prop="routeHigher">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.routeHigher" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="路由component" prop="routeComponent">
            <el-input :prefix-icon="Key" size="large" v-model="userForm.routeComponent" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="路由name" prop="routeName">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.routeName" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="路由path" prop="routePath">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.routePath" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="路由icon" prop="routeIcon">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.routeIcon" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="是否遍历在第一列表" prop="routeHidden">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.routeHidden" :maxlength="11" :show-word-limit="true" />
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="drawer = false">取消</el-button>
          <el-button type="primary" @click="saveRoute()">保存</el-button>
        </div>
      </template>
    </el-drawer>
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref, watch } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, ElMessageBox, ElNotification } from 'element-plus'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import { Plus, Search, User, Phone, Key } from '@element-plus/icons-vue'
import { syDictApi } from '@/api/syApi/syDict-api'
import { syUserApi, pageUserParam } from '@/api/syApi/syUser-api'
import { syRoleApi } from '@/api/syApi/syRole-api'

/**
 * 新增修改抽屉
 */
// 显示抽屉
const drawer = ref(false)
// 显示抽屉方向
const direction = ref('rtl')
// 标题
const dialogTile = ref('')
// 输入框表单
const userForm = ref({
  routeId: '',
  routeLabel: '',
  routeHigher: '',
  routeComponent: '',
  routeName: '',
  routePath: '',
  routeIcon: '',
  routeHidden: ''
})
// 当新增修改窗口消失时，清空输入框类容
watch(
  () => drawer.value,
  async (newValue) => {
    if (newValue === false) {
      userForm.value = {
        routeId: '',
        routeLabel: '',
        routeHigher: '',
        routeComponent: '',
        routeName: '',
        routePath: '',
        routeIcon: '',
        routeHidden: ''
      }
    }
  }
)
const assertSce = (nameVule) => {
  ElNotification({
    title: 'Error',
    message: nameVule + '必要存在的',
    type: 'error'
  })
}
// 保存并上传信息
const saveRoute = async () => {
  if (userForm.value.routeLabel === '') { return assertSce('标题') }
  if (userForm.value.routeHigher === '') { return assertSce('上一级') }
  if (userForm.value.routeComponent === '') { return assertSce('路由component') }
  if (userForm.value.routeName === '') { return assertSce('路由name') }
  if (userForm.value.routePath === '') { return assertSce('路由path') }
  if (userForm.value.routeIcon === '') { return assertSce('路由icon') }
  if (userForm.value.routeHidden === '') { return assertSce('是否遍历在第一列表') }

  await syUserApi.updateUser(userForm.value).then(data => {
    if (data.data.success) {
      ElNotification({
        title: 'Success',
        message: dialogTile.value + '成功',
        type: 'success'
      })
      drawer.value = false
      loadData()
      return
    }
    ElNotification({
      title: 'Error',
      message: dialogTile.value + '失败',
      type: 'error'
    })
    drawer.value = false
  }).catch(err => {
    ElNotification({
      title: 'Error',
      message: dialogTile.value + '失败' + err,
      type: 'error'
    })
    drawer.value = false
  })
}
// 修改用户信息回填数据
const upDateByUser = async (id?: string) => {
  drawer.value = true
  if (id) {
    dialogTile.value = '修改用户信息'
    await syUserApi.selectUserById(id).then(data => {
      const upUser = data.data.data
      // 填充用户信息
      userForm.value = {
        routeId: '',
        routeLabel: '',
        routeHigher: '',
        routeComponent: '',
        routeName: '',
        routePath: '',
        routeIcon: '',
        routeHidden: ''
      }
    }).catch(err => {
      console.log(err)
      console.log('用户信息获取失败')
    })
  } else {
    // 为空把弹窗标题改为：用户新增
    dialogTile.value = '添加新用户'
  }
}
/**
 * 数据展现与查询信息
 */

// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(true)
interface Users {
  id: string
}
// 定义分页查询请求参数
const pageUserParamC = reactive<pageUserParam>({
  // 当前页
  currentPage: 1,
  // 一页显示条数
  pageSize: 8,
  userName: '',
  userAccount: '',
  roleName: '全部'
})
// 定义接收Table表格数据
const userData = ref([])
/**
 * 点击查询按钮或重新加载数据方法
 */
const queryUserData = () => {
  // 令分页查询参数的当前页初始化为1
  pageUserParamC.currentPage = 1
  // 令分页查询参数的一页显示条数初始化为1
  pageUserParamC.pageSize = 8
  loadData()
}
// 定义接收用户ID集合（用于删除数据）
let userIds: string[] = []
// 表格选中事件
const selectUserRow = (selection: Users[]) => {
  userIds = []
  selection.forEach(userRow => {
    userIds.push(userRow.id)
  })
}

const removeRow = (userId: string | null) => {
  ElMessageBox.confirm('是否删除点击或选择的用户信息数据?', '提示消息', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  }).then(async () => {
    // 判断是否为单条数据
    if (userId) {
      // 如果为单条数据直接装载进去
      userIds = [userId]
    } else {
      if (userIds.length === 0) {
        ElMessage({
          type: 'warning',
          message: '请选择需要删除的数据'
        })
        return
      }
    }
    const removeUser = await syUserApi.removeUserIds({ userIds: userIds.join(',') })
    if (removeUser.data.success) {
      // 清空删除集合
      userIds = []
      // 重新加载数据
      queryUserData()
      await ElMessageBox.alert(removeUser.data.message, '提示信息', {
        type: 'success',
        confirmButtonText: '确定'
      })
    } else {
      // 提示删除失败
      await ElMessageBox.alert(removeUser.data.message, '提示信息', {
        type: 'error',
        confirmButtonText: '确定'
      })
    }
  }).catch(err => {
    // 取消删除
    console.log(err)
  })
}
// 分页查询数据
const loadData = async () => {
  await syUserApi.pageUser(pageUserParamC).then(data => {
    // 用户数据
    const userList:any = data
    if (!userList.data.success) {
      console.log('获取用户列表失败')
      loading.value = false
      return
    }
    // 用户list
    userData.value = userList.data.data.records
    // 总数量
    total.value = Number(userList.data.data.total)
    // 数据加载动画
    loading.value = false
  }).catch(err => {
    console.log('获取用户列表失败')
    console.log(err)
  })
}

// 一页的大小
const handleSizeChange = (pageSize: number) => {
  pageUserParamC.pageSize = pageSize
  loadData()
}
// 去到第几页
const handleCurrentChange = (currentPage: number) => {
  pageUserParamC.currentPage = currentPage
  loadData()
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadData()
})
</script>

<style lang="scss">
</style>
