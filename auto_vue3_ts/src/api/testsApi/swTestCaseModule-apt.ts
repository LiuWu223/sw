import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParamModule {
  currentPage: number,
  pageSize: number,
  caseModuleName: string
}

export class swTestCaseModuleApi {
  /**
   * 新增/修改
   */
  static async save (caseModuleFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestCaseModule/saveOrUpdateCaseModule', caseModuleFrom)
  }

  /**
   * 删除
   */
  static async removeCaseModuleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestCaseModule/removeCaseModuleIds', {
      params: params
    })
  }

  /**
   * 分页查询
   */
  static async pageCaseModule (queryParam: QueryParamModule): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/swtest/swtestCaseModule/pageCaseModule',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        caseModuleName: queryParam.caseModuleName
      }
    })
  }

  /**
  * 根据主键ID查询
  */
  static async getCaseCaseModuleId (caseModuleId: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestCaseModule/getCaseModuleId/${caseModuleId}`)
  }

  /**
  * 查询所有
  */
  static async selectCaseModule (): Promise<AxiosResponse> {
    return axios.get('/swtest/swtestCaseModule/selectCaseModule')
  }

  /**
  * 导出测试任务
  */
  static async exportTaks (): Promise<AxiosResponse> {
    return axios.get('/swtest/swtestTask/exportTaks', {
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
  }

  /**
  * http执行cmd
  */
  static async httpCom (): Promise<AxiosResponse> {
    return axios.get('/swtest/swtestTask/httpCom')
  }
}
