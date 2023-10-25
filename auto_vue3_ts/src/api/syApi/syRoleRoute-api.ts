import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageRoleRouteParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 路由名称
  routeLabel: string,
  // 角色名称
  roleName: string
}

export class syRoleRouteApi {
  /**
     * 分页查询路由信息
     * @param pageRoleRouteParam 分页查询角色路由信息表单
     * @returns 分页角色路由信息
     */
  static async pageRoleRoute (pageRoleRouteParam: pageRoleRouteParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageRoleRoute',
      params: {
        currentPage: pageRoleRouteParam.currentPage,
        pageSize: pageRoleRouteParam.pageSize,
        routeLabel: pageRoleRouteParam.routeLabel,
        roleName: pageRoleRouteParam.roleName
      }
    })
  }

  /**
   * 角色路由数据修改or保存
   * @param RoleRouteFrom 角色路由信息表单
   * @returns 状态码
   */
  static async roleRouteSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/roleRouteSaveOrUp', params)
  }

  /**
   * 角色路由删除
   * @param params 角色路由ID集合
   * @returns 状态码
   */
  static async removeRoleRouteIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeRoleRouteIds', { params: params })
  }
}
