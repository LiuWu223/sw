import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export interface QueryParam {
  // 用户账号
  userAccount: string,
  // 用户密码
  userPassword: string
}
export interface DeviceParam {
  currentPage: number,
  pageSize: number,
  deviceName: string,
  deviceType: string,
  userAccount: string
}
export class userApi {
  /**
   * 获取已连接adb设备
   * @param params 角色ID集合
   */
  static async adbDevices (): Promise<AxiosResponse> {
    return axios.get('/system/systemUser/adbDevices')
  }

  /**
   * 登录
   * @param queryParam
   * @returns
   */
  static async loginUser (queryParam: QueryParam): Promise<AxiosResponse> {
    return axios({
      method: 'post',
      url: '/system/systemUser/loginUser',
      data: queryParam
    })
  }

  /**
  * 查询所有
  */
  static async selectTask (): Promise<AxiosResponse> {
    return axios.get('/swtest/swtestTask/selectTask', {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  /**
   * 新增/修改
   */
  static async save (taskFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestTask/saveOrUpdateTask', taskFrom, {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  /**
   * 删除
   */
  static async removeTaskIds (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestTask/removeTask', {
      params: params,
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  /**
  * 根据主键ID查询
  */
  static async getTaskIdUpdata (taskId: string, taskState: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestTask/getTaskIdUpdata/${taskId}/${taskState}`)
  }

  /**
   * 设备管理
   */

  // 新增
  static async saveOrUpdateDevice (deviceFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestDevices/saveOrUpdateDevice', deviceFrom, {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  // 查询
  static async pageDevice (queryParam: DeviceParam): Promise<AxiosResponse> {
    return axios({
      method: 'get',
      url: '/swtest/swtestDevices/pageDevice',
      params: {
        currentPage: queryParam.currentPage,
        pageSize: queryParam.pageSize,
        deviceName: queryParam.deviceName,
        deviceType: queryParam.deviceType,
        userAccount: queryParam.userAccount
      },
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  // 删除
  static async removeDevice (params: object): Promise<AxiosResponse> {
    return axios.delete('/swtest/swtestDevices/removeDevice', {
      params: params,
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  // 根据id查询
  static async getDeviceId (deviceId: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestDevices/getDeviceId/${deviceId}`, {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  // 修改设备状态
  static async setState (setStateFrom: object): Promise<AxiosResponse> {
    return axios.post('/swtest/swtestDevices/setState', setStateFrom, {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }

  // 根据用户名称查询任务
  static async selectUserIdByTask (userAccount: string): Promise<AxiosResponse> {
    return axios.get(`/swtest/swtestTask/selectUserIdByTask/${userAccount}`, {
      headers: {
        Authorization: localStorage.getItem('Authorization')
      }
    })
  }
}
