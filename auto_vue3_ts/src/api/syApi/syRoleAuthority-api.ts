import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageRoleAuthorityParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 权限名称
  authorityName: string,
  // 角色名称
  roleName: string
}

export class syRoleAuthorityApi {
  /**
     * 分页查询角色权限信息
     * @param pageRoleAuthorityParam 分页查询角色权限信息表单
     * @returns 分页角色权限信息
     */
  static async pageRoleAuthority (pageRoleAuthorityParam: pageRoleAuthorityParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageRoleAuthority',
      params: {
        currentPage: pageRoleAuthorityParam.currentPage,
        pageSize: pageRoleAuthorityParam.pageSize,
        authorityName: pageRoleAuthorityParam.authorityName,
        roleName: pageRoleAuthorityParam.roleName
      }
    })
  }

  /**
   * 角色权限数据修改or保存
   * @param RoleAuthorityFrom 角色权限信息表单
   * @returns 状态码
   */
  static async roleAuthoritySaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/roleAuthoritySaveOrUp', params)
  }

  /**
   * 角色权限删除
   * @param params 角色权限ID集合
   * @returns 状态码
   */
  static async removeRoleAuthorityIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeRoleAuthorityIds', { params: params })
  }
}
