/**
 * 防抖函数
 * @param fn 传入一个方法
 * @param delay 超时时间
 * @returns 原方法
 */
export const useDebounce = (fn: (...args) => any, delay: number) => {
  let timer:any
  return function (...args: any[]) {
    // 删除setTimout
    clearTimeout(timer)
    timer = setTimeout(() => {
      fn(...args)
    }, delay)
  }
}
