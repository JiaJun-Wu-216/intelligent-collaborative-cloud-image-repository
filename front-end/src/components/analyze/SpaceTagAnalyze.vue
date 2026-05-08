<template>
  <div class="space-tag-analyze">
    <a-card title="图库标签词云">
      <v-chart :option="option" style="height: 320px; max-width: 100%" :loading="loading" />
    </a-card>
  </div>
</template>

<script setup lang="ts">
import VChart from 'vue-echarts'
import 'echarts'
import 'echarts-wordcloud'
import {computed, ref, watchEffect} from 'vue'
import type {SpaceTagAnalyzeResponse} from '@/api/EntityType.ts'
import {message} from 'ant-design-vue'
import {getSpaceTagAnalyze} from '@/api/SpaceAPI.ts'

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
const dataList = ref<SpaceTagAnalyzeResponse[]>([])

/**
 * 加载数据
 */
const fetchData = async () => {
  loading.value = true
  const response = await getSpaceTagAnalyze({
    queryAll: props.queryAll,
    queryPublic: props.queryPublic,
    spaceId: props.spaceId,
  })
  if (response.code === 0 && response.data) {
    dataList.value = response.data
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

// 图标选项
const option = computed(() => {
  const tagData = dataList.value.map((item) => ({
    name: item.tag,
    value: item.count,
  }))

  return {
    tooltip: {
      trigger: 'item',
      formatter: (params: any) => `${params.name}: ${params.value} 次`,
    },
    series: [
      {
        type: 'wordCloud',
        gridSize: 10,
        sizeRange: [12, 50], // 字体大小范围
        rotationRange: [-90, 90],
        shape: 'circle',
        textStyle: {
          color: () =>
            `rgb(${Math.round(Math.random() * 255)}, ${Math.round(
              Math.random() * 255,
            )}, ${Math.round(Math.random() * 255)})`, // 随机颜色
        },
        data: tagData,
      },
    ],
  }
})
</script>

<style scoped></style>
