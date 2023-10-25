import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageCaseParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // Case名称
  caseName: string
}

export class tsCaseApi {
  /**
     * 分页查询测试用例信息
     * @param pageCaseParam 分页查询测试用例信息表单
     * @returns 分页测试用例信息
     */
  static async pageTsCase (pageCaseParam: pageCaseParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsCase/pageTsCase',
      params: {
        currentPage: pageCaseParam.currentPage,
        pageSize: pageCaseParam.pageSize,
        caseName: pageCaseParam.caseName
      }
    })
  }

  /**
   * 测试用例数据修改or保存
   * @param CaseFrom 测试用例信息表单
   * @returns 状态码
   */
  static async tsCaseSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsCase/tsCaseSaveOrUp', params)
  }

  /**
   * 测试用例删除
   * @param params 测试用例ID集合
   * @returns 状态码
   */
  static async removeTsCaseIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsCase/removeTsCaseIds', { params: params })
  }
}
