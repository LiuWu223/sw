import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageRoleParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 路由名称
  routeLabel: string
}

export class syRouteApi {
  /**
     * 分页查询路由信息
     * @param pageRoleParam 分页查询路由信息表单
     * @returns 分页路由信息
     */
  static async pageRoute (pageRoleParam: pageRoleParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageRoute',
      params: {
        currentPage: pageRoleParam.currentPage,
        pageSize: pageRoleParam.pageSize,
        routeLabel: pageRoleParam.routeLabel
      }
    })
  }

  /**
   * 路由数据修改or保存
   * @param roleFrom 路由信息表单
   * @returns 状态码
   */
  static async syRouteSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/syRouteSaveOrUp', params)
  }

  /**
   * 路由删除
   * @param params 路由ID集合
   * @returns 状态码
   */
  static async removeRouteIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeRouteIds', { params: params })
  }
}
