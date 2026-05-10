<template>
  <div id="spaceManagePage">
    <div style="margin-bottom: 16px">
      <a-flex justify="space-between">
        <h2>空间成员管理</h2>
        <a-space>
          <a-button type="primary" ghost href="/space-analyze?queryPublic=1" target="_blank">
            分析公共图库
          </a-button>
          <a-button type="primary" ghost href="/space-analyze?queryAll=1" target="_blank">
            分析全空间
          </a-button>
          <a-button type="primary" href="/add-space" target="_blank">+ 创建空间</a-button>
        </a-space>
      </a-flex>
    </div>
    <!-- 添加成员表单 -->
    <a-form layout="inline" :model="formData" @finish="handleSubmit">
      <a-form-item label="用户 id" name="userId">
        <a-input v-model:value="formData.userId" placeholder="请输入用户 id" allow-clear />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">添加用户</a-button>
      </a-form-item>
    </a-form>
    <!-- 表单 -->
    <a-table :columns="columns" :data-source="dataList">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'userInfo'">
          <a-space>
            <a-avatar :src="record.user?.userAvatar" />
            {{ record.user?.userName }}
          </a-space>
        </template>
        <template v-if="column.dataIndex === 'spaceRole'">
          <a-select
            v-model:value="record.spaceRole"
            :options="SPACE_ROLE_OPTIONS"
            @change="(value: any) => editSpaceRole(value, record)"
          />
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space wrap>
            <a-button type="link" danger @click="doDelete(record.id)">删除</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import type {SpaceUserAddRequest, SpaceUserVO} from '@/api/EntityType.ts'
import {addSpaceUser, deleteSpaceUser, editSpaceUser, listSpaceUser} from '@/api/SpaceAPI.ts'
import dayjs from 'dayjs'
import {message} from 'ant-design-vue'
import {SPACE_ROLE_OPTIONS} from '@/constants/space.ts'

interface Props {
  id: string
}

const props = defineProps<Props>()

// 表格列
const columns = [
  {
    title: '用户',
    dataIndex: 'userInfo',
  },
  {
    title: '角色',
    dataIndex: 'spaceRole',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
    key: 'action',
  },
]

const dataList = ref<SpaceUserVO[]>([])

// 添加成员表单
const formData = reactive<SpaceUserAddRequest>({})

/**
 * 获取数据
 */
const fetchData = async () => {
  const spaceId = props.id
  if (!spaceId) {
    return
  }
  const response = await listSpaceUser({
    spaceId: spaceId,
  })
  if (response.code === 0 && response.data) {
    dataList.value = response.data ?? []
  } else {
    message.error('获取数据失败：' + response.message)
  }
}

const handleSubmit = async () => {
  const spaceId = props.id
  if (!spaceId) {
    return
  }
  const response = await addSpaceUser({
    spaceId,
    ...formData,
  })
  if (response.code === 0) {
    message.success('添加成功')
    // 刷新数据
    await fetchData()
  } else {
    message.error('添加失败，' + response.message)
  }
}

/**
 * 编辑空间成员
 * @param value
 * @param record
 */
const editSpaceRole = async (value: any, record: SpaceUserVO) => {
  const response = await editSpaceUser({
    id: record.id,
    spaceRole: value,
  })
  if (response.code === 0) {
    message.success('修改成功')
  } else {
    message.error('修改失败，' + response.message)
  }
}

/**
 * 删除数据
 * @param id  数据主键
 */
const doDelete = async (id: string) => {
  if (!id) {
    return
  }
  const response = await deleteSpaceUser({ id })
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
