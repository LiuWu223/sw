import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  caseModuleClassifyId: string
}

export class swTestCaseClassifyStepApi {
  /**
   * 新增/修改
   */
  static async save (caseClassifyStepFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestCaseClassifyStep/saveOrUpdateCaseClassifyStep', caseClassifyStepFrom)
  }

  /**
   * 删除
   */
  static async removeCaseClassifyStepIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestCaseClassifyStep/removeCaseClassifyStepIds', {
      params: params
    })
  }

  /**
   * 分页查询
   */
  static async pageCaseClassifyStep (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/swtest/swtestCaseClassifyStep/pageCaseClassifyStep',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        caseModuleClassifyId: queryParam.caseModuleClassifyId
      }
    })
  }

  /**
  * 根据主键ID查询
  */
  static async getCaseClassifyStepId (caseClassifyStepId: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestCaseClassifyStep/getCaseClassifyStepId/${caseClassifyStepId}`)
  }
}
