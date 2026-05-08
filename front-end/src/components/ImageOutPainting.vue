<template>
  <a-modal
    class="image-out-painting"
    v-model:visible="visible"
    title="AI 扩图"
    :footer="false"
    @cancel="closeModal"
  >
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="12">
        <h4>原始图片</h4>
        <img :src="picture?.url" :alt="picture?.name" style="width: 100%" />
      </a-col>
      <a-col :span="12">
        <h4>扩图结果</h4>
        <img v-if="resultImageUrl" :src="resultImageUrl" :alt="picture?.name" style="width: 100%" />
      </a-col>
    </a-row>
    <a-flex justify="center" gap="16">
      <a-button type="primary" :loading="!!taskId" ghost @click="createTask">生成图片</a-button>
      <a-button type="primary" :loading="uploadLoading" v-if="resultImageUrl" @click="handleUpload"
        >保存结果</a-button
      >
    </a-flex>
  </a-modal>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {message} from 'ant-design-vue'
import type {PictureUploadRequest, PictureVO} from '@/api/EntityType.ts'
import {
  createPictureOutPaintingTask,
  getPictureOutPaintingTask,
  uploadPictureByUrl,
} from '@/api/PictureAPI.ts'

interface Props {
  picture?: PictureVO
  spaceId?: number
  onSuccess?: (newPicture: PictureVO) => void
}

const props = defineProps<Props>()
const resultImageUrl = ref<string>('')
const taskId = ref<string>()

/**
 * 创建任务
 */
const createTask = async () => {
  if (!props.picture?.id) {
    return
  }
  const response = await createPictureOutPaintingTask({
    pictureId: props.picture.id,
    // 根据需要设置扩图参数
    parameters: {
      xScale: 2,
      yScale: 2,
    },
  })
  if (response.code === 0 && response.data) {
    message.success('创建任务成功，请耐心等待，不要退出页面')
    taskId.value = response.data.output?.taskId
    // 开启轮询
    startPolling()
  } else {
    message.error('创建任务失败' + response.message)
  }
}

// 轮询定时器
let pollingTimer: any = null

/**
 * 开启轮训
 */
const startPolling = () => {
  if (!taskId.value) {
    return
  }
  pollingTimer = setInterval(async () => {
    try {
      const response = await getPictureOutPaintingTask(taskId.value as string)
      if (response.code === 0 && response.data) {
        const taskResult = response.data.output
        if (taskResult?.taskStatus === 'SUCCEEDED') {
          message.success('扩图任务执行成功')
          resultImageUrl.value = taskResult.outputImageUrl as string
          // 清理轮询
          clearPolling()
        } else if (taskResult?.taskStatus === 'FAILED') {
          message.error('扩图任务执行失败')
          // 清理轮询
          clearPolling()
        }
      } else {
        message.error('创建任务失败' + response.message)
      }
    } catch (error: any) {
      console.error('扩图任务轮询失败', error)
      message.error('扩图任务轮询失败，' + error.message)
      // 清理轮询
      clearPolling()
    }
  }, 3000) // 每 3 秒轮询一次
}

/**
 * 关闭轮训
 */
const clearPolling = () => {
  if (pollingTimer) {
    clearInterval(pollingTimer)
    pollingTimer = null
    taskId.value = null
  }
}

// 是否正在上传
const uploadLoading = ref<boolean>(false)

/**
 * 上传
 * @param file
 */
const handleUpload = async ({ file }: any) => {
  uploadLoading.value = true
  try {
    const params: PictureUploadRequest = {
      fileUrl: resultImageUrl.value,
      spaceId: props.spaceId,
    }
    if (props.picture) {
      params.id = props.picture.id
    }
    const response = await uploadPictureByUrl(params)
    if (response.code === 0 && response.data) {
      message.success('图片上传成功')
      // 将上传成功的图片信息传递给父组件
      props.onSuccess?.(response.data)
      // 关闭弹窗
      closeModal()
    } else {
      message.error('图片上传失败' + response.message)
    }
  } catch (error: any) {
    console.error('图片上传失败', error)
    message.error('图片上传失败' + error.message)
  }
  uploadLoading.value = false
}

// 是否可见
const visible = ref(false)

// 打开弹窗
const openModal = () => {
  visible.value = true
}

// 关闭弹窗
const closeModal = () => {
  visible.value = false
}

// 暴露函数给父组件
defineExpose({
  openModal,
})
</script>

<style scoped>
.image-out-painting {
  text-align: center;
}
</style>
