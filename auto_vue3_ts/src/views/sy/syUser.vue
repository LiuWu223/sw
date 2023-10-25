<template>
  <el-card class="sysUserCases" shadow="never">
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
          <el-form-item label="身份选择" prop="userRole" style="display: flex; justify-content: center; align-items: center;">
            <el-select
            v-model="pageUserParamC.roleName"
            placeholder="选择身份"
            @focus="selectRole"
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
        </div>
        <el-form-item style="display: block;">
        <el-button @click="loadUserData">查询</el-button>
        <el-button type="primary" @click="upDateByUser()">新增</el-button>
        <el-button type="success">导出当页用户</el-button>
        <el-button type="warning">导入Excel用户</el-button>
        <el-button type="danger" @click="removeUserByIds(null)">批量删除</el-button>
        </el-form-item>
      </el-form>
    </template>
    <div v-loading="loading">
      <el-table :data="userData" border style="width: 100%; height:600px;" @select="selectUserRow">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="id" label="用户编号" align="center" />
        <el-table-column prop="userName" label="用户名称" align="center" />
        <el-table-column prop="roleName" label="用户身份" align="center" />
        <el-table-column prop="userAccount" label="用户账号" align="center" />
        <el-table-column prop="userPassword" label="用户密码" align="center" />
        <el-table-column prop="dictContent" label="性别" align="center" />
        <el-table-column prop="userPhoneNumber" label="电话号码" align="center" />
        <el-table-column prop="userAvatar" label="用户头像" align="center" >
          <template v-slot="scope">
            <el-image
              style="width: 60px;
              height: 60px;"
              :initial-index="indexBog"
              @click="indexRow(scope.row)"
              :preview-src-list="srcList"
              @lick="dialogVisibleImage = true"
              :src='"auto_sw_admin_war" + scope.row.userAvatar'
              preview-teleported />
            <!-- 放大 -->
            <el-dialog v-model="dialogVisibleImage">
              <el-image style="width:100%;" w-full :src='"auto_sw_admin_war" + scope.row.userAvatar' alt="Preview Image" />
            </el-dialog>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column prop="deleteFlag" width="100" label="删除状态" align="center" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="upDateByUser(scope.row.id)">编辑</el-button>
            <el-button style="color: #ff4400;" link type="primary" size="small" @click="removeUserByIds(scope.row.id)">删除</el-button>
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

          <el-form-item label="用户名称" prop="userName">
            <el-input :prefix-icon="User" size="large" v-model="userForm.userName" :maxlength="20" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="用户账号" prop="userAccount">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.userAccount" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="用户密码" prop="userPassword">
            <el-input :prefix-icon="Key" size="large" v-model="userForm.userPassword" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="电话" prop="userAccount">
            <el-input :prefix-icon="Phone" size="large" v-model="userForm.userPhoneNumber" :maxlength="11" :show-word-limit="true" />
          </el-form-item>

          <el-form-item label="性别" prop="dictContent">
            <el-select
            v-model="userForm.dictContent"
            placeholder="性别"
            @focus="selectSex"
            size="large"
            >
              <el-option
                v-for="item in sexList"
                :key="item.id"
                :label="item.dictContent"
                :value="item.dictContent"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="身份" prop="userRole">
            <el-select
            v-model="userForm.userRole"
            placeholder="选择身份"
            @focus="selectRole"
            size="large"
            >
              <el-option
                v-for="item in roleList"
                :key="item.id"
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
          <el-button @click="drawer = false">取消</el-button>
          <el-button type="primary" @click="saveUser()">保存</el-button>
        </div>
      </template>
    </el-drawer>
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref, watch } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, ElMessageBox, UploadProps, UploadUserFile, UploadInstance, UploadRawFile, genFileId, ElNotification } from 'element-plus'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import { Plus, Search, User, Phone, Key } from '@element-plus/icons-vue'
import axios from '@/utils/axios'
import { syDictApi } from '@/api/syApi/syDict-api'
import { syUserApi, pageUserParam } from '@/api/syApi/syUser-api'
import { syRoleApi } from '@/api/syApi/syRole-api'

const roleChange = (re) => {
  console.log(re)
  console.log(1)
}
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
  userId: '',
  userName: '',
  userAccount: '',
  userPassword: '',
  dictContent: '',
  userAvatar: '',
  userRole: '',
  userPhoneNumber: '',
  syUserRole: ''
})
// 文件上传列表
const fileList = ref<UploadUserFile[]>([])
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
// 性别选择下拉框
const sexList = ref([])
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
  console.log(fileListData)
  formData.append('file', fileListData.values[0].raw)
  await axios.post('/common/upload/single', formData).then(data => {
    console.log(data)
    if (!data.data.success) {
      ElMessage.error('头像上传失败')
    } else {
      ElMessage.success('头像上传成功')
      userForm.value.userAvatar = data.data.data
    }
  }).catch(err => {
    console.log(err)
    console.log('头像上传失败')
  })
}
// 当新增修改窗口消失时，清空输入框类容
watch(
  () => drawer.value,
  async (newValue) => {
    if (newValue === false) {
      userForm.value = {
        userId: '',
        userName: '',
        userAccount: '',
        userPassword: '',
        userAvatar: '',
        userRole: '',
        dictContent: '',
        userPhoneNumber: '',
        syUserRole: ''
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
const saveUser = async () => {
  if (userForm.value.userName === '') { return assertSce('昵称') }
  if (userForm.value.userAccount === '') { return assertSce('账号') }
  if (userForm.value.userPassword === '') { return assertSce('密码') }
  if (userForm.value.userRole === '') { return assertSce('身份') }
  // 上传头像
  // fileListRef.value !== -1 且文件列表不能为空
  if (isImageOnErrorNot.value && isImageOnRedy.value && fileListRef.value !== -1) {
    await submitUpload()
  }
  isImageOnErrorNot.value = true
  isImageOnRedy.value = false

  const userOrUserRoleFrom = {
    syUserRole: {
      id: userForm.value.syUserRole,
      roleName: userForm.value.userRole,
      userId: userForm.value.userId
    },
    syUser: {
      id: userForm.value.userId,
      dictId: userForm.value.dictContent,
      userAccount: userForm.value.userAccount,
      userAvatar: userForm.value.userAvatar,
      userName: userForm.value.userName,
      userPassword: userForm.value.userPassword,
      userPhoneNumber: userForm.value.userPhoneNumber
    }
  }

  await syUserApi.updateUser(userOrUserRoleFrom).then(data => {
    if (data.data.success) {
      ElNotification({
        title: 'Success',
        message: dialogTile.value + '成功',
        type: 'success'
      })
      drawer.value = false
      loadUserData()
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
// 身份下拉框数据
const selectRole = async () => {
  await syRoleApi.selectRole().then(data => {
    roleList.value = data.data.data
  }).catch(err => {
    console.log('身份下拉框数据获取失败')
    console.log(err)
  })
}
// 性别下拉框数据
const selectSex = async () => {
  const dictType = '性别'
  await syDictApi.selectDictType(dictType).then(data => {
    sexList.value = data.data.data
  }).catch(err => {
    console.log('性别下拉框数据获取失败')
    console.log(err)
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
        userId: upUser.id,
        userName: upUser.userName,
        userAccount: upUser.userAccount,
        userPassword: upUser.userPassword,
        userAvatar: upUser.userAvatar,
        userRole: upUser.roleName,
        dictContent: upUser.dictContent,
        userPhoneNumber: upUser.userPhoneNumber,
        syUserRole: upUser.syUserRole
      }
    }).catch(err => {
      console.log(err)
      console.log('用户信息获取失败')
    })
  } else {
    // 为空把弹窗标题改为：用户新增
    dialogTile.value = '添加新用户'
    fileList.value = []
  }
}
/**
 * 数据展现与查询信息
 */

const imgeShow = ref(false)
// 放大图片框
const srcList = []
const dialogVisibleImage = ref(false)
// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(true)
// 默认加载没有出错
const error = ref(false)
interface Users {
  id: string
}
// 定义差点大图的开始index
const indexBog = ref()
const indexRow = (indexRaw) => {
  console.log(indexRaw)
  srcList.forEach(function (value, key) {
    if (value === 'auto_sw_admin_war' + indexRaw.userAvatar) {
      indexBog.value = key
      return key
    }
  })
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
  loadUserData()
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

const removeUserByIds = (userId: string | null) => {
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
const loadUserData = async () => {
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
    // 给查看大图添加图片
    userList.data.data.records.forEach(function (value) {
      srcList.push('auto_sw_admin_war' + value.userAvatar)
    })
  }).catch(err => {
    console.log('获取用户列表失败')
    console.log(err)
  })
}

// 一页的大小
const handleSizeChange = (pageSize: number) => {
  pageUserParamC.pageSize = pageSize
  loadUserData()
}
// 去到第几页
const handleCurrentChange = (currentPage: number) => {
  pageUserParamC.currentPage = currentPage
  loadUserData()
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  // 加载表格数据
  loadUserData()
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
.el-input__wrapper{
  height: 35px;
  width: 200px;
}
.el-input{
  width: 53%;
}
</style>
