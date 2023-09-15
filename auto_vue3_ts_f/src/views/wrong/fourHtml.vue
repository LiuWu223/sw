import router from "@/router";

<template>
    <div class="FourHtml" style="display: flex;justify-content: center;align-items: center;height: 800px;">
      <img style="cursor:pointer;" @click="backA()" src="../../assets/images/404.gif"/>
    </div>
</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function backA () {
  router.go(-1)
}
</script>
