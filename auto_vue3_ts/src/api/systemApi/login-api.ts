import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  userAccount: string,
  userPassword: string
}

/**
 * @description 用户API请求类
 */
export class userLoginApi {
  /**
   * 登录
   * @param userAccount 登录账号
   * @param userPassword 登录密码
   */
  static async loginUser (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/system/systemUser/login',
      data: queryParam
    })
  }

  static async adbDevices (): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: 'http://127.0.0.1:5000' + '/adbTool/adbDevices'
    })
  }

  static async gitClone (pahts): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: 'http://127.0.0.1:5000' + '/gitTool/gitClone',
      data: pahts
    })
  }

  static async usreCmd (userFrom: object): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/swtest/swtestTask/userCmd',
      data: userFrom
    })
  }
}
