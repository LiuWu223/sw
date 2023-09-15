import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'

const service = axios.create({
  baseURL: '/micro_question_answer_admin_war',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization: localStorage.getItem('Authorization')
  }
})

// 请求拦截器
service.interceptors.request.use((config: AxiosRequestConfig | any) => {
  return config
}, (error) => {
  return Promise.resolve(error)
})

// 响应拦截器
service.interceptors.response.use((response: AxiosResponse) => {
  return response
}, (error) => {
  return Promise.resolve(error)
})
export default service
