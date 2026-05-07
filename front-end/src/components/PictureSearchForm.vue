<template>
  <div class="picture-search-form">
    <!-- 搜索框 -->
    <a-form layout="inline" :model="searchParams" @finish="doSearch" style="margin-bottom: 16px">
      <a-form-item label="关键词">
        <a-input
          v-model:value="searchParams.searchText"
          placeholder="从名称和简介搜索"
          allow-clear
        />
      </a-form-item>
      <a-form-item name="category" label="图片分类">
        <a-auto-complete
          v-model:value="searchParams.category"
          placeholder="请输入分类"
          :options="categoryOptions"
          allow-clear
          style="min-width: 180px"
        />
      </a-form-item>
      <a-form-item name="tags" label="图片标签">
        <a-select
          v-model:value="searchParams.tags"
          mode="tags"
          :options="tagOptions"
          placeholder="请输入标签"
          allow-clear
          style="min-width: 180px"
        />
      </a-form-item>
      <a-form-item label="日期" name="dateRange">
        <a-range-picker
          style="width: 400px"
          show-time
          v-model:value="dateRange"
          :placeholder="['编辑开始日期', '编辑结束时间']"
          format="YYYY/MM/DD HH:mm:ss"
          :presets="rangePresets"
          @change="onRangeChange"
        />
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input v-model:value="searchParams.name" placeholder="请输入名称" allow-clear />
      </a-form-item>
      <a-form-item label="简介" name="introduction">
        <a-input v-model:value="searchParams.introduction" placeholder="请输入简介" allow-clear />
      </a-form-item>
      <a-form-item label="宽度" name="picWidth">
        <a-input-number v-model:value="searchParams.picWidth" />
      </a-form-item>
      <a-form-item label="高度" name="picHeight">
        <a-input-number v-model:value="searchParams.picHeight" />
      </a-form-item>
      <a-form-item label="格式" name="picFormat">
        <a-input v-model:value="searchParams.picFormat" placeholder="请输入格式" allow-clear />
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" html-type="submit" style="width: 96px">搜索</a-button>
          <a-button type="primary" html-type="reset" @click="doClear">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import type {PictureQueryRequest} from '@/api/EntityType.ts'
import dayjs from 'dayjs'
import {getTagCategory} from '@/api/PictureAPI.ts'
import {message} from 'ant-design-vue'

interface Props {
  onSearch?: (searchParams: PictureQueryRequest) => void
}

const props = defineProps<Props>()

const dateRange = ref<[]>([])
const rangePresets = ref([
  { label: '过去 7 天', value: [dayjs().add(-7, 'd'), dayjs()] },
  { label: '过去 14 天', value: [dayjs().add(-14, 'd'), dayjs()] },
  { label: '过去 30 天', value: [dayjs().add(-30, 'd'), dayjs()] },
  { label: '过去 90 天', value: [dayjs().add(-90, 'd'), dayjs()] },
])

/**
 * 日期范围更改时触发
 * @param dates
 * @param dateStrings
 */
const onRangeChange = (dates: any[], dateStrings: string[]) => {
  if (dates.length < 2) {
    searchParams.startEditTime = undefined
    searchParams.endEditTime = undefined
  } else {
    searchParams.startEditTime = dates[0].toDate()
    searchParams.endEditTime = dates[1].toDate()
  }
}

const searchParams = reactive<PictureQueryRequest>({})

const doSearch = () => {
  props.onSearch?.(searchParams)
}

/**
 * 重置搜索参数
 */
const doClear = () => {
  // 清空对象中的所有值
  Object.keys(searchParams).forEach((key: any) => {
    searchParams[key] = undefined
  })
  // 日期筛选项置空
  dateRange.value = []
  // 清空后重新搜索
  props.onSearch?.(searchParams)
}

const categoryOptions = ref<object[]>([])
const tagOptions = ref<object[]>([])

/**
 * 获取标签和分类选项
 */
const getTagCategoryOptions = async () => {
  const response = await getTagCategory()
  // 操作成功
  if (response.code === 0 && response.data) {
    tagOptions.value = (response.data.tagList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
    categoryOptions.value = (response.data.categoryList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
  } else {
    message.error('创建失败，' + response.message)
  }
}

onMounted(() => {
  getTagCategoryOptions()
})
</script>

<style scoped>
.picture-search-form .ant-form-item {
  margin-top: 16px;
}
</style>
