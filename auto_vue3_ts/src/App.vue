<template>
  <el-config-provider :locale="zhCn">
    <router-view />
  </el-config-provider>
</template>

<script lang="ts" setup>
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
</script>

<style lang="scss">
@media screen  and (min-width: 800px){
  .el-dialog {
    width: 800px;
  }
}

@media screen and (max-width: 800px){
  .el-dialog {
    width: 100% !important;
  }
}
.fang-enter-from{
  transform: translateX(-100%);
}
.fang-enter-to{
  transform: translateX(0);
}
.fang-enter-active{
  transition: 0.5s ease;
}
</style>
