import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  roleName: string
}

export class roleApi {
  /**
   * @param currentPage
   * @param pageSize
   */
  static async pageRole (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/system/systemRole/pageRole',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        roleName: queryParam.roleName
      }
    })
  }

  /**
   * 根据角色ID集合批量删除角色信息
   * @param params 角色ID集合
   */
  static async removeRoleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/system/systemRole/removeRoleIds', {
      // headers: {
      //   'Content-Type': 'application/json;charset=UTF-8',
      //   Authorization: localStorage.getItem('Authorization')
      // },
      params: params
    })
  }

  /**
   * 根据主键ID查询角色信息
   * @param roleId 角色ID
   */
  static async getRoleById (roleId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemRole/getRoleById/${roleId}`)
  }

  /**
   * 保存角色信息
   * @param schoolFrom 角色信息表单
   */
  static async save (roleFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemRole/saveOrUpdateRole', roleFrom)
  }

  // 查询所有角色信息
  static async selectRole (): Promise<AxiosResponse> {
    return axios.get('/system/systemRole/selectRole')
  }
}
