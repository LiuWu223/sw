const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
module.exports = defineConfig({
  transpileDependencies: true,
  // 部署环境配置
  devServer: {
    // 端口号配置
    port: 3000,
    // 代理配置
    proxy: {
      // /代表所有请求连接代理到http://127.0.0.1:8080这个链接
      '/micro_question_answer_admin_war': {
        // 代理目标
        target: 'http://127.0.0.1:8080',
        // 是否变更来源
        changeOrigin: true,
        // 是否使用ws请求
        ws: false,
        // 路径重新规则
        pathRewrite: {
          '^/': ''
        }
      }
    }
  },
  chainWebpack: config => {
    config.plugin('html').tap(args => {
      args[0].title = 'test'
      return args
    })
  },
  // Element UI 组件库按需导入——自动导入配置
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()]
      }),
      Components({
        resolvers: [ElementPlusResolver()]
      })
    ]
  }
})
