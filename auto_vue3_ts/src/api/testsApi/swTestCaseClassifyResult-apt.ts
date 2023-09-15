import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  caseModuleClassifyId: string
}

export class swTestCaseClassifyResultApi {
  /**
   * 新增/修改
   */
  static async save (rolePermissionsFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestCaseClassifyResult/saveOrUpdateCaseClassifyResult', rolePermissionsFrom, {
    })
  }

  /**
   * 删除
   */
  static async removeCaseClassifyResultIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestCaseClassifyResult/removeCaseClassifyResultIds', {
      params: params
    })
  }

  /**
   * 分页查询
   */
  static async pageCaseClassifyResult (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/swtest/swtestCaseClassifyResult/pageCaseClassifyResult',
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
  static async getRoleByPermissions (caseClassifyResultId: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestCaseClassifyResult/getCaseClassifyResultId/${caseClassifyResultId}`)
  }
}
