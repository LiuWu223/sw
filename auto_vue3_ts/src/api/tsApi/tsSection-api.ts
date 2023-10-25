import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageSectionParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 测试测试项名称
  sectionName: string
}

export class tsSectionApi {
  /**
     * 分页查询测试用例测试项信息
     * @param pageSectionParam 分页查询测试用例测试项信息表单
     * @returns 分页测试用例测试项信息
     */
  static async pageTsSection (pageSectionParam: pageSectionParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/ts/tsSection/pageSection',
      params: {
        currentPage: pageSectionParam.currentPage,
        pageSize: pageSectionParam.pageSize,
        sectionName: pageSectionParam.sectionName
      }
    })
  }

  /**
   * 测试用例测试项数据修改or保存
   * @param SectionFrom 测试用例测试项信息表单
   * @returns 状态码
   */
  static async sectionSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/ts/tsSection/sectionSaveOrUp', params)
  }

  /**
   * 测试用例测试项删除
   * @param params 测试用例测试项ID集合
   * @returns 状态码
   */
  static async removeSectionIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/ts/tsSection/removeSectionIds', { params: params })
  }
}
