import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageCaseStepParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 测试用例结果结果名称
  stepContent: string
}

export class tsCaseStepApi {
  /**
     * 分页查询测试用例结果结果信息
     * @param pageCaseStepParam 分页查询测试用例结果结果信息表单
     * @returns 分页测试用例结果结果信息
     */
  static async pageTsCaseStep (pageCaseStepParam: pageCaseStepParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsCaseStep/pageCaseStep',
      params: {
        currentPage: pageCaseStepParam.currentPage,
        pageSize: pageCaseStepParam.pageSize,
        stepContent: pageCaseStepParam.stepContent
      }
    })
  }

  /**
   * 测试用例结果结果数据修改or保存
   * @param CaseStepFrom 测试用例结果结果信息表单
   * @returns 状态码
   */
  static async caseStepSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsCaseStep/caseStepSaveOrUp', params)
  }

  /**
   * 测试用例结果结果删除
   * @param params 测试用例结果结果ID集合
   * @returns 状态码
   */
  static async removeCaseStepIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsCaseStep/removeCaseStepIds', { params: params })
  }
}
