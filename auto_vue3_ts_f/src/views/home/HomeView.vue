<template>
  <div class="contentD">
    <el-card class="sysUserCases" shadow="never">
      <template #header>
        <div style="padding: 10px;">
          <el-button @click="toNewlyTasks()">任务下发页</el-button>
          <el-button type="success" @click="toPassTacks()">测试完成列表</el-button>
          <el-button type="warning" @click="toTasksIng()">任务列表</el-button>
          <el-button type="danger" @click="toFallTacks()">测试异常列表</el-button>
        </div>
      </template>
      <el-scrollbar>
        <div class="viewS">
          <router-view v-slot="{ Component }">
            <transition name="fang">
              <component :is="Component" />
            </transition>
          </router-view>
        </div>
      </el-scrollbar>
      <div>
        <el-dialog :show-close="false" v-model="centerDialogVisible" title="登录" width="30%" center>
          <span>{{ massD }}</span>
          <el-input style="padding: 10px;" v-model="userAccount" placeholder="账号" userAccount />
          <el-input style="padding: 10px;" show-password type="password" v-model="userPassword" placeholder="密码" clearable />
          <template #footer>
            <span class="dialog-footer">
              <el-button type="primary" @click="toHomes()">登录</el-button>
            </span>
          </template>
        </el-dialog>
      </div>
      <div style="height: 30px;width: 100%;color: #bbbbbb;">....</div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { watch, ref, onMounted } from 'vue'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
import jwtDecode from 'jwt-decode'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { userApi } from '@/api/systemApi/sysUser-api'

const router = useRouter()
const titleName = ref()
const thisPath = router.currentRoute.value.fullPath
if (thisPath === '/') {
  titleName.value = '下发任务'
} else if (thisPath === '/passTacks') {
  titleName.value = '已完成任务'
} else if (thisPath === '/tasksIng') {
  titleName.value = '任务列表'
} else if (thisPath === '/fallTacks') {
  titleName.value = '失败任务'
}
watch(
  () => router.currentRoute.value.fullPath,
  (newValue) => {
    if (newValue === '/') {
      titleName.value = '下发任务'
    } else if (newValue === '/passTacks') {
      titleName.value = '已完成任务'
    } else if (newValue === '/tasksIng') {
      titleName.value = '任务列表'
    } else if (newValue === '/fallTacks') {
      titleName.value = '失败任务'
    }
  })
const toNewlyTasks = () => {
  router.push({
    path: '/'
  }).catch(err => {
    console.log(err)
  })
}
const toTasksIng = () => {
  router.push({
    path: '/tasksIng'
  }).catch(err => {
    console.log(err)
  })
}
const toFallTacks = () => {
  router.push({
    path: '/fallTacks'
  }).catch(err => {
    console.log(err)
  })
}
const toPassTacks = () => {
  router.push({
    path: '/passTacks'
  }).catch(err => {
    console.log(err)
  })
}

const userAccount = ref('')
const userPassword = ref('')
// 登录函数
const toHomes = async () => {
  // 定义传输的对象
  const loginlist = {
    userAccount: userAccount.value,
    userPassword: userPassword.value
  }
  // 调用请求后端API
  const userRes = await userApi.loginUser(loginlist)
  if (userRes.data.code === 1016) {
    // 将token保存到浏览器
    localStorage.setItem(
      'Authorization', userRes.data.data.Authorization
    )
    ElMessage({
      message: '登录成功.',
      type: 'success'
    })
    centerDialogVisible.value = false
    location.reload()
  } else {
    centerDialogVisible.value = true
    ElMessage.error('数据库无该账号，请查证重试！')
  }
}
const centerDialogVisible = ref(false)
const massD = ref('下发任务前提需要登录，token不存在需要登录')
const getToken = () => {
  if (localStorage.getItem('Authorization') !== null) {
    // 获取当前时间
    const currentTime = new Date().getTime()
    // token设置的时间戳
    const tokenTime:any = jwtDecode(localStorage.getItem('Authorization'))
    // 计算当前时间和token的时间差值
    const time = currentTime - tokenTime.currentTime
    // 计算差值的分钟数
    const min = time / 1000 / 60
    // 定义token的有效时间 一天
    const validity = (1000 * 60 * 60 * 24) / 1000 / 60
    // console.log(tokenTime.systemUser)
    if (min > validity) {
      massD.value = 'token超时, 需要重新登录！'
      centerDialogVisible.value = true
    }
  } else {
    massD.value = '未登录用户, 下发任务需要登录!'
    centerDialogVisible.value = true
  }
}
/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  getToken()
})
</script>

<style lang="scss" scoped>
.sysUserCases {
  .el-pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

<style lang="scss">
.contentHeader{
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.contentD{
  width: 100%;
  display: flex;
  justify-content: center;
  height: calc(100vh - 10px);
}
.viewS{
  width: 1200px;
  height: auto;
}
.sysUserCases {
  .el-card__header {
    padding-bottom: 0 !important;
  }
}
.sysUserCases-import-excel {
  .el-dialog__body {
    border-top: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
.input-with-select{
  width: 190px;
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
</style>
