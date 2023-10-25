import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageCaseResultParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 测试用例预期结果预期结果名称
  resultContent: string
}

export class tsCaseResultApi {
  /**
     * 分页查询测试用例预期结果信息
     * @param pageCaseResultParam 分页查询测试用例预期结果信息表单
     * @returns 分页测试用例预期结果信息
     */
  static async pageTsCaseResult (pageCaseResultParam: pageCaseResultParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsCaseResult/pageCaseResult',
      params: {
        currentPage: pageCaseResultParam.currentPage,
        pageSize: pageCaseResultParam.pageSize,
        resultContent: pageCaseResultParam.resultContent
      }
    })
  }

  /**
   * 测试用例预期结果数据修改or保存
   * @param CaseResultFrom 测试用例预期结果信息表单
   * @returns 状态码
   */
  static async caseResultSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsCaseResult/caseResultSaveOrUp', params)
  }

  /**
   * 测试用例预期结果删除
   * @param params 测试用例预期结果ID集合
   * @returns 状态码
   */
  static async removeCaseResultIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsCaseResult/removeCaseResultIds', { params: params })
  }
}
