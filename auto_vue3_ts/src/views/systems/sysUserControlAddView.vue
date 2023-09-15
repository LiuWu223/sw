<template>
  <!-- 添加或修改抽屉 -->
  <el-drawer v-model="drawer" :direction="direction" :show-close=false >
    <template #header>
      <h1>{{ dialogTile }}</h1>
    </template>
    <template #default>
      <el-form :model="userForm" ref="userFormRef">

        <el-form-item label="用户名称" prop="userName">
          <el-input :prefix-icon="User" size="large" v-model="userForm.userName" :maxlength="20" :show-word-limit="true" />
        </el-form-item>

        <el-form-item label="用户账号" prop="userAccount">
          <el-input :prefix-icon="Phone" size="large" v-model="userForm.userAccount" :maxlength="11" :show-word-limit="true" />
        </el-form-item>

        <el-form-item label="用户密码" prop="userPassword">
          <el-input :prefix-icon="Key" size="large" v-model="userForm.userPassword" :maxlength="11" :show-word-limit="true" />
        </el-form-item>

        <el-form-item label="身份选择" prop="rolei">
          <el-select
          v-model="rolei"
          placeholder="选择身份"
          @focus="foucsRolei"
          size="large"
          >
            <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户头像" prop="userAvatar">
          <template v-if="userForm.userAvatar !== ''">
            <el-upload
              ref="upload"
              v-model:file-list="fileList"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :auto-upload="false"
              class="upload-demo"
              :limit="1"
              :headers="headers"
              :on-exceed="handleExceed"
              :on-change="handleChange"
            >
              <template #file="{ file }">
              <div>
                <img class="el-upload-list__item-thumbnail" @error='imgOnError' :src="file.url" alt="" />
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                  <el-icon><Search /></el-icon>
                  </span>
                </span>
              </div>
            </template>
              <el-icon><Plus /></el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisibles">
              <img w-full style="width:100%;" :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>
          </template>
          <template v-if="userForm.userAvatar === ''">
            <el-upload
              ref="upload"
              v-model:file-list="fileList"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :auto-upload="false"
              class="upload-demo"
              :limit="1"
              :headers="headers"
              :on-exceed="handleExceed"
              :on-change="handleChange"
            >
              <template #file="{ file }">
              <div>
                <img class="el-upload-list__item-thumbnail"  :src="file.url" alt="" />
                <span class="el-upload-list__item-actions">
                  <span
                    style="position: absolute;right: 10px;top: 120px;"
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                  <el-icon><Search /></el-icon>
                  </span>
                </span>
              </div>
            </template>
              <el-icon><Plus /></el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisibles">
              <img w-full style="width:100%;" :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>
          </template>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="ecancel(userFormRef)">取消</el-button>
        <el-button type="primary" @click="saveUser(userFormRef)">保存</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref, watch } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, UploadProps, UploadUserFile, UploadInstance, UploadRawFile, genFileId, FormInstance, ElNotification } from 'element-plus'
// 引入请求后端封装的API
import { userApi, QueryParam } from '@/api/systemApi/sysUser-api'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import { Plus, Search, User, Phone, Key } from '@element-plus/icons-vue'
import axios from '@/utils/axios'
import jwtDecode from 'jwt-decode'
import { userLoginApi } from '@/api/systemApi/login-api'
import { roleApi } from '@/api/systemApi/sysRole-api'
import { userRoleApi } from '@/api/systemApi/sysUserRole-api'
// =======================================================抽屉==
// 显示抽屉
const drawer = ref(false)
// 显示抽屉方向
const direction = ref('rtl')
// 标题
const dialogTile = ref('')
// 输入框表单
const userForm = ref({
  userName: '',
  userAccount: '',
  userPassword: '',
  userAvatar: ''
})
// 替换数据
const userIs = ref({
  userAccount: '',
  userAvatar: '',
  userName: '',
  userPassword: '',
  id: '',
  acc: ''
})
// 重新加载数据
// 文件上传列表
const fileList = ref<UploadUserFile[]>([])
// 表单上传
// Form 表单的Ref变量
const userFormRef = ref<FormInstance>()
// 存放文件的路径
const fileListData = []
// 选中的文件
const fileListRef = ref()
// 放大图片地址
const dialogImageUrl = ref('')
// 预览图片显示
const dialogVisibles = ref(false)
// 身份选择下拉框
const roleList = ref([])
// 选中身份
const rolei = ref()
// 文件上传请求头参数
const headers = { Authorization: localStorage.getItem('Authorization') }
// 不确定变量
const isImageOnErrorNot = ref(true)
const isImageOnRedy = ref(false)
// 点击文件列表中已上传文件时的钩子
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url
  dialogVisibles.value = true
}
// 过滤文件,控制只能上传一个，再次选择则替换
const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value.handleStart(file)
}
// 控制上传的文件格式与文件大小
const beforeAvatarUpload = (file, fileList) => {
  const fileTypeList = ['pdf', 'png', 'jpeg', 'jpg']
  const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
  const isType = (fileTypeList.indexOf(fileType) === -1)
  if (isType) {
    ElMessage.error('只支持pdf、png、jpeg、jpg格式')
    // 清空文件列表
    fileList.splice(fileList.indexOf(file), 1)
    fileListRef.value = fileList.indexOf(file)
    return false
  } else if (file.size / 1024 / 1024 > 12) {
    ElMessage.error('大小不能超过 12MB!')
    // 清空文件列表
    fileList.splice(fileList.indexOf(file), 1)
    fileListRef.value = fileList.indexOf(file)
    return false
  }
  return true
}
// 校验文件大小
const handleChange = (file, fileList) => {
  if (file.status !== 'ready') { return false }
  beforeAvatarUpload(file, fileList)
  fileListData.values = fileList
  isImageOnErrorNot.value = true
  isImageOnRedy.value = true
}
// 图片未加载出禁止上传图片
const imgOnError = () => { isImageOnErrorNot.value = false }
// 头像上传方法
const submitUpload = async () => {
  const formData = new FormData()
  formData.append('file', fileListData.values[0].raw)
  await axios.post('/common/upload/single', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(res => {
    if (res.data.code !== 1008) {
      ElMessage.error('头像上传失败')
    } else {
      ElMessage.success('头像上传成功')
      userForm.value.userAvatar = res.data.data
    }
  })
}
watch(
  () => drawer.value,
  async (newValue) => {
    if (newValue === false) {
      userForm.value = {
        userName: '',
        userAccount: '',
        userPassword: '',
        userAvatar: ''
      }
      rolei.value = ''
    }
  }
)
const ecancel = (userFormRef) => {
  drawer.value = false
  // 清空校验信息
  userFormRef.resetFields()
}
const open1 = (nameVule) => {
  ElNotification({
    title: 'Error',
    message: nameVule + '必要存在的',
    type: 'error'
  })
}
const jj = ref({
  userRoleId: '',
  userId: '',
  roleId: ''
})
// 查询出来的身份
const thisRole = ref()
// 保存并上传信息
const saveUser = async (formEl: FormInstance | undefined) => {
  if (userForm.value.userName === '') {
    open1('昵称')
    return
  }
  if (userForm.value.userAccount === '') {
    open1('账号')
    return
  }
  if (userForm.value.userPassword === '') {
    open1('密码')
    return
  }
  if (rolei.value === undefined) {
    open1('身份')
    return
  }
  if (userIs.value.userAccount !== userForm.value.userAccount) {
    const selectName = await userApi.selectUserName(userForm.value)
    if (selectName.data.code === 1014) {
      ElMessage({
        message: selectName.data.message,
        type: 'error'
      })
      return
    }
  }
  // 上传头像
  // fileListRef.value !== -1 且文件列表不能为空
  if (isImageOnErrorNot.value && isImageOnRedy.value && fileListRef.value !== -1) {
    await submitUpload()
  }
  isImageOnErrorNot.value = true
  isImageOnRedy.value = false
  if (userForm.value.userName === userIs.value.userName &&
  userForm.value.userAccount === userIs.value.userAccount &&
  userForm.value.userPassword === userIs.value.userPassword &&
  userForm.value.userAvatar === userIs.value.userAvatar &&
  thisRole.value === rolei.value) {
    drawer.value = false
    fileList.value = []
    // 清空表单的数据
    formEl.resetFields()
    // 重新加载用户列表数据
    loadUserData()
    return
  }
  // 调用后端用户信息保存接口
  const res = await userApi.save(userForm.value)
  const loginlist = {
    userAccount: userForm.value.userAccount,
    userPassword: userForm.value.userPassword
  }
  // 判断是否保存成功
  if (res.data.success) {
    ElNotification({
      title: 'Success',
      message: dialogTile.value + '成功',
      type: 'success'
    })
    // 添加用户身份
    // 1.通过名称获取刚新添的用户信息
    const selectUserId = await userApi.selectUserNameRturn(userForm.value)
    // 2.新增/修改用户角色
    const roleSelectId = ref()
    roleList.value.forEach(function (element) {
      if (element.roleName === rolei.value) {
        roleSelectId.value = element.roleId
      }
    })

    const thisUserRole = await userRoleApi.getUserIdByRoles(selectUserId.data.data.userId)
    if (thisUserRole.data.data === null) {
      jj.value.userId = selectUserId.data.data.userId
      jj.value.roleId = roleSelectId.value
    } else {
      jj.value.userRoleId = thisUserRole.data.data.userRoleId
      jj.value.userId = thisUserRole.data.data.userId
      jj.value.roleId = roleSelectId.value
    }
    // 用户角色添加修改保存
    // 用户角色添加修改保存成功seveUserRole.data.code === 1002
    await userRoleApi.save(jj.value)
    drawer.value = false
    fileList.value = []
    // 清空表单的数据
    formEl.resetFields()
    const tokenList:any = await jwtDecode(localStorage.getItem('Authorization'))
    if (tokenList.systemUser.userAccount === userIs.value.userAccount &&
    tokenList.systemUser.userName === userIs.value.userName) {
      const userRes = await userLoginApi.loginUser(loginlist)
      localStorage.setItem(
        'Authorization', userRes.data.data.Authorization
      )
    }
    // 重新加载用户列表数据
    loadUserData()
  } else {
    // 保存失败提示信息
    ElMessage({
      message: res.data.message,
      type: 'error'
    })
  }
}
// 搜索身份
const foucsRolei = async () => {
  const getUserByIdRes = await roleApi.selectRole()
  if (getUserByIdRes.data.code !== 1000) {
    roleList.value = null
    ElNotification({
      title: 'Error',
      message: '获取用户身份失败',
      type: 'error'
    })
  }
  roleList.value = getUserByIdRes.data.data
}
// ==========================================================

// 放大图片框
const srcList = []
const userName = ref()
const userAccount = ref()
// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(true)
// 默认加载没有出错
const error = ref(false)
// 定义分页查询请求参数
const queryParam = reactive<QueryParam>({
  // 当前页
  currentPage: 1,
  // 一页显示条数
  pageSize: 8,
  userName: '',
  userAccount: ''
})
// 定义接收Table表格数据
const userData = ref([])
// 分页查询数据
const loadUserData = async () => {
  // 调用请求后端API
  const pageUserList = {
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    userName: userName.value,
    userAccount: userAccount.value
  }
  const TestUseCasesRes = await userApi.pageUser(pageUserList)
  // 把接收到的数据赋值给data变量
  const data = TestUseCasesRes.data.data
  // 判断data和data.records是否有值
  if (data && data.records) {
    // 把查询出来的数据赋值给TestUseCasesData变量
    userData.value = data.records
    // 把查询出来的总条数赋值给total变量
    total.value = Number(data.total)
    loading.value = false
    // 给查看大图添加图片
    data.records.forEach(function (value) {
      srcList.push('micro_question_answer_admin_war' + value.userAvatar)
    })
  } else {
    error.value = true
  }
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadUserData()
})
</script>
