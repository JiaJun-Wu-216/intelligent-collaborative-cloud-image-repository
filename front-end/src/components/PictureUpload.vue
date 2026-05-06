<template>
  <div class="picture-upload">
    <a-upload
      list-type="picture-card"
      :show-upload-list="false"
      :custom-request="handleUpload"
      :before-upload="beforeUpload"
    >
      <img v-if="picture?.url" :src="picture?.url" alt="avatar" />
      <div v-else>
        <loading-outlined v-if="loading"></loading-outlined>
        <plus-outlined v-else></plus-outlined>
        <div class="ant-upload-text">点击或拖拽上传图片</div>
      </div>
    </a-upload>
  </div>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import {LoadingOutlined, PlusOutlined} from '@ant-design/icons-vue'
import type {UploadProps} from 'ant-design-vue'
import {message} from 'ant-design-vue'
import type {PictureUploadRequest, PictureVO} from '@/api/EntityType.ts'
import {uploadPicture} from '@/api/PictureAPI.ts'

interface Props {
  picture?: PictureVO
  spaceId?: number
  onSuccess?: (newPicture: PictureVO) => void
}

const props = defineProps<Props>()

const loading = ref<boolean>(false)

/**
 * 上传图片
 */
const handleUpload = async ({ file }: any) => {
  loading.value = true
  try {
    const params:PictureUploadRequest = props.picture ? { id: props.picture.id } : {}
    params.spaceId = props.spaceId
    const response = await uploadPicture(file, params as PictureUploadRequest)
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
.picture-upload :deep(.ant-upload) {
  width: 100% !important;
  height: 100% !important;
  min-width: 152px;
  min-height: 152px;
}

.picture-upload img {
  max-width: 100%;
  max-height: 480px;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
