<template>
  <div class="url-picture-upload">
    <a-input-group compact style="margin-bottom: 16px">
      <a-input
        v-model:value="fileUrl"
        style="width: calc(100% - 120px)"
        placeholder="请输入图片 URL"
      />
      <a-button type="primary" :loading="loading" @click="handleUpload" style="width: 120px"
        >提交</a-button
      >
    </a-input-group>
    <div class="image-wrapper">
      <img v-if="picture?.url" :src="picture?.url" alt="" />
    </div>
  </div>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import type {UploadProps} from 'ant-design-vue'
import {message} from 'ant-design-vue'
import type {PictureUploadRequest, PictureVO} from '@/api/EntityType.ts'
import {uploadPictureByUrl} from '@/api/PictureAPI.ts'

interface Props {
  picture?: PictureVO
  spaceId?: number
  onSuccess?: (newPicture: PictureVO) => void
}

const props = defineProps<Props>()

const loading = ref<boolean>(false)

const fileUrl = ref<string>()

/**
 * 上传图片
 */
const handleUpload = async () => {
  loading.value = true
  try {
    const params: PictureUploadRequest = {
      fileUrl: fileUrl.value,
    }
    params.spaceId = props.spaceId
    if (props.picture) {
      params.id = props.picture.id
    }
    const response = await uploadPictureByUrl(params)
    if (response.code === 0 && response.data) {
      message.success('图片上传成功')
      // 将上传成功的图片信息传递给父组件
      props.onSuccess?.(response.data)
    } else {
      message.error('图片上传失败' + response.message)
    }
  } catch (error: any) {
    console.error('图片上传失败', error)
    message.error('图片上传失败' + error.message)
  }
  loading.value = false
}

/**
 * 上传前的校验
 * @param file  文件信息
 */
const beforeUpload = (file: UploadProps['fileList'][number]) => {
  // 校验图片格式
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
  if (!isJpgOrPng) {
    message.error('不支持上传该格式的图片，推荐 jpg 或 png')
  }
  // 校验图片大小
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isLt2M) {
    message.error('不能上传超过 2M 的图片')
  }
  return isJpgOrPng && isLt2M
}
</script>
<style scoped>
.url-picture-upload {
  margin-bottom: 16px;
}

.url-picture-upload .image-wrapper {
  text-align: center;
  margin-top: 16px;
}

.url-picture-upload .image-wrapper img {
  max-width: 100%;
  max-height: 480px;
}
</style>
