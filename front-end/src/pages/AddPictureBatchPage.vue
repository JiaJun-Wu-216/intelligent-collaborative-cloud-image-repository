<template>
  <div id="addPictureBatchPage">
    <h2 style="margin-bottom: 16px">批量创建</h2>
    <!-- 图片信息表单 -->
    <a-form layout="vertical" name="formData" :model="formData" @finish="handleSubmit">
      <a-form-item name="searchText" label="关键词">
        <a-input v-model:value="formData.searchText" placeholder="请输入关键词" allow-clear />
      </a-form-item>
      <a-form-item name="count" label="抓取数量">
        <a-input-number
          v-model:value="formData.count"
          placeholder="请输入数量"
          :min="1"
          :max="30"
          allow-clear
          style="min-width: 180px"
        />
      </a-form-item>
      <a-form-item name="namePrefix" label="图片名称前缀">
        <a-input
          v-model:value="formData.namePrefix"
          placeholder="请输入图片名称前缀，会自动补充序号"
          allow-clear
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" :loading="loading" style="width: 100%">
          开始任务
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue'
import type {PictureUploadByBatchRequest} from '@/api/EntityType.ts'
import {uploadPictureByBatch} from '@/api/PictureAPI.ts'
import {message} from 'ant-design-vue'
import {useRouter} from 'vue-router'

const router = useRouter()

const formData = reactive<PictureUploadByBatchRequest>({
  count: 10,
})

const loading = ref<boolean>(false)

/**
 * 提交表单
 */
const handleSubmit = async () => {
  loading.value = true
  const response = await uploadPictureByBatch({
    ...formData,
  })
  // 操作成功
  if (response.code === 0 && response.data) {
    message.success(`创建成功,共 ${response.data} 条`)
    // 跳转到主页
    await router.push({
      path: '/',
    })
  } else {
    message.error('创建失败，' + response.message)
  }
  loading.value = false
}
</script>

<style scoped>
#addPictureBatchPage {
  max-width: 720px;
  margin: 0 auto;
}
</style>
