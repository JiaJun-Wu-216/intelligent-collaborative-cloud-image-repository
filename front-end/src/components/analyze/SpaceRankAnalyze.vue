<template>
  <div class="space-category-analyze">
    <a-card title="空间图片分类分析">
      <v-chart :option="option" style="height: 320px; max-width: 100%" :loading="loading" />
    </a-card>
  </div>
</template>

<script setup lang="ts">
import VChart from 'vue-echarts'
import 'echarts'
import {computed, ref, watchEffect} from 'vue'
import type {Space} from '@/api/EntityType.ts'
import {message} from 'ant-design-vue'
import {getSpaceRankAnalyze} from '@/api/SpaceAPI.ts'

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
const dataList = ref<Space[]>([])

/**
 * 加载数据
 */
const fetchData = async () => {
  loading.value = true
  const response = await getSpaceRankAnalyze({
    queryAll: props.queryAll,
    queryPublic: props.queryPublic,
    spaceId: props.spaceId,
    topN: 10,
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
  const spaceNames = dataList.value.map((item) => item.spaceName)
  const usageData = dataList.value.map((item) => (item.totalSize as number / (1024 * 1024)).toFixed(2)) // 转为 MB

  return {
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: spaceNames,
    },
    yAxis: {
      type: 'value',
      name: '空间使用量 (MB)',
    },
    series: [
      {
        name: '空间使用量 (MB)',
        type: 'bar',
        data: usageData,
        itemStyle: {
          color: '#5470C6', // 自定义柱状图颜色
        },
      },
    ],
  }
})
</script>

<style scoped></style>
