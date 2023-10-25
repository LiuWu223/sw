import { createStore } from 'vuex'

/** 状态属性，用来存储应用程序中的所有状态数据 */
const state = {
  asyncRoutesMark: false,
  menu: [],
  routers: []
}

/** 用于修改状态中的数据 */
const mutations = {
  setAsyncRoutesMark (state, data) {
    state.asyncRoutesMark = data
  },

  setMenuList (state, data) {
    state.menu = data
  },

  setRouters (state, data) {
    state.routers = data
  }
}
const options = {
  state,
  mutations
}
const store = createStore(options)
export default store
