import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  roleName: string,
  userName: string
}

export class userRoleApi {
  /**
   * @param currentPage
   * @param pageSize
   */
  static async pageUserRole (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/system/systemUserRole/pageUserRole',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        roleName: queryParam.roleName,
        userName: queryParam.userName
      }
    })
  }

  /**
   * 根据用户角色ID集合批量删除用户角色信息
   * @param params 用户角色ID集合
   */
  static async removeUserRoleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/system/systemUserRole/removeUserRoleIds', {
      params: params
    })
  }

  /**
   * 根据主键ID查询用户角色信息
   * @param userRoleId 用户角色ID
   */
  static async getUserRoleById (userRoleId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemUserRole/getUserRoleById/${userRoleId}`, {
    })
  }

  /**
   * 保存用户角色信息
   * @param schoolFrom 用户角色信息表单
   */
  static async save (userRoleFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemUserRole/saveOrUpdateUserRole', userRoleFrom, {
    })
  }

  /**
   * 根据主键ID查询角色信息
   * @param userId 角色ID
   */
  static async getUserIdByRoles (userId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemUserRole/getUserIdByRoles/${userId}`, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  /**
   * 根据主键ID查询角色信息
   * @param userId 角色ID
   */
  static async getUserId (userId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemUserRole/getUserIdByRole/${userId}`)
  }
}
