<template>
  <el-card class="Cases" shadow="never">
    <template #header>
      <el-form :inline="true" :model="queryParam">
        <div class="mt-4" style="width: 960px; display: flex; padding: 20px; justify-content: space-between;">
          <el-form-item label="测试用例名查找" style="display: flex; justify-content: center; align-items: center;">
            <el-input
              v-model="caseModuleClassifyName"
              placeholder="测试用例模块名称"
              class="input-with-select"
              size="large"
              clearableforEach
            >
            </el-input>
          </el-form-item>
        </div>
        <el-form-item style="display: block;">
        <el-button @click="loadCaseModuleClassifyData">查询</el-button>
        <el-button type="success" @click="exportTaks()" :loading='exproIcon'>测试任务导出(暂时次按钮)</el-button>
        <el-button @click="httpCom()">执行cmd</el-button>
        <el-button type="primary" @click="openDialog()">新增</el-button>
        <el-button type="danger" @click="removeCaseModuleClassifyByIds(null)">批量删除</el-button>
        </el-form-item>
      </el-form>
    </template>
    <div v-loading="loading">
      <el-table :data="caseModuleClassifyData" border style="width: 100%" height="730px" @select="selectCaseModuleClassifyRow">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="caseModuleClassifyId" label="id" align="center" width="70" />
        <el-table-column prop="caseModuleId" label="模块id" align="center" width="70" />
        <el-table-column prop="caseModuleClassifyName" label="名称" align="center" />
        <el-table-column prop="caseModuleClassifyNunmber" label="序号" align="center" width="70" />
        <el-table-column prop="caseModuleClassifyItem" label="测试项" align="center" />
        <el-table-column prop="caseModuleClassifyTitle" label="标题" align="center" />
        <el-table-column prop="caseModuleClassifyGrade" label="等级" align="center" width="70" />
        <el-table-column prop="caseModuleClassifyPercondition" label="预置条件" align="center" />
        <el-table-column prop="caseClassIfyStep" label="操作步骤" align="center">
          <template #default="scope">
            <div v-for="(val,index) in scope.row.caseClassIfyStep" :key="index">
                <div>{{ val.caseClassifyStepNumber +'、'+val.caseClassifyStepContent+'\n'}}</div>
              </div>
          </template>
        </el-table-column>
        <el-table-column prop="caseClassIfyResult" label="预期结果" align="center">
          <template #default="scope">
            <div v-for="(val,index) in scope.row.caseClassIfyResult" :key="index">
                <div>{{ val.caseClassifyResultNumber +'、'+val.caseClassifyResultContent+'\n'}}</div>
              </div>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" width="110" />
        <el-table-column prop="createBy" label="创建人" align="center" />
        <el-table-column prop="updateTime" label="修改时间" align="center" width="110" />
        <el-table-column prop="updateBy" label="修改人" align="center" />
        <el-table-column prop="deleteFlag" width="100" label="删除状态" align="center" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="openDialog(scope.row.caseModuleClassifyId)">编辑</el-button>
            <el-button style="color: #ff4400;" link type="primary" size="small" @click="removeCaseModuleClassifyByIds(scope.row.caseModuleClassifyId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="queryParam.currentPage"
        :page-size="queryParam.pageSize"
        :page-sizes="[8, 50, 100, 500]"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <!-- 添加或修改抽屉 -->
    <el-drawer :size="900" v-model="drawer" :direction="direction" :show-close=false >
      <template #header>
        <h1>{{ dialogTile }}</h1>
        <span>ID:{{ caseMouleClassifyForm.caseModuleClassifyId }}</span>
      </template>
      <template #default>
        <el-form :model="caseMouleClassifyForm" ref="caseMouleClassifyFormRef">
          <div id="drawer_form" class="drawer_from_class">
            <el-form-item label="模块" prop="caseModuleId">
            <el-select
              v-model="caseModuleIdRef"
              placeholder="测试用例模块选择"
              @focus="foucsCaseModuleId"
              size="large"
              filterable
              style="width: 210px;"
              >
                <el-option
                  v-for="item in caseModuleList"
                  :key="item.caseModuleId"
                  :label="item.caseModuleName"
                  :value="item.caseModuleName"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="名称" prop="caseModuleClassifyName">
              <el-input
                v-model="caseMouleClassifyForm.caseModuleClassifyName"
                autosize
                type="textarea"
                maxlength="200"
                clearable
                resize="none"
                size="large"
                style="width: 300px;"/>
            </el-form-item>
            <el-form-item label="序号" prop="caseModuleClassifyNunmber">
              <el-input size="large" v-model="caseMouleClassifyForm.caseModuleClassifyNunmber" :show-word-limit="true" style="width: 300px;"/>
            </el-form-item>
            <el-form-item label="测试项" prop="caseModuleClassifyItem">
              <el-input
                autosize
                type="textarea"
                maxlength="200"
                clearable
                resize="none"
                size="large"
                v-model="caseMouleClassifyForm.caseModuleClassifyItem"
                style="width: 300px;"/>
            </el-form-item>
            <el-form-item label="标题" prop="caseModuleClassifyTitle">
              <el-input
              autosize
              type="textarea"
              maxlength="200"
              clearable
              resize="none"
              size="large"
              v-model="caseMouleClassifyForm.caseModuleClassifyTitle"
              style="width: 300px;"/>
            </el-form-item>
            <el-form-item label="重要等级" prop="caseModuleClassifyGrade">
            <el-select
              v-model="caseModuleClassifyGradeR"
              placeholder="重要等级"
              size="large"
              filterable
              style="width: 130px;"
              >
                <el-option
                  v-for="item in caseModuleClassifyGradeList"
                  :key="item.caseModuleClassifyGradeId"
                  :label="item.caseModuleClassifyGradeName"
                  :value="item.caseModuleClassifyGradeName"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="预置条件" prop="caseModuleClassifyPercondition">
              <el-input
              autosize
              type="textarea"
              maxlength="200"
              clearable
              resize="none"
              size="large"
              v-model="caseMouleClassifyForm.caseModuleClassifyPercondition"
              style="width: 270px;"/>
            </el-form-item>
          </div>
          <el-divider>测试步骤与预期结果</el-divider>
          <el-form-item
            v-for="(stepAndResul, index) in AddStepAndResultFrom.addStepAndResul"
            :key="stepAndResul.caseClassifyStepNumber"
            :label="'步骤' + (index + 1)"
            :prop="'domains.' + index + '.value'"
          >
            <div class="drawer__resul_and_step">
              <div class="drawer_class_step">
                <el-input
                placeholder="步骤内容"
                autosize
                type="textarea"
                maxlength="200"
                clearable
                resize="none"
                :prefix-icon="DocumentAdd"
                v-model="stepAndResul.caseClassifyStepContent"
                style="width: 350px;" />
              </div>
              <div class="drawer_class_resul">
                <el-input
                placeholder="预期结果"
                autosize
                type="textarea"
                maxlength="200"
                clearable
                resize="none"
                :prefix-icon="FolderChecked"
                v-model="stepAndResul.caseClassifyResultContent"
                style="width: 350px;" />
                <el-icon style="margin-left: 5px;color: #949494;font-size: 22px;margin-bottom: 70px;" @click.prevent="removeDomain(stepAndResul)"><CircleClose /></el-icon>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button @click="addDomain">添加步骤</el-button>
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="ecancel(caseMouleClassifyFormRef)">取消</el-button>
          <el-button type="primary" @click="saveCaseMouleClassify(caseMouleClassifyFormRef)">保存</el-button>
        </div>
      </template>
    </el-drawer>
  </el-card>
</template>

<script lang="ts" setup>
// 引入vue组合式API及钩子函数
import { onMounted, reactive, ref, watch } from 'vue'
// 引入ElementUI的消息提示组件
import { ElMessage, ElMessageBox, FormInstance, ElNotification } from 'element-plus'
// 引入ElementUI的Message的样式
import 'element-plus/es/components/message/style/index'
// 引入ElementUI的MessageBox相关样式
import 'element-plus/es/components/message-box/style/index'
import { FolderChecked, DocumentAdd } from '@element-plus/icons-vue'
// 引入API文件
import { swTestCaseClassifyApi, QueryParam } from '@/api/testsApi/swTestCaseModuleClassify-apt'
import { swTestCaseModuleApi } from '@/api/testsApi/swTestCaseModule-apt'
import { swTestCaseClassifyStepApi } from '@/api/testsApi/swTestCaseClassifyStep-apt'
import { swTestCaseClassifyResultApi } from '@/api/testsApi/swTestCaseClassifyResult-apt'

// =======================================================抽屉==
// 选中模块ID
const caseModuleIdRef = ref()
// 模块ID选择下拉框
const caseModuleList = ref([])

// 查询模块信息
const foucsCaseModuleId = async () => {
  const getCaseModule = await swTestCaseModuleApi.selectCaseModule()
  if (getCaseModule.data.code !== 1000) {
    caseModuleIdRef.value = null
    ElNotification({
      title: 'Error',
      message: '获取用例模块失败',
      type: 'error'
    })
  }
  caseModuleList.value = getCaseModule.data.data
}
// 等级选中模块ID
const caseModuleClassifyGradeR = ref()
// 模块ID选择下拉框
const caseModuleClassifyGradeList = ref([
  {
    caseModuleClassifyGradeId: '1',
    caseModuleClassifyGradeName: 'P0'
  },
  {
    caseModuleClassifyGrade: '2',
    caseModuleClassifyGradeName: 'P1'
  },
  {
    caseModuleClassifyGrade: '3',
    caseModuleClassifyGradeName: 'P2'
  },
  {
    caseModuleClassifyGrade: '4',
    caseModuleClassifyGradeName: 'P4'
  },
  {
    caseModuleClassifyGrade: '5',
    caseModuleClassifyGradeName: 'P5'
  },
  {
    caseModuleClassifyGrade: '6',
    caseModuleClassifyGradeName: 'P6'
  }
])
interface addStepAndResul {
  caseClassifyResultId: number,
  caseClassifyStepId: number,
  caseClassifyStepNumber: number
  caseClassifyStepContent: string
  caseModuleClassifyId: string
  caseClassifyResultContent: string
}
const AddStepAndResultFrom = reactive<{
  addStepAndResul: addStepAndResul[]
}>({
  addStepAndResul: [
    {
      caseClassifyResultId: null,
      caseClassifyStepId: null,
      caseClassifyStepNumber: null,
      caseClassifyStepContent: '',
      caseModuleClassifyId: '',
      caseClassifyResultContent: ''
    }
  ]
})
// 删除操作步骤
const removeDomain = (item: addStepAndResul) => {
  const index = AddStepAndResultFrom.addStepAndResul.indexOf(item)
  if (index !== -1) {
    AddStepAndResultFrom.addStepAndResul.splice(index, 1)
  }
}
// 添加操作步骤
const addDomain = () => {
  AddStepAndResultFrom.addStepAndResul.push({
    caseClassifyResultId: null,
    caseClassifyStepId: null,
    caseClassifyStepNumber: null,
    caseClassifyStepContent: '',
    caseModuleClassifyId: '',
    caseClassifyResultContent: ''
  })
}
watch(
  () => AddStepAndResultFrom.addStepAndResul.length,
  async () => {
    for (var y = 0; y < AddStepAndResultFrom.addStepAndResul.length; y++) {
      AddStepAndResultFrom.addStepAndResul[y].caseClassifyStepNumber = y + 1
    }
  }
)
// 显示抽屉
const drawer = ref(false)
// 显示抽屉方向
const direction = ref('rtl')
// 标题
const dialogTile = ref('')
// 输入框表单
const caseMouleClassifyForm = ref({
  caseModuleId: '',
  caseModuleClassifyId: '',
  caseModuleClassifyName: '',
  caseModuleClassifyNunmber: '',
  caseModuleClassifyItem: '',
  caseModuleClassifyTitle: '',
  caseModuleClassifyGrade: '',
  caseModuleClassifyPercondition: ''
})
// 不确定变量
const ecancel = (userFormRef) => {
  drawer.value = false
  // 清空步骤数组
  AddStepAndResultFrom.addStepAndResul = []
  // 清空校验信息
  userFormRef.resetFields()
}
watch(
  () => drawer.value,
  async (newValue) => {
    if (newValue === false) {
      // 清空步骤数组
      AddStepAndResultFrom.addStepAndResul = []
    }
  }
)
// Form 表单的Ref变量
const caseMouleClassifyFormRef = ref<FormInstance>()
const open1 = (nameVule) => {
  ElNotification({
    title: 'Error',
    message: nameVule + '必要存在的',
    type: 'error'
  })
}
const r = ref({
  caseClassifyResultId: null,
  caseClassifyResultNumber: null,
  caseClassifyResultContent: '',
  caseModuleClassifyId: null
})
const s = ref({
  caseClassifyStepId: null,
  caseClassifyStepNumber: null,
  caseClassifyStepContent: '',
  caseModuleClassifyId: null
})
// 保存并上传信息
const saveCaseMouleClassify = async (formEl: FormInstance | undefined) => {
  if (caseModuleIdRef.value === '') {
    open1('模块名称')
    return
  }
  caseModuleList.value.forEach(values => {
    if (values.caseModuleName === caseModuleIdRef.value) {
      caseMouleClassifyForm.value.caseModuleId = values.caseModuleId
    }
  })
  if (caseMouleClassifyForm.value.caseModuleClassifyNunmber === '') {
    open1('序号')
    return
  } if (caseMouleClassifyForm.value.caseModuleClassifyItem === '') {
    open1('测试项')
    return
  } if (caseMouleClassifyForm.value.caseModuleClassifyTitle === '') {
    open1('标题')
    return
  } if (caseModuleClassifyGradeR.value === '') {
    open1('等级')
    return
  }
  caseModuleClassifyGradeList.value.forEach(values => {
    if (values.caseModuleClassifyGradeName === caseModuleClassifyGradeR.value) {
      caseMouleClassifyForm.value.caseModuleClassifyGrade = values.caseModuleClassifyGradeName
    }
  })
  if (caseMouleClassifyForm.value.caseModuleClassifyPercondition === '') {
    open1('预置条件')
    return
  }
  const saveClassify = await swTestCaseClassifyApi.save(caseMouleClassifyForm.value)
  const resultFrom = []
  const stepFrom = []
  AddStepAndResultFrom.addStepAndResul.forEach(valus => {
    r.value = {
      caseClassifyResultId: valus.caseClassifyResultId,
      caseClassifyResultNumber: valus.caseClassifyStepNumber,
      caseClassifyResultContent: valus.caseClassifyResultContent,
      caseModuleClassifyId: saveClassify.data.data
    }
    s.value = {
      caseClassifyStepId: valus.caseClassifyStepId,
      caseClassifyStepNumber: valus.caseClassifyStepNumber,
      caseClassifyStepContent: valus.caseClassifyStepContent,
      caseModuleClassifyId: saveClassify.data.data
    }
    resultFrom.push(r.value)
    stepFrom.push(s.value)
  })
  for (var sr = 0; sr < resultFrom.length; sr++) {
    swTestCaseClassifyResultApi.save(resultFrom[sr])
    swTestCaseClassifyStepApi.save(stepFrom[sr])
  }
  // 判断是否保存成功
  if (saveClassify.data.success) {
    ElNotification({
      title: 'Success',
      message: dialogTile.value + '成功',
      type: 'success'
    })
    drawer.value = false
    // 清空表单的数据
    formEl.resetFields()
    // 重新加载列表数据
    loadCaseModuleClassifyData()
  } else {
    // 保存失败提示信息
    ElMessage({
      message: saveClassify.data.message,
      type: 'error'
    })
  }
}
// 修改用户信息
const openDialog = async (caseModuleId?: string) => {
  drawer.value = true
  if (caseModuleId) {
    dialogTile.value = '修改测试用例模块信息'
    // 查询出本条用例的信息
    const getCaseModuleClassifyIdRes = await swTestCaseClassifyApi.getCaseModuleClassifyId(caseModuleId)
    // 通过本条用例的模块id查询所属模块
    const getCaseModule = await swTestCaseModuleApi.getCaseCaseModuleId(getCaseModuleClassifyIdRes.data.data.caseModuleId)
    const data = getCaseModuleClassifyIdRes.data.data
    // 通过模块id查询模块信息，把模型nama赋值给input
    caseModuleIdRef.value = getCaseModule.data.data.caseModuleName
    // 等级
    caseModuleClassifyGradeR.value = data.caseModuleClassifyGrade
    // 用例基本信息
    caseMouleClassifyForm.value = data
    // 选择修改时删除第一个空步骤
    if (AddStepAndResultFrom.addStepAndResul !== null) {
      AddStepAndResultFrom.addStepAndResul.splice(0, 1)
    }
    for (var i = 0; i < data.caseClassIfyResult.length; i++) {
      AddStepAndResultFrom.addStepAndResul.push({
        caseClassifyResultId: data.caseClassIfyResult[i].caseClassifyResultId,
        caseClassifyResultContent: data.caseClassIfyResult[i].caseClassifyResultContent,
        caseClassifyStepId: data.caseClassIfyStep[i].caseClassifyStepId,
        caseClassifyStepContent: data.caseClassIfyStep[i].caseClassifyStepContent,
        caseClassifyStepNumber: data.caseClassIfyResult[i].caseClassifyResultNumber,
        caseModuleClassifyId: caseMouleClassifyForm.value.caseModuleClassifyId
      })
    }
  } else {
    // 为空把弹窗标题改为：用户新增
    caseMouleClassifyForm.value = {
      caseModuleId: '',
      caseModuleClassifyId: '',
      caseModuleClassifyName: '',
      caseModuleClassifyNunmber: '',
      caseModuleClassifyItem: '',
      caseModuleClassifyTitle: '',
      caseModuleClassifyGrade: '',
      caseModuleClassifyPercondition: ''
    }
    r.value = {
      caseClassifyResultId: null,
      caseClassifyResultNumber: null,
      caseClassifyResultContent: '',
      caseModuleClassifyId: null
    }
    s.value = {
      caseClassifyStepId: null,
      caseClassifyStepNumber: null,
      caseClassifyStepContent: '',
      caseModuleClassifyId: null
    }
    // 清空步骤数组
    AddStepAndResultFrom.addStepAndResul = []
    // 新建清空 测试用例输入框
    caseModuleClassifyGradeR.value = ''
    caseModuleIdRef.value = ''
    dialogTile.value = '添加测试模块用例信息'
  }
}
const exproIcon = ref(false)
// ==========================================================
const httpCom = () => {
  swTestCaseModuleApi.httpCom().then(res => {
    console.log(res)
    // const execFile = require('child_process').execFile
    // const child = execFile('node', [ '--version'], ( error, stdout, stderr) => {
    // if (error) {
    //   console.error( 'stderr', stderr)
    // throw error
    // }
    //   console.log( 'stdout', stdout)
    // })
    // if (res.request.status === 200) {
    //   // 创建一个a标签
    //   const link = document.createElement('a')
    //   // 实例化文件
    //   const blob = new Blob([res.data], {
    //     type: 'text/plain;charset=utf-8'
    //   })
    //   // 创建一个下载文件连接
    //   link.href = URL.createObjectURL(blob)
    //   // 设置下载文件名称
    //   const fileName = '测试任务.bat'
    //   link.download = fileName
    //   // 模拟点击a标签
    //   link.click()
    // }
  })
}
const exportTaks = () => {
  exproIcon.value = true
  swTestCaseModuleApi.exportTaks().then(res => {
    // 判断是否请求成功
    if (res.request.status === 200) {
      // 创建一个a标签
      const link = document.createElement('a')
      // 实例化文件
      const blob = new Blob([res.data], {
        type: 'application/vnd.ms-excel;charset=utf-8'
      })
      // 创建一个下载文件连接
      link.href = URL.createObjectURL(blob)
      // 设置下载文件名称
      const fileName = '测试任务.xls'
      link.download = fileName
      // 模拟点击a标签
      link.click()
      ElNotification({
        title: 'Success',
        message: '导出成功, 请手动下载到指定目录！',
        type: 'success'
      })
      exproIcon.value = false
    } else {
      ElNotification({
        title: 'Error',
        message: '导出失败！！',
        type: 'error'
      })
      exproIcon.value = false
    }
  })
}
const caseModuleClassifyName = ref()
// 定义分页查询总条数变量
const total = ref(0)
// 定义加载中进度条变量
const loading = ref(true)
// 默认加载没有出错
const error = ref(false)
interface CaseClassifys {
  caseModuleId: string
}
// 定义分页查询请求参数
const queryParam = reactive<QueryParam>({
  // 当前页
  currentPage: 1,
  // 一页显示条数
  pageSize: 8,
  caseModuleClassifyName: ''
})
// 定义接收Table表格数据
const caseModuleClassifyData:any = ref([])
// （用于删除数据）
let caseModuleIds: string[] = []
/**
 * 点击查询按钮或重新加载数据方法
 */
const queryUserData = () => {
  // 令分页查询参数的当前页初始化为1
  queryParam.currentPage = 1
  // 令分页查询参数的一页显示条数初始化为1
  queryParam.pageSize = 8
  // 执行查询方法
  loadCaseModuleClassifyData()
}
/**
 * 点击选择框触发事件
 * @param selection 选择的集合
 */
const selectCaseModuleClassifyRow = (selection: CaseClassifys[]) => {
  // 初始化需要上传的用户Id集合
  caseModuleIds = []
  selection.forEach(caseModuleClassifyRow => {
    caseModuleIds.push(caseModuleClassifyRow.caseModuleId)
  })
}
/**
 * 根据主键ID删除信息
 */
const removeCaseModuleClassifyByIds = (caseModuleId: string | null) => {
  // 提示用户是否删除
  ElMessageBox.confirm('是否删除点击或选择的用户信息数据?', '提示消息', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  }).then(async () => {
    // 判断是否为单条数据
    if (caseModuleId) {
      // 如果为单条数据直接装载进去
      caseModuleIds = [caseModuleId]
    } else {
      if (caseModuleIds.length === 0) {
        // 提示请选择需要删除的数据
        ElMessage({
          type: 'warning',
          message: '请选择需要删除的数据'
        })
        return
      }
    }
    // 请求后端API删除数据
    const removeRes = await swTestCaseClassifyApi.removeCaseModuleClassifyIds({ caseModuleIds: caseModuleIds.join(',') })
    // 判断是否删除成功
    if (removeRes.data.success) {
      // 清空删除集合
      caseModuleIds = []
      // 重新加载数据
      queryUserData()
      // 提示删除成功
      await ElMessageBox.alert(removeRes.data.message, '提示信息', {
        type: 'success',
        confirmButtonText: '确定'
      })
    } else {
      // 提示删除失败
      await ElMessageBox.alert(removeRes.data.message, '提示信息', {
        type: 'error',
        confirmButtonText: '确定'
      })
    }
  })
}
// 分页查询数据
const loadCaseModuleClassifyData = async () => {
  // 调用请求后端API
  const pageCaseModuleClassifyList = {
    currentPage: queryParam.currentPage,
    pageSize: queryParam.pageSize,
    caseModuleClassifyName: caseModuleClassifyName.value
  }
  const CaseModuleClassifyRes = await swTestCaseClassifyApi.pageCaseModuleClassify(pageCaseModuleClassifyList)
  // 把接收到的数据赋值给data变量
  const data = CaseModuleClassifyRes.data.data
  // 判断data和data.records是否有值
  if (data && data.records) {
    caseModuleClassifyData.value = data.records
    // 把查询出来的总条数赋值给total变量
    total.value = Number(data.total)
    loading.value = false
  } else {
    error.value = true
  }
}
/**
 * 一页显示条数改变事件
 * @param pageSize 新的一页显示条数
 */
const handleSizeChange = (pageSize: number) => {
  queryParam.pageSize = pageSize
  loadCaseModuleClassifyData()
}
/**
 * 当前页改变事件
 * @param currentPage 新的当前页
 */
const handleCurrentChange = (currentPage: number) => {
  queryParam.currentPage = currentPage
  loadCaseModuleClassifyData()
}

/**
 * 组件加载完成时执行的钩子函数
 */
onMounted(() => {
  loadCaseModuleClassifyData()
})
</script>

<style lang="scss" scoped>
.el-scrollbar__wrap { overflow-x: hidden; }
.drawer_from_class{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.drawer__resul_and_step{
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 95%;
  padding: 20px;
  border: 1px solid #ededed;
  border-radius: 15px;
}
.drawer_class_resul{
  display: flex;
  height: 70px;
  align-items: center;
  justify-content: center;
}
.drawer_class_step{
  display: flex;
  height: 70px;
  align-items: center;
  justify-content: center;
}
.Cases {
  .el-pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

<style lang="scss">
.Cases {
  .el-card__header {
    padding-bottom: 0 !important;
  }
}
.Cases-import-excel {
  .el-dialog__body {
    border-top: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
.input-with-select{
  width: 230px;
}
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
.el-input__wrapper{
  height: 35px;
  width: 200px;
}
</style>
