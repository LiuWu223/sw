import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/**分页查询表单 */
export interface pageSettingParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // 用户ID
  userId: string,
  // 系统主题
  settingTheme: string,
  // 系统语言
  settingLingo: string,
  // 自动登录
  settingAutoLogin: string
}

export class sySettingApi {
  /**
     * 分页查询用户设置信息
     * @param pageSettingParam 分页查询用户设置信息表单
     * @returns 分页用户设置信息
     */
  static async pageSetting (pageSettingParam: pageSettingParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/sy/syUser/pageSetting',
      params: {
        currentPage: pageSettingParam.currentPage,
        pageSize: pageSettingParam.pageSize,
        userId: pageSettingParam.userId,
        settingTheme: pageSettingParam.settingTheme,
        settingLingo: pageSettingParam.settingLingo,
        settingAutoLogin: pageSettingParam.settingAutoLogin
      }
    })
  }

  /**
   * 用户设置数据修改or保存
   * @param settingFrom 用户设置信息表单
   * @returns 状态码
   */
  static async settingSaveOrUp (settingFrom: object): Promise<AxiosResponse> {
    return axios.post('/sy/syUser/settingSaveOrUp', settingFrom)
  }

  /**
   * 用户设置删除
   * @param params 用户设置ID集合
   * @returns 状态码
   */
  static async removeSettingIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/sy/syUser/removeSettingIds', { params: params })
  }
}
