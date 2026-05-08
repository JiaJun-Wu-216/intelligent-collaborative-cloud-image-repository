<template>
  <div class="space-user-analyze">
    <a-flex gap="middle">
      <a-card title="存储空间" style="width: 50%">
        <div style="height: 320px; text-align: center">
          <h3>
            {{ formatSize(data.usedSize) }} /
            {{ data.maxSize ? formatSize(data.maxSize) : '无限制' }}
          </h3>
          <a-progress type="dashboard" :percent="data.sizeUsageRatio ?? 0" />
        </div>
      </a-card>
      <a-card title="图片数量" style="width: 50%">
        <div style="height: 320px; text-align: center">
          <h3>
            {{ data.usedCount }} /
            {{ data.maxCount ?? '无限制' }}
          </h3>
          <a-progress type="dashboard" :percent="data.sizeUsageRatio ?? 0" />
        </div>
      </a-card>
    </a-flex>
  </div>
</template>

<script setup lang="ts">
import {ref, watchEffect} from 'vue'
import type {SpaceUsageAnalyzeResponse} from '@/api/EntityType.ts'
import {message} from 'ant-design-vue'
import {getSpaceUsageAnalyze} from '@/api/SpaceAPI.ts'
import {formatSize} from '@/utils'

interface Props {
  queryAll?: boolean
  queryPublic?: boolean
  spaceId?: number
}

const props = withDefaults(defineProps<Props>(), {
  queryAll: false,
  queryPublic: false,
})

// 加载状态
const loading = ref<boolean>(false)
// 图表数据
const data = ref<SpaceUsageAnalyzeResponse>({})

/**
 * 加载数据
 */
const fetchData = async () => {
  loading.value = true
  const response = await getSpaceUsageAnalyze({
    queryAll: props.queryAll,
    queryPublic: props.queryPublic,
    spaceId: props.spaceId,
  })
  if (response.code === 0 && response.data) {
    data.value = response.data
  } else {
    message.error('获取数据失败，' + response.message)
  }
  loading.value = false
}

/**
 * 监听变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  fetchData()
})
</script>

<style scoped></style>
