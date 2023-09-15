import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  permissionsCode: string,
  permissionsName: string
}

export class permissionsApi {
  /**
   * @param currentPage
   * @param pageSize
   */
  static async pagePermissions (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('Authorization')
      },
      method: 'get',
      url: '/system/systemPermissions/pagePermissions',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        permissionsCode: queryParam.permissionsCode,
        permissionsName: queryParam.permissionsName
      }
    })
  }

  /**
   * 根据角色ID集合批量删除角色信息
   * @param params 角色ID集合
   */
  static async removePermissionsIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/system/systemPermissions/removePermissionsIds', {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('Authorization')
      },
      params: params
    })
  }

  /**
   * 根据主键ID查询角色信息
   * @param permissionsId 角色ID
   */
  static async getPermissionsById (permissionsId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemPermissions/getPermissionsById/${permissionsId}`)
  }

  /**
   * 保存角色信息
   * @param schoolFrom 角色信息表单
   */
  static async save (permissionsFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemPermissions/saveOrUpdatePermissions', permissionsFrom)
  }

  static async selectPermissionsName (permissionsFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemPermissions/selectPermissionsCode', permissionsFrom)
  }
}
