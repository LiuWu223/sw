import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  userName: string,
  userAccount: string
}

export class userApi {
  /**
   * @param currentPage
   * @param pageSize
   */
  static async pageUser (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/system/systemUser/pageUser',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        userName: queryParam.userName,
        userAccount: queryParam.userAccount
      }
    })
  }

  /**
   * 根据角色ID集合批量删除角色信息
   * @param params 角色ID集合
   */
  static async removeUserIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/system/systemUser/removeUserIds', {
      params: params
    })
  }

  /**
   * 根据主键ID查询角色信息
   * @param userId 角色ID
   */
  static async getUserById (userId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemUser/getUserById/${userId}`, {
    })
  }

  /**
   * 保存角色信息
   * @param schoolFrom 角色信息表单
   */
  static async save (userFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemUser/saveOrUpdateUser', userFrom, {
    })
  }

  /**
   * 根据用户账号查询用户信息
   * @param
   */
  static async selectUserNameRturn (userFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemUser/selectUserNameRturn', userFrom, {
    })
  }

  /**
   * 根据用户账号查询用户信息
   * @param schoolFrom 角色信息表单
   */
  static async selectUserName (userFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemUser/selectUserName', userFrom, {
    })
  }
}
