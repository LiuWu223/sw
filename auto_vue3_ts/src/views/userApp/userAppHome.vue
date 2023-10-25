<template>
  <div style="display: flex;flex-direction: row;">
    <div class="mains">
      <div class="div1">
        <user-app-index-count-data-view></user-app-index-count-data-view>
      </div>
      <div class="div1">12</div>
    </div>
    <div class="div2">
      111
    </div>
  </div>
</template>
<script lang="ts" setup>
import UserAppIndexCountDataView from './userAppIndexCountDataView.vue'
</script>
<style scoped lang="scss">
.mains{
  width: 77%;
  display: flex;
  flex-direction: column;
}
.div1{
  width: 97%;
  height: 200px;
  background: rgb(255, 255, 255);
  margin-bottom: 15px;
  background-color: #fff;
  padding: 10px;
  border-radius: 6px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.div2{
  width: 20%;
  height: 300px;
  background: rgb(255, 255, 255);
  margin-bottom: 15px;
  background-color: #fff;
  padding: 10px;
  border-radius: 6px;
  margin-left: 15px;
}
</style>
