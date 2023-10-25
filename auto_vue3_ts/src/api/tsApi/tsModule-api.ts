import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 分页查询表单 */
export interface pageModuleParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 测试模块名称
  moduleName: string
}

export class tsModuleApi {
  /**
     * 分页查询测试用例模块信息
     * @param pageModuleParam 分页查询测试用例模块信息表单
     * @returns 分页测试用例模块信息
     */
  static async pageTsModule (pageModuleParam: pageModuleParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsModule/pageModule',
      params: {
        currentPage: pageModuleParam.currentPage,
        pageSize: pageModuleParam.pageSize,
        moduleName: pageModuleParam.moduleName
      }
    })
  }

  /**
   * 测试用例模块数据修改or保存
   * @param ModuleFrom 测试用例模块信息表单
   * @returns 状态码
   */
  static async moduleSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsModule/moduleSaveOrUp', params)
  }

  /**
   * 测试用例模块删除
   * @param params 测试用例模块ID集合
   * @returns 状态码
   */
  static async removeModuleIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsModule/removeModuleIds', { params: params })
  }

  /**
   * 查询模块项目测试用例
   * @returns 查询模块项目测试用例
   */
  static async selectModulesAndSectionAndCase (): Promise<AxiosResponse> {
    return axios.get('/ts/tsModule/selectModulesAndSectionAndCase')
  }
}
