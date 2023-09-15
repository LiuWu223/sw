<template>
  <el-row class="login" justify="center" align="middle">
    <el-col :span="14" class="login-contain">
      <el-row class="login-form" justify="center">
        <el-col :span="12" class="login-form-left">
          <div></div>
          <span>seewo自动化测试平台</span>
          <p>安排合理，高效工作</p>
        </el-col>
        <el-col :span="12" class="login-form-right">
          <span>登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</span>
          <el-form :model="loginForm" style="margin-top:20px">
            <el-form-item prop="userAccount">
              <el-input v-model="loginForm.userAccount" size="large" placeholder="请输入用户登录账号" :prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="userPassword">
              <el-input v-model="loginForm.userPassword" type="password" size="large" placeholder="请输入用户登录密码" :prefix-icon="Lock" />
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="loginForm.remember" label="记住密码" size="large" />
              <el-checkbox v-model="loginForm.noLogin" label="两周内免登录" size="large" />
            </el-form-item>
            <el-form-item>
              <div></div>
              <el-button type="primary" size="large" style="width: 100%;" @click="login"><p v-if="!logdings">登&nbsp;&nbsp;录</p><i v-if="logdings" class="loading"></i></el-button>
            </el-form-item>
            <el-form-item>
              <el-row justify="space-between" style="width: 100%;">
                <!-- 测试 -->
                <div>
                  <button @click="adbDevices">获取adb设备</button>
                  <button @click="gitClone">下载到服务器</button>
                  <input v-model="gitPath" type="text" placeholder="请输入git代码地址" />
                </div>
              </el-row>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { ElNotification, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useDebounce } from '@/utils/debounce'
// 引入请求后端封装的API
import { userLoginApi } from '@/api/systemApi/login-api'
// ==========================================
const adbDevices = () => {
  const a = userLoginApi.adbDevices()
  console.log(a)
}
const gitPath = ref('')

const gitClone = () => {
  const paths = {
    gitPath: gitPath.value
  }
  const a = userLoginApi.gitClone(paths)
  console.log(a)
}
// ===========================
const router = useRouter()
const loginForm = reactive({
  userAccount: '',
  userPassword: '',
  remember: false,
  noLogin: false
})
// 登录加载动画
const logdings = ref(false)
// 登录函数
const toHomes = async () => {
  if (loginForm.userAccount === '') {
    ElNotification({
      title: 'Error',
      message: '请输入账号！',
      type: 'error',
      position: 'bottom-right'
    })
    return
  } else if (loginForm.userPassword === '') {
    ElNotification({
      title: 'Error',
      message: '请输入密码！',
      type: 'error',
      position: 'bottom-right'
    })
    return
  }
  // 用户输入账号密码
  const userList = {
    userAccount: loginForm.userAccount,
    userPassword: loginForm.userPassword
  }
  if (logdings.value === false) { logdings.value = true }
  // 调用登录接口
  await userLoginApi.loginUser(userList).then(userRes => {
    // 判断登录成功
    if (userRes.data.code === 1016) {
      // 将token保存到浏览器
      localStorage.setItem(
        'Authorization', userRes.data.data.Authorization
      )
      // 跳转到首页
      router.push({
        name: 'IndexView',
        replace: true
      }).catch(err => {
        console.log(err)
        ElMessage.error('无法跳转到首页')
      })
      if (logdings.value === true) { logdings.value = false }
    } else {
      if (logdings.value === true) { logdings.value = false }
    }
  }).catch(error => {
    // 登录错误处理
    if (logdings.value === true) { logdings.value = false }
    console.log(error)
  })
}
// 防抖函数的使用
const login = useDebounce(toHomes, 1000)
</script>

<style scoped lang="scss">
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 170px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
// .loading {
//   width: 40px;
//   height: 40px;
//   margin: auto;
//   // top: 525px;
//   position: absolute;
// }
.loading::before {
  content: "";
  width: 4px;
  height: 4px;
  border-radius: 100%;
  color: rgba(255, 255, 255, 0.4);
  box-shadow: 0 -10px rgba(255, 255, 255, 0.9), /* top */ 10px 0px,
    /* right */ 0 10px, /* bottom */ -10px 0 rgba(255, 255, 255, 0.7),
    /* left */ -7px -7px rgba(255, 255, 255, 0.8),
    /* left-top */ 7px -7px rgb(255, 255, 255), /* right-top */ 7px 7px,
    /* right-bottom */ -7px 7px; /* left-bottom */
  animation: spin 1s steps(8) infinite;
  /* center */
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  margin: auto;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
.el-col-14{
  max-width: 970px;
  flex: 0 0 970px;
}
.login {
  width: 100%;
  height: 100vh;
  background-image: url("../../assets/images/background.jpg");
  background-size: 100% 100%;
  .login-contain {
    height: 630px;
    border-radius: 15px;
    background: -webkit-gradient(linear, left top, right bottom, color-stop(0%, rgba(36, 199, 252, 0.5)), color-stop(100%, rgba(7, 71, 243, 0.5)));
    width: 970px;
    .login-form, .login-form-left, login-form-right {
      height: 100%;
      .login-form-left {
        background-image: url("../../assets/images/background_login_left.png");
        background-size: 94% 100%;
        border-radius: 15px 0 0 15px;
        padding: 40px;
        div {
          width: 40px;
          height: 5px;
          margin-bottom: 20px;
          background-color: var(--el-color-white);
        }
        span {
          color: var(--el-color-white);
          font-size: 28px;
        }
        p {
          color: var(--el-color-white);
          font-size: 14px;
        }
      }

      .login-form-right {
        background-image: url("../../assets/images/background_login_right.png");
        background-size: 100% 100%;
        border-radius: 0 15px 15px 0;
        padding-top: 30px;
        text-align: center;
        span {
          color: var(--el-color-primary);
          font-size: 24px;
          font-weight: bold;
          border-bottom: 5px solid rgba(64, 158, 255, 0.3);
        }
        .el-form {
          width: 70%;
          margin: 60px auto 0;
          .el-form-item {
            margin-top: 20px;
          }
        }
      }
    }
  }
}
.login, .login-form-left, .login-form-right {
  background-position: center;
  background-repeat: no-repeat;
}
</style>
