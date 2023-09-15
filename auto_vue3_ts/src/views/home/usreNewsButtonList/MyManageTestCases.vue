<template>
  <el-row>
    <el-col
      v-for="(o, index) in 2"
      :key="o"
      :span="8"
      :offset="index > 0 ? 2 : 0"
    >
      <el-card :body-style="{ padding: '0px' }">
        <div class="image">Git拉去测试框架</div>
        <div style="padding: 14px">
          <span>Yummy hamburger</span>
          <div class="bottom">
            <time class="time">{{ currentDate }}</time>
            <el-button key="primary" type="primary" link @click="userCmd" >下载使用</el-button>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { userLoginApi } from '@/api/systemApi/login-api'

const currentDate = ref(new Date())
const userCmd = async () => {
  const userFrom = {
    // git clone https://github.com/LiuWu223/myRepository.git
    userCmd: 'adb devices'
  }
  userLoginApi.usreCmd(userFrom).then(res => {
    console.log(res)
  }).catch(err => {
    console.log(err)
  })/*  */
}
</script>

<style>
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
  height: 200px;
  display:flex;
  align-items: center;
  justify-content: center;
}
.el-col-8{
  max-width: 200px;
  flex: 0 0 200px;
}
</style>
