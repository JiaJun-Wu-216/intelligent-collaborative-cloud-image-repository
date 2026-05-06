<template>
  <div id="spaceManagePage">
    <div style="margin-bottom: 16px">
      <a-flex justify="space-between">
        <h2>空间管理</h2>
        <a-space>
          <a-button type="primary" href="/add-space" target="_blank">+ 创建空间</a-button>
        </a-space>
      </a-flex>
    </div>
    <!-- 搜索框 -->
    <a-form layout="inline" :model="searchParams" @finish="doSearch" style="margin-bottom: 16px">
      <a-form-item name="spaceName" label="空间名称">
        <a-input v-model:value="searchParams.spaceName" placeholder="请输入空间名称" allow-clear />
      </a-form-item>
      <a-form-item name="spaceLevel" label="空间级别">
        <a-select
          v-model:value="searchParams.spaceLevel"
          :options="SPACE_LEVEL_OPTIONS"
          placeholder="请选择空间级别"
          style="min-width: 180px"
          allow-clear
        />
      </a-form-item>
      <a-form-item name="userId" label="用户 ID">
        <a-input v-model:value="searchParams.userId" placeholder="请输入用户 ID" allow-clear />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">搜索</a-button>
      </a-form-item>
    </a-form>
    <!-- 表单 -->
    <a-table
      :columns="columns"
      :data-source="dataList"
      :pagination="pagination"
      @change="doTableChange"
    >
      <template #bodyCell="{ column, record }">
        <!-- 图片信息 -->
        <template v-if="column.dataIndex === 'spaceLevel'">
          <div>{{ SPACE_LEVEL_MAP[record.spaceLevel] }}</div>
        </template>
        <!-- 图片信息 -->
        <template v-if="column.dataIndex === 'spaceUseInfo'">
          <div>大小：{{ formatSize(record.totalSize) }} / {{ formatSize(record.maxSize) }}</div>
          <div>数量：{{ record.totalCount }} / {{ record.maxCount }}</div>
        </template>
        <!-- 相关时间 -->
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.dataIndex === 'editTime'">
          {{ dayjs(record.editTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space wrap>
            <a-button type="link" :href="`/add-space?id=${record.id}`" target="_blank"
              >编辑</a-button
            >
            <a-button type="link" danger @click="doDelete(record.id)">删除</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import type { Space, SpaceQueryRequest } from '@/api/EntityType.ts'
import { deleteSpace, doSpaceReview, listSpaceByPage } from '@/api/SpaceAPI.ts'
import dayjs from 'dayjs'
import { message } from 'ant-design-vue'
import { SPACE_LEVEL_MAP, SPACE_LEVEL_OPTIONS } from '@/constants/space.ts'
import { formatSize } from '@/utils'

const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    width: 80,
  },
  {
    title: '空间名称',
    dataIndex: 'spaceName',
  },
  {
    title: '空间级别',
    dataIndex: 'spaceLevel',
  },
  {
    title: '使用情况',
    dataIndex: 'spaceUseInfo',
  },
  {
    title: '用户 ID',
    dataIndex: 'userId',
    width: 80,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '编辑时间',
    dataIndex: 'editTime',
  },
  {
    title: '操作',
    key: 'action',
  },
]
const dataList = ref<Space[]>([])
const total = ref(0)

const searchParams = reactive<SpaceQueryRequest>({
  current: 1,
  pageSize: 10,
  sortField: 'create_time',
  sortOrder: 'descend',
})

const pagination = computed(() => {
  return {
    current: searchParams.current,
    pageSize: searchParams.pageSize,
    total: total.value,
    showSizeChanger: true,
    showTotal: (total: number) => `共 ${total} 条`,
  }
})

/**
 * 获取数据
 */
const fetchData = async () => {
  const response = await listSpaceByPage({
    ...searchParams,
  })
  if (response.code === 0 && response.data) {
    dataList.value = response.data.records ?? []
    total.value = Number.parseInt(response.data.total) ?? 0
  } else {
    message.error('获取数据失败：' + response.message)
  }
}

/**
 * 表格变化，重新获取数据
 * @param page  分页信息
 */
const doTableChange = (page: any) => {
  searchParams.current = page.current
  searchParams.pageSize = page.pageSize
  fetchData()
}

/**
 * 搜索数据
 */
const doSearch = () => {
  // 重置页码
  searchParams.current = 1
  fetchData()
}

/**
 * 删除数据
 * @param id  数据主键
 */
const doDelete = async (id: string) => {
  if (!id) {
    return
  }
  const response = await deleteSpace({ id })
  if (response.code === 0) {
    message.success('删除成功')
    // 刷新数据
    await fetchData()
  } else {
    message.error('删除失败')
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped></style>
