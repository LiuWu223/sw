<template>
  <el-dialog
    class="permissionsAdd-edit"
    draggable
    align-center
    destroy-on-close
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="dialogTile"
    v-model="dialogVisible"
    @close="closeDialog"
    width="800">
    <el-form :model="permissionsForm" :rules="permissionsRules" ref="permissionsFormRef">
      <el-form-item label="权限Code：" prop="permissionsCode">
        <el-input v-model="permissionsForm.permissionsCode" placeholder="权限Code" :maxlength="50" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="权限名称：" prop="permissionsName">
        <el-input v-model="permissionsForm.permissionsName" placeholder="权限名称" :maxlength="100" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="权限类型：" prop="permissionsType">
        <el-input v-model="permissionsForm.permissionsType" placeholder="权限类型" :maxlength="100" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="权限URL：" prop="permissionsUrl">
        <el-input v-model="permissionsForm.permissionsUrl" placeholder="权限URL" :maxlength="100" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="权限Icon：" prop="permissionsIcon">
        <el-input v-model="permissionsForm.permissionsIcon" placeholder="权限Icon" :maxlength="100" :show-word-limit="true" />
      </el-form-item>
      <el-form-item label="放在哪个路径下面：" prop="permissionsFatherId">
        <p>Filterable (Single selection)</p>
        <el-cascader
          placeholder="Try searchingL Guide"
          :options="options"
          filterable
          v-model="rrr"
        />
        <!-- <el-input v-model="permissionsForm.permissionsFatherId" placeholder="父ID" :maxlength="100" :show-word-limit="true" /> -->
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="savePermissions(permissionsFormRef)" :disabled="savaButton">保存</el-button>
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
import { permissionsApi } from '@/api/systemApi/sysPermissions-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'

const options = [
  {
    value: 'guide',
    label: 'Guide'
  }
]
// 定义组件事件
const emits = defineEmits(['reload'])
const savaButton = ref(false)
// 定义form表单的双向绑定的值
const permissionsForm = ref({
  // 权限Code
  permissionsCode: '',
  // 权限名称
  permissionsName: '',
  permissionsFatherId: '',
  permissionsIcon: '',
  permissionsUrl: '',
  permissionsType: ''
})
// 定义form表单的验证规则
const permissionsRules = reactive<FormRules>({
  permissionsCode: [
    { required: true, message: '请输入权限Code', trigger: 'blur' }
  ],
  permissionsName: [
    { required: true, message: '请输入权限名称', trigger: 'blur' }
  ],
  permissionsFatherId: [
    { required: true, message: '请输入父ID', trigger: 'blur' }
  ],
  permissionsIcon: [
    { required: true, message: '请输入权限Icon', trigger: 'blur' }
  ],
  permissionsUrl: [
    { required: true, message: '请输入权限URL', trigger: 'blur' }
  ],
  permissionsType: [
    { required: true, message: '请输入权限类型', trigger: 'blur' }
  ]
})
// 定义是否显示新增修改弹窗
const dialogVisible = ref(false)
// 定义新增修改弹窗标题
const dialogTile = ref('')
// Form 表单的Ref变量
const permissionsFormRef = ref<FormInstance>()
/**
 * 打开权限新增修改弹窗
 * @param permissionsId 权限ID
 */
const openDialog = async (permissionsId?: string) => {
  // 显示新增修改弹窗
  dialogVisible.value = true
  if (permissionsId) {
    // 不为空把弹窗标题改为：权限修改
    dialogTile.value = '修改'
    const getPermissionsByIdRes = await permissionsApi.getPermissionsById(permissionsId)
    // 获取查询出来的数据
    const data = getPermissionsByIdRes.data.data
    // 将查询出来的数据赋值给testUseCasesForm
    permissionsForm.value = data
    if (permissionsForm.value.permissionsType) {
      permissionsForm.value.permissionsType = '1'
    } else {
      permissionsForm.value.permissionsType = '0'
    }
  } else {
    // 为空把弹窗标题改为：权限新增
    dialogTile.value = '新增'
  }
}
/**
 * 保存测试用例信息
 * @param formEl 测试用例信息输入的表单
 */
const savePermissions = async (formEl: FormInstance | undefined) => {
  // 判断表单是否为空
  if (!formEl) return
  // 验证表单的信息是否都符合验证规则里面的规则
  await formEl.validate(async (valid) => {
    // 如果验证通过则执行保存方法
    if (valid) {
      savaButton.value = true
      if (dialogTile.value === '新增') {
        const selectName = await permissionsApi.selectPermissionsName(permissionsForm.value)
        if (selectName.data.code === 1014) {
          await ElMessageBox.alert(selectName.data.message, '提示信息', {
            type: 'error',
            confirmButtonText: '确定'
          })
          return
        }
      }
      // 调用后端测试用例信息保存接口
      const res = await permissionsApi.save(permissionsForm.value)
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
            savaButton.value = false
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
  permissionsForm.value = {
    // 权限Code
    permissionsCode: '',
    // 权限名称
    permissionsName: '',
    permissionsFatherId: '',
    permissionsIcon: '',
    permissionsUrl: '',
    permissionsType: ''
  }
}

// 向外暴露组件的属性或函数
defineExpose({
  openDialog
})

</script>

<style lang="scss">
.permissionsAdd-edit{
  border-radius: 10px;
}
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
