<template>
  <div>
    <tinymce-editor v-model="content" :init="init"></tinymce-editor>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import TinymceEditor from '@tinymce/tinymce-vue'
import { UploadApi } from '@/api/common/UploadApi'
import axios from '@/utils/axios'

const content = ref('')
const init = reactive({
  language: 'zh_CN',
  plugins: 'preview importcss tinydrive searchreplace autolink autosave save directionality visualblocks visualchars fullscreen image link media template codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons',
  toolbar: 'undo redo | bold italic underline strikethrough | fontfamily fontsize blocks | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist checklist | forecolor backcolor casechange permanentpen formatpainter removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media pageembed template link anchor codesample | a11ycheck ltr rtl | showcomments addcomment | footnotes | mergetags',
  images_upload_handler: (blobInfo: { blob: () => string | Blob; }) => new Promise((resolve) => {
    const formData = new FormData()
    formData.append('file', blobInfo.blob())
    UploadApi.single(formData).then(res => {
      if (res.data.success) {
        // http://127.0.0.1:8080/micro_question_answer/upload/kasjdhaklsdjlkaskjdlas.jpg
        const path = window.location.origin + axios.defaults.baseURL + res.data.data
        resolve(path)
      }
    })
  })
})
</script>

<style scoped lang="scss">

</style>
