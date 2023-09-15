import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueUeditorWrap from 'vue-ueditor-wrap'
// import tool from './utils/'
import tool from './utils/tool.js'
import 'element-plus/theme-chalk/el-loading.css'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-notification.css'
import 'element-plus/theme-chalk/el-message-box.css'
import 'element-plus/theme-chalk/el-drawer.css'

createApp(App).use(store).use(router).use(VueUeditorWrap).use(tool).mount('#app')
