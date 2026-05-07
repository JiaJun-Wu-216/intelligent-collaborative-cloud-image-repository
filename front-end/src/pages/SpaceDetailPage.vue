<template>
  <div id="spaceDetailPage">
    <!-- 空间信息 -->
    <a-flex justify="space-between" style="margin-bottom: 16px">
      <h2>{{ space.spaceName }}（私有空间）</h2>
      <a-space size="middle">
        <a-button type="primary" :href="`/add-picture?spaceId=${id}`" target="_blank"
          >+ 创建图片</a-button
        >
        <a-button :icon="h(EditOutlined)" @click="doBatchEdit"> 批量编辑</a-button>
        <a-tooltip
          :title="`占用空间 ${formatSize(space.totalSize)} / ${formatSize(space.maxSize)}`"
        >
          <a-progress
            type="circle"
            :parcent="(((space.totalSize ?? 0) * 100) / (space.maxSize ?? 0)).toFixed(1)"
            :size="42"
          />
        </a-tooltip>
      </a-space>
    </a-flex>
    <!-- 搜索表单 -->
    <picture-search-form :onSearch="onSearch" style="margin-bottom: 16px" />
    <!-- 按颜色搜索 -->
    <a-form-item label="按颜色搜索" style="margin: 16px 0">
      <color-picker format="hex" @pureColorChange="onColorChange" />
    </a-form-item>
    <!-- 图片列表 -->
    <picture-list
      :data-list="dataList"
      :loading="loading"
      :showOption="true"
      :onReload="fetchPictureVOListData"
    />
    <!-- 分页 -->
    <a-pagination
      style="text-align: center"
      v-model:current="searchParams.current"
      v-model:pageSize="searchParams.pageSize"
      :total="total"
      @change="onPageChange"
    />
    <BatchEditPicture
      ref="batchEditPictureModalRef"
      :spaceId="id as number"
      :pictureList="dataList"
      :onSuccess="onBatchEditPictureSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {h, onMounted, ref} from 'vue'
import type {PictureQueryRequest, PictureVO, SpaceVO} from '@/api/EntityType.ts'
import {message} from 'ant-design-vue'
import {getSpaceVOById} from '@/api/SpaceAPI.ts'
import {useRouter} from 'vue-router'
import {listPictureVOByPage, searchPictureByColor} from '@/api/PictureAPI.ts'
import {formatSize} from '@/utils'
import PictureList from '@/components/PictureList.vue'
import PictureSearchForm from '@/components/PictureSearchForm.vue'
import {ColorPicker} from 'vue3-colorpicker'
import 'vue3-colorpicker/style.css'
import BatchEditPicture from '@/components/BatchEditPicture.vue'
import {EditOutlined} from '@ant-design/icons-vue'

const props = defineProps<{
  id: string | number
}>()

const space = ref<SpaceVO>({})
const router = useRouter()

// 获取空间详情
const fetchSpaceDetail = async () => {
  try {
    const response = await getSpaceVOById(props.id as string)
    if (response.code === 0 && response.data) {
      space.value = response.data
    } else {
      message.error('获取空间详情失败，' + response.message)
    }
  } catch (e: any) {
    message.error('获取空间详情失败：' + e.message)
  }
}

const dataList = ref<PictureVO[]>([])
const total = ref(0)
const loading = ref(true)

// 搜索条件
const searchParams = ref<PictureQueryRequest>({
  current: 1,
  pageSize: 12,
  sortField: 'create_time',
  sortOrder: 'descend',
})

// 分页参数
const onPageChange = (page: number, pageSize: number) => {
  // 切换页号时，会修改搜索参数并获取数据
  searchParams.value.current = page
  searchParams.value.pageSize = pageSize
  fetchPictureVOListData()
}

// 获取数据
const fetchPictureVOListData = async () => {
  loading.value = true
  // 转换搜索参数
  const params = {
    spaceId: props.id,
    ...searchParams.value,
  }
  const response = await listPictureVOByPage(params)
  if (response.data) {
    dataList.value = response.data.records ?? []
    total.value = response.data.total ?? 0
  } else {
    message.error('获取数据失败，' + response.message)
  }
  loading.value = false
}

/**
 * 搜索
 * @param newSearchParams
 */
const onSearch = (newSearchParams: PictureQueryRequest) => {
  searchParams.value = {
    ...searchParams.value,
    ...newSearchParams,
    current: 1,
  }
  fetchPictureVOListData()
}

// 颜色搜索
const onColorChange = async (color: string) => {
  loading.value = true
  const response = await searchPictureByColor({
    picColor: color,
    spaceId: props.id as number,
  })
  if (response.code === 0 && response.data) {
    const data = response.data ?? []
    dataList.value = data
    total.value = data.length
  } else {
    message.error('获取数据失败，' + response.message)
  }
  loading.value = false
}

// 打开批量编辑弹窗
const doBatchEdit = () => {
  if (batchEditPictureModalRef.value) {
    batchEditPictureModalRef.value.openModal()
  }
}

// 分享弹窗引用
const batchEditPictureModalRef = ref()

// 批量编辑成功后，刷新数据
const onBatchEditPictureSuccess = () => {
  fetchPictureVOListData()
}

onMounted(() => {
  fetchSpaceDetail()
  fetchPictureVOListData()
})
</script>

<style scoped></style>
