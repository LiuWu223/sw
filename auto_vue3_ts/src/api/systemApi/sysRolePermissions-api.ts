import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  currentPage: number,
  pageSize: number,
  permissionsName: string,
  roleName: string
}

export class rolePermissionsApi {
  /**
   * @param currentPage
   * @param pageSize
   */
  static async pageRolePermissions (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/system/systemRolePermissions/pageRolePermissions',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        permissionsName: queryParam.permissionsName,
        roleName: queryParam.roleName
      }
    })
  }

  /**
   * 根据用户角色ID集合批量删除用户角色信息
   * @param params 用户角色ID集合
   */
  static async removeRolePermissionsIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/system/systemRolePermissions/removeRolePermissionsIds', {
      params: params
    })
  }

  /**
  * 根据主键ID查询角色信息
  * @param userId 角色ID
  */
  static async getRoleByPermissions (roleId: string): Promise<AxiosResponse> {
    return axios.get(`/system/systemRolePermissions/getRoleByPermissions/${roleId}`, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  /**
   * 保存用户角色信息
   * @param schoolFrom 用户角色信息表单
   */
  static async save (rolePermissionsFrom: object): Promise<AxiosResponse> {
    return axios.post('/system/systemRolePermissions/saveOrUpdateRolePermissions', rolePermissionsFrom, {
    })
  }
}
