import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageAuthorityParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 权限名称
  authorityName: string
}

export class syAuthorityApi {
  /**
     * 分页查询权限信息
     * @param pageAuthorityParam 分页查询权限信息表单
     * @returns 分页权限信息
     */
  static async pageAuthority (pageAuthorityParam: pageAuthorityParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageAuthority',
      params: {
        currentPage: pageAuthorityParam.currentPage,
        pageSize: pageAuthorityParam.pageSize,
        authorityName: pageAuthorityParam.authorityName
      }
    })
  }

  /**
   * 权限数据修改or保存
   * @param AuthorityFrom 权限信息表单
   * @returns 状态码
   */
  static async syAuthoritySaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/syAuthoritySaveOrUp', params)
  }

  /**
   * 权限删除
   * @param params 权限ID集合
   * @returns 状态码
   */
  static async removeAuthorityIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeAuthorityIds', { params: params })
  }
}
