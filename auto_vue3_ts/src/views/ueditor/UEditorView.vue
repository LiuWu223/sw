<template>
  <vue-ueditor-wrap
    v-model="msg"
    :config="editorConfig"
    editor-id="editor"
    :editorDependencies="['ueditor.config.js','ueditor.all.js']"
  ></vue-ueditor-wrap>
  <h1>--------显示内容----------</h1>
  <div v-html="msg"></div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'

const msg = ref('')
const editorConfig = reactive({
  UEDITOR_HOME_URL: '/UEditorPlus/',
  initialFrameWidth: '99.5%'
})
</script>

<style scoped lang="scss">

</style>
