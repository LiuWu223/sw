import axios from '@/utils/axios'
import { AxiosResponse } from 'axios'

export class UploadApi {
  /**
   * 单文件上传
   * @param formData
   */
  static async single (formData: FormData): Promise<AxiosResponse> {
    return axios.post('/common/upload/single', formData)
  }
}
