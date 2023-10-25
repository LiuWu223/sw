import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageTaskDetailsParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number
}

export class tsTaskDetailsApi {
  /**
     * 分页查询任务详情信息
     * @param pageTaskDetailsParam 分页查询任务详情信息表单
     * @returns 分页任务详情信息
     */
  static async pageTsTaskDetails (pageTaskDetailsParam: pageTaskDetailsParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsTaskDetails/pageTaskDetails',
      params: {
        currentPage: pageTaskDetailsParam.currentPage,
        pageSize: pageTaskDetailsParam.pageSize
      }
    })
  }

  /**
   * 任务详情数据修改or保存
   * @param TaskDetailsFrom 任务详情信息表单
   * @returns 状态码
   */
  static async taskDetailsSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsTaskDetails/taskDetailsSaveOrUp', params)
  }

  /**
   * 任务详情删除
   * @param params 任务详情ID集合
   * @returns 状态码
   */
  static async removeTaskDetailsIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsTaskDetails/removeTaskDetailsIds', { params: params })
  }
}
