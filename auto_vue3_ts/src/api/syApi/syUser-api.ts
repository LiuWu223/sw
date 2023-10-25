import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 登录表单 */
export interface loginParam {
  userAccount: string,
  userPassword: string
}

/** 分页查询表单 */
export interface pageUserParam {
  currentPage: number,
  pageSize: number,
  userName: string,
  userAccount: string
  roleName: string
}

export class syUserApi {
  /**
   * 用户登录
   * @param loginParam 登录
   * @returns token
   */
  static async login (loginParam: loginParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/login',
      data: loginParam
    })
  }

  /**
   * 分页查询用户信息
   * @param pageUserParam 分页查询用户信息表单
   * @returns 分页用户信息
   */
  static async pageUser (pageUserParam: pageUserParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/sy/syUser/pageUser',
      params: {
        currentPage: pageUserParam.currentPage,
        pageSize: pageUserParam.pageSize,
        userName: pageUserParam.userName,
        userAccount: pageUserParam.userAccount,
        roleName: pageUserParam.roleName
      }
    })
  }

  /**
   * 用户数据修改or保存
   * @param userFrom 用户信息表单
   * @returns 状态码
   */
  static async updateUser (userOrUserRoleFrom: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/updateUser', userOrUserRoleFrom)
  }

  /**
   * 用户删除
   * @param params 用户ID集合
   * @returns 状态码
   */
  static async removeUserIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeUserIds', { params: params })
  }

  /**
   * 根据ID查询用户信息
   * @param id 用户ID
   * @returns 用户信息
   */
  static async selectUserById (id: string): Promise<AxiosResponse> {
    return axios.get(`/sy/syUser/selectUserById/${id}`)
  }
}
