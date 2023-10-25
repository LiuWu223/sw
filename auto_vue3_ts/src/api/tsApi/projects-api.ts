import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export class tsCaseApi {
  // 查询用户项目
  static async selectProjectByUserId (userId: string): Promise<AxiosResponse> {
    return axios.get(`/ts/tsUserProject/selectProjectByUserId/${userId}`)
  }

  // 查询项目任务
  static async selectTaskByProjectId (projectId: string): Promise<AxiosResponse> {
    return axios.get(`/ts/tsProjectTask/selectTaskByProjectId/${projectId}`)
  }

  // 新建项目
  static async tsProjectSaveOrUp (projectFrom: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsProject/tsProjectSaveOrUp', projectFrom)
  }
}
