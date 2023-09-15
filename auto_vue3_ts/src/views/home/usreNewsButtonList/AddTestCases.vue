<template>
  <div class="add_test_cases_main">
    <div class="add_test_cases_main_form">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="200px"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
        :hide-required-asterisk="true"
        style="margin-top: 20px;"
      >
        <el-divider>
          测试用例表单
        </el-divider>
        <el-form-item label="所属测试模块" prop="region">
          <el-select v-model="ruleForm.region" placeholder="三方测试">
            <el-option label="三方测试" value="shanghai" />
            <el-option label="设置中心" value="beijing" />
          </el-select>
        </el-form-item>
        <el-form-item label="测试项" prop="name">
          <el-input style="width: 300px;"  v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="测试标题" prop="name">
          <el-input style="width: 300px;"  v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="测试重要等级" prop="region">
          <el-select v-model="ruleForm.region" placeholder="三方测试">
            <el-option label="P0" value="shanghai" />
            <el-option label="P1" value="beijing" />
            <el-option label="P2" value="beijing" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="name">
          <el-input style="width: 300px;"  v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            Create
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
        </el-form-item>
        <el-divider content-position="left">操作步骤</el-divider>
        <el-form-item
          v-for="(domain, index) in dynamicValidateForm.domains"
          :key="domain.key"
          :label="'第' + (index + 1) + '步'"
          :prop="'domains.' + index + '.value'"
        >
          <el-input v-model="domain.value" style="width: 300px;" />
          <el-button style="margin-left: 5px;" size="small" :icon="CloseBold" circle @click.prevent="removeDomain(domain)" />
        </el-form-item>
        <el-form-item>
          <el-button @click="addDomain">添加步骤</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="add_test_cases_main_button">
      <el-button size="large" type="primary" :icon="DeleteFilled ">清空列表</el-button>
      <el-button style="margin-right: 30px;" size="large" type="primary" :loading="dd" @click="dd = true"><el-icon v-show="!dd" class="el-icon--right"><ArrowRightBold /></el-icon>&nbsp;添加用例</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DeleteFilled, ArrowRightBold, CloseBold } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'

const dd = ref(false)

interface RuleForm {
  name: string
  region: string
  count: string
  date1: string
  date2: string
  delivery: boolean
  type: string[]
  resource: string
  desc: string
}

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  name: 'Hello',
  region: '',
  count: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: ''
})

const rules = reactive<FormRules<RuleForm>>({
  name: [
    { required: true, message: '测试标题不能为空', trigger: 'blur' },
    { min: 1, max: 100, message: '长度必须为 1 to 100', trigger: 'blur' }
  ],
  region: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'change'
    }
  ],
  count: [
    {
      required: true,
      message: 'Please select Activity count',
      trigger: 'change'
    }
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change'
    }
  ],
  date2: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a time',
      trigger: 'change'
    }
  ],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change'
    }
  ],
  resource: [
    {
      required: true,
      message: 'Please select activity resource',
      trigger: 'change'
    }
  ],
  desc: [
    { required: true, message: 'Please input activity form', trigger: 'blur' }
  ]
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
// 添加输入框
const formRef = ref<FormInstance>()
interface DomainItem {
  key: number
  value: string
}
const dynamicValidateForm = reactive<{
  domains: DomainItem[]
  email: string
}>({
  domains: [
    {
      key: 1,
      value: ''
    }
  ],
  email: ''
})

const removeDomain = (item: DomainItem) => {
  const index = dynamicValidateForm.domains.indexOf(item)
  if (index !== -1) {
    dynamicValidateForm.domains.splice(index, 1)
  }
}

const addDomain = () => {
  dynamicValidateForm.domains.push({
    key: Date.now(),
    value: ''
  })
}

</script>

<style lang="scss">
.add_test_cases_main_form{
  height: auto;
  margin-top: 50px;
}
.add_test_cases_main_button{
  height: 80px;
  display: flex;
  justify-content: right;
  align-items: center;
}
</style>
