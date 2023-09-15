<template>
  <el-dialog
    class="userRoleAdd-edit"
    draggable
    align-center
    destroy-on-close
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="dialogTile"
    v-model="dialogVisible"
    @close="closeDialog"
    width="800">
    <el-form :model="userRoleForm" :rules="userRoleRules" ref="userRoleFormRef">
      <el-form-item label="角色Code：" prop="userRoleCode">
        <el-input v-model="userRoleForm.userRoleCode" placeholder="角色Code" :maxlength="50" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="角色名称：" prop="userRoleName">
        <el-input v-model="userRoleForm.userRoleName" placeholder="角色名称" :maxlength="100" :show-word-limit="true" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="saveUserUserRole(userRoleFormRef)">保存</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
// 引入vue3的组合式API
import { ref, reactive, defineExpose, defineEmits } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessageBox } from 'element-plus'
// Element组件库的相关类型
import type { FormInstance, FormRules } from 'element-plus'
// 引入学校后端请求API
import { userRoleApi } from '@/api/systemApi/sysUserRole-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'

// 定义组件事件
const emits = defineEmits(['reload'])

// 定义form表单的双向绑定的值
const userRoleForm = ref({
  // 角色Code
  userRoleCode: '',
  // 角色名称
  userRoleName: ''
})
// 定义form表单的验证规则
const userRoleRules = reactive<FormRules>({
  // 测试用例ID
  userRoleCode: [
    // 必填，提示信息为：请输入编码，触发事件为：失去焦点
    { required: true, message: '请输入角色Code', trigger: 'blur' }
  ],
  // 测试用例名称
  userRoleName: [
    // 必填，提示信息为：请输入学校名称，触发事件为：失去焦点
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ]
})
// 定义是否显示新增修改弹窗
const dialogVisible = ref(false)
// 定义新增修改弹窗标题
const dialogTile = ref('')
// Form 表单的Ref变量
const userRoleFormRef = ref<FormInstance>()
/**
 * 打开角色新增修改弹窗
 * @param userRoleId 角色ID
 */
const openDialog = async (userRoleId?: string) => {
  // 显示新增修改弹窗
  dialogVisible.value = true
  // 判断测试用例是否为空
  if (userRoleId) {
    // 不为空把弹窗标题改为：角色修改
    dialogTile.value = '角色修改'
    // 根据学校ID获取测试用例信息
    const getUserRoleByIdRes = await userRoleApi.getUserRoleById(userRoleId)
    // 获取查询出来的数据
    const data = getUserRoleByIdRes.data.data
    // 将查询出来的数据赋值给testUseCasesForm
    userRoleForm.value = data
  } else {
    // 为空把弹窗标题改为：角色新增
    dialogTile.value = '角色新增'
  }
}
/**
 * 保存测试用例信息
 * @param formEl 测试用例信息输入的表单
 */
const saveUserUserRole = async (formEl: FormInstance | undefined) => {
  // 判断表单是否为空
  if (!formEl) return
  // 验证表单的信息是否都符合验证规则里面的规则
  await formEl.validate(async (valid) => {
    // 如果验证通过则执行保存方法
    if (valid) {
      // 调用后端测试用例信息保存接口
      const res = await userRoleApi.save(userRoleForm.value)
      // 判断是否保存成功
      if (res.data.success) {
        // 提示保存成功
        await ElMessageBox.alert(res.data.message, '提示信息', {
          type: 'success',
          confirmButtonText: '确定',
          callback: () => {
            // 清空表单的数据
            formEl.resetFields()
            // 重新加载学校列表数据
            emits('reload')
            // 关闭弹窗
            dialogVisible.value = false
          }
        })
      } else {
        // 保存失败提示信息
        await ElMessageBox.alert(res.data.message, '提示信息', {
          type: 'error',
          confirmButtonText: '确定'
        })
      }
    }
  })
}

const closeDialog = () => {
  // 初始化form表单的双向绑定的值
  userRoleForm.value = {
    // 角色Code
    userRoleCode: '',
    // 角色名称
    userRoleName: ''
  }
}

// 向外暴露组件的属性或函数
defineExpose({
  openDialog
})

</script>

<style lang="scss">
.TestUseCases-edit {
  .avatar-uploader .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }
  .el-dialog__body {
    border-top: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }
  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }
  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    text-align: center;
  }

}
</style>
