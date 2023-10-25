import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 分页查询表单 */
export interface pageTaskParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 任务名称
  taskName: string
}

export class tsTaskApi {
  // 下载测试框架
  static async dowTestFram (pageTaskParam: pageTaskParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: 'http://192.168.3.85:5000/socketServer/userGit',
      params: {
        currentPage: pageTaskParam.currentPage,
        pageSize: pageTaskParam.pageSize,
        taskName: pageTaskParam.taskName
      }
    })
  }

  /**
   * 任务数据修改or保存
   * @param TaskFrom 任务信息表单
   * @returns 状态码
   */
  static async taskSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsTask/taskSaveOrUp', params)
  }

  /**
   * 例任务删除
   * @param params 任务ID集合
   * @returns 状态码
   */
  static async removeTaskIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsTask/removeTaskIds', { params: params })
  }

  // 新建热任务，任务用例，项目任务
  static async taskSaveOrUpTaskCase (uptFrom: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsTask/taskSaveOrUpTaskCase', uptFrom)
  }

  static async selectTaskByTaskId (taskId: string): Promise<AxiosResponse> {
    return axios.get(`/ts/tsTask/selectTaskByTaskId/${taskId}`)
  }
}
