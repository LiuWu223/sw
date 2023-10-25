import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageUserRoleParam {
  currentPage: number,
  pageSize: number,
  userName: string,
  roleName: string
}

export class syUserRoleApi {
  /**
     * 分页查询用户角色信息
     * @param pageUserRoleParam 分页查询用户角色信息表单
     * @returns 分页用户角色信息
     */
  static async pageUserRole (pageUserRoleParam: pageUserRoleParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageUserRole',
      params: {
        currentPage: pageUserRoleParam.currentPage,
        pageSize: pageUserRoleParam.pageSize,
        userName: pageUserRoleParam.userName,
        userAccount: pageUserRoleParam.roleName
      }
    })
  }

  /**
   * 用户角色数据修改or保存
   * @param userRoleFrom 用户角色信息表单
   * @returns 状态码
   */
  static async userRoleSaveOrUp (userRoleFrom: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/userRoleSaveOrUp', userRoleFrom)
  }

  /**
   * 用户角色删除
   * @param params 用户角色ID集合
   * @returns 状态码
   */
  static async removeUserRoleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeUserRoleIds', { params: params })
  }
}
