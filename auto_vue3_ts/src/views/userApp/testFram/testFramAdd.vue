<template>
  <div class="projectAddClass">
    <el-drawer v-model="drawer" :direction="direction" :show-close=false >
      <template #header>
        <h1>{{ dialogTile }}</h1>
      </template>
      <template #default>
        <el-form :model="testFramForm">
          <el-form-item label="框架名称" prop="testFramName">
            <el-input :prefix-icon="Reading" size="large" v-model="testFramForm.testFramName" :maxlength="20" :show-word-limit="true" />
          </el-form-item>
          <el-form-item label="框架Git链接" prop="testFramLink">
            <el-input :prefix-icon="Reading" size="large" v-model="testFramForm.testFramLink" :show-word-limit="true" />
          </el-form-item>
          <el-form-item label="框架描述描述" prop="testFramRefer">
            <el-input :prefix-icon="Warning" size="large" v-model="testFramForm.testFramRefer" :show-word-limit="true" />
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="drawer = false">取消</el-button>
          <el-button type="primary" @click="saveTestFram()">保存</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>
<script lang="ts" setup>
import { defineExpose, ref, watch, onMounted } from 'vue'
import {
  Reading,
  Warning
} from '@element-plus/icons-vue'
import axios from 'axios'

// 显示抽屉
const drawer = ref(false)
// 显示抽屉方向
const direction = ref('rtl')
// 标题
const dialogTile = ref('新增项目')
// 输入框表单
const testFramForm = ref({
  testFramId: '',
  testFramName: '',
  testFramLink: '',
  testFramRefer: ''
})
watch(
  () => drawer.value,
  async (newValue) => {
    if (newValue === false) {
      testFramForm.value = {
        testFramId: '',
        testFramName: '',
        testFramLink: '',
        testFramRefer: ''
      }
    }
  }
)
// 保存
const saveTestFram = () => {
  console.log('点击了保存')
  drawer.value = false
}
// 打开窗口
const testFramAdd = async (id?: string) => {
  drawer.value = true
}
defineExpose({
  testFramAdd
})
</script>
<style scoped lang="scss">
.projectAddClass{
  display: flex;
  flex-direction: row;
}
</style>
