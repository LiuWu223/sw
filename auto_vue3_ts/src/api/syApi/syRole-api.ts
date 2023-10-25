import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 分页查询表单 */
export interface pageRoleParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 角色名称
  roleName: string
}

export class syRoleApi {
  /**
     * 分页查询角色信息
     * @param pageRoleParam 分页查询角色信息表单
     * @returns 分页角色信息
     */
  static async pageRole (pageRoleParam: pageRoleParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syRole/pageRole',
      params: {
        currentPage: pageRoleParam.currentPage,
        pageSize: pageRoleParam.pageSize,
        roleName: pageRoleParam.roleName
      }
    })
  }

  /**
   * 角色数据修改or保存
   * @param RoleFrom 角色信息表单
   * @returns 状态码
   */
  static async roleSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syRole/roleSaveOrUp', params)
  }

  /**
   * 角色删除
   * @param params 角色ID集合
   * @returns 状态码
   */
  static async removeRoleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syRole/removeRoleIds', { params: params })
  }

  /**
   * 查询所有角色信息
   * @returns 所有角色
   */
  static async selectRole (): Promise<AxiosResponse> {
    return axios.get('/sy/syRole/selectRole')
  }
}
