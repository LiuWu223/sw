import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 分页查询表单 */
export interface pageDictParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 字典类型
  dictType: string
}

export class syDictApi {
  /**
     * 分页查询字典信息
     * @param pageDictParam 分页查询字典信息表单
     * @returns 分页字典信息
     */
  static async pageDict (pageDictParam: pageDictParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageDict',
      params: {
        currentPage: pageDictParam.currentPage,
        pageSize: pageDictParam.pageSize,
        dictType: pageDictParam.dictType
      }
    })
  }

  /**
   * 字典数据修改or保存
   * @param dictFrom 字典信息表单
   * @returns 状态码
   */
  static async syDictSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/syDictSaveOrUp', params)
  }

  /**
   * 字典删除
   * @param params 字典ID集合
   * @returns 状态码
   */
  static async removeDictIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeDictIds', { params: params })
  }

  static async selectDictType (dictType: string): Promise<AxiosResponse> {
    return axios.get(`/sy/syDict/selectDictType/${dictType}`)
  }
}
