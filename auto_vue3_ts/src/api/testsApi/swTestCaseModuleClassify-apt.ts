import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  caseModuleClassifyName: string
}

export class swTestCaseClassifyApi {
  /**
   * 新增/修改
   */
  static async save (caseModuleClassifyFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestCaseModuleClassify/saveOrUpdateCaseModuleClassify', caseModuleClassifyFrom)
  }

  /**
   * 删除
   */
  static async removeCaseModuleClassifyIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestCaseModuleClassify/removeCaseModuleClassifyIds', {
      params: params
    })
  }

  /**
   * 分页查询
   */
  static async pageCaseModuleClassify (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/swtest/swtestCaseModuleClassify/pageCaseModuleClassify',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        caseModuleClassifyName: queryParam.caseModuleClassifyName
      }
    })
  }

  /**
  * 根据主键ID查询
  */
  static async getCaseModuleClassifyId (caseClassifyId: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestCaseModuleClassify/getCaseModuleClassifyId/${caseClassifyId}`)
  }
}
