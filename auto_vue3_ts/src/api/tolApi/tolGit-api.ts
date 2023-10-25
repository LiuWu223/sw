import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

/** 分页查询表单 */
export interface pageGitParam {
  // 当前页
  currentPage: number,
  // 页大小
  pageSize: number,
  // GIT名称
  gitName: string
}

export class tolGitApi {
  /**
     * 分页查询GIT信息
     * @param pageGitParam 分页查询GIT信息表单
     * @returns 分页GIT信息
     */
  static async pageGit (pageGitParam: pageGitParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/tol/tolGit/pageGit',
      params: {
        currentPage: pageGitParam.currentPage,
        pageSize: pageGitParam.pageSize,
        gitName: pageGitParam.gitName
      }
    })
  }

  /**
   * GIT数据修改or保存
   * @param GitFrom GIT信息表单
   * @returns 状态码
   */
  static async gitSaveOrUp (params: object): Promise<AxiosResponse> {
    return axios.post('/tol/tolGit/gitSaveOrUp', params)
  }

  /**
   * GIT删除
   * @param params GITID集合
   * @returns 状态码
   */
  static async removeGitIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/tol/tolGit/removeGitIds', { params: params })
  }
}
