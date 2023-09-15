import { createStore } from 'vuex'

const state = {
  asyncRoutesMark: false,
  menu: [],
  routers: []
}
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
