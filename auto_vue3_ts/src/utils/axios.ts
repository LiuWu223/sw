import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: '/auto_sw_admin_war',
  timeout: 30000
})

/**
 * 请求拦截器,
 * 作用：1、在发送请求之前做些什么
 *      2、可以在此处添加请求头、身份验证等
 */
service.interceptors.request.use((config: AxiosRequestConfig | any) => {
  if (config.url === '/common/upload/single') {
    config.headers = {
      'Content-Type': 'multipart/form-data',
      Authorization: localStorage.getItem('Authorization')
    }
    return config
  }
  // <此处可以对数据进行处理>
  config.headers = {
    'Content-Type': 'application/json',
    Authorization: localStorage.getItem('Authorization')
  }
  return config
}, (error) => {
  // <此处可做错误处理>
  return Promise.resolve(error)
})

/**
 * 添加响应拦截器 ,
 * 作用：1、对响应数据做些什么
 *      2、以在此处处理数据、错误处理等
 */
service.interceptors.response.use((response: AxiosResponse) => {
  // <此处可以对数据进行处理>
  // console.log(response)
  // if (response.status === 200) {
  //   if (response.data.success === false) {
  //     ElNotification({
  //       title: 'Error',
  //       message: response.data.message,
  //       type: 'error',
  //       duration: 1000
  //     })
  //   } else {
  //     ElNotification({
  //       title: 'Success',
  //       message: response.data.message,
  //       type: 'success',
  //       duration: 1000
  //     })
  //   }
  // }
  return response
}, (error) => {
  console.log(error)
  // <此处可做错误处理>
  // const errorData = error.response.data
  // 正则匹配中文
  // const regex = /[a-zA-Z0-9\u4e00-\u9fa5\s]/g
  // const regexErr = errorData.match(regex)
  // let errorInfo = ''
  // // 遍历拼接每个中文
  // if (regexErr) {
  //   regexErr.forEach(char => {
  //     errorInfo += char
  //   })
  // }
  ElMessage({
    message: error.message,
    type: 'error'
  })
  return Promise.resolve(error)
})

export default service
