<template>
  <div id="spaceDetailPage">
    <!-- 空间信息 -->
    <a-flex justify="space-between" style="margin-bottom: 16px">
      <h2>{{ space.spaceName }}（{{ SPACE_TYPE_MAP[space.spaceType] }}）</h2>
      <a-space size="middle">
        <a-button
          type="primary"
          :href="`/add-picture?spaceId=${id}`"
          target="_blank"
          v-if="canUploadPicture"
          >+ 创建图片</a-button
        >
        <a-button
          v-if="canManageSpaceUser"
          type="primary"
          ghost
          :icon="h(TeamOutlined)"
          :href="`/space-user-manage/${id}`"
          target="_blank"
        >
          成员管理
        </a-button>
        <a-button
          v-if="canManageSpaceUser"
          type="primary"
          ghost
          :icon="h(BarChartOutlined)"
          :href="`/space-analyze?spaceId=${id}`"
          target="_blank"
        >
          空间分析
        </a-button>
        <a-button v-if="canEditPicture" :icon="h(EditOutlined)" @click="doBatchEdit">
          批量编辑</a-button
        >
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
      :canEdit="canEditPicture"
      :canDelete="canDeletePicture"
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
import {computed, h, onMounted, ref, watch} from 'vue'
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
import {BarChartOutlined, EditOutlined, TeamOutlined} from '@ant-design/icons-vue'
import {SPACE_PERMISSION_ENUM, SPACE_TYPE_MAP} from '@/constants/space.ts'

const props = defineProps<{
  id: string | number
}>()

const space = ref<SpaceVO>({})
const router = useRouter()

// 通用权限检查函数
const createPermissionChecker = (permission: string) => {
  return computed(() => {
    return (space.value.permissionList ?? []).includes(permission)
  })
}

// 定义权限检查
const canManageSpaceUser = createPermissionChecker(SPACE_PERMISSION_ENUM.SPACE_USER_MANAGE)
const canUploadPicture = createPermissionChecker(SPACE_PERMISSION_ENUM.PICTURE_UPLOAD)
const canEditPicture = createPermissionChecker(SPACE_PERMISSION_ENUM.PICTURE_EDIT)
const canDeletePicture = createPermissionChecker(SPACE_PERMISSION_ENUM.PICTURE_DELETE)

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

// 空间 ID 改变时，必须重新获取数据
watch(
  () => props.id,
  (newSpaceId) => {
    fetchSpaceDetail()
    fetchPictureVOListData()
  },
)

onMounted(() => {
  fetchSpaceDetail()
  fetchPictureVOListData()
})
</script>

<style scoped></style>
