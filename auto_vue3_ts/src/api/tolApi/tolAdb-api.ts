import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageAdbParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // ADB名称
  adbName: string,
  adbDir: string
}

export class tolAdbApi {
  /**
     * 分页查询ADB信息
     * @param pageAdbParam 分页查询ADB信息表单
     * @returns 分页ADB信息
     */
  static async pageAdb (pageAdbParam: pageAdbParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/tol/tolAdb/pageAdb',
      params: {
        currentPage: pageAdbParam.currentPage,
        pageSize: pageAdbParam.pageSize,
        adbName: pageAdbParam.adbName,
        adbDir: pageAdbParam.adbDir
      }
    })
  }

  /**
   * ADB数据修改or保存
   * @param AdbFrom ADB信息表单
   * @returns 状态码
   */
  static async syAdbSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/tol/tolAdb/adbSaveOrUp', params)
  }

  /**
   * ADB删除
   * @param params ADBID集合
   * @returns 状态码
   */
  static async removeAdbIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/tol/tolAdb/removeAdbIds', { params: params })
  }
}
