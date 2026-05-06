<template>
  <div id="addSpacePage">
    <h2 style="margin-bottom: 16px">
      {{ route.query?.id ? '修改空间' : '创建空间' }}
    </h2>
    <!-- 空间信息表单 -->
    <a-form
      layout="vertical"
      name="spaceForm"
      :model="spaceForm"
      @finish="handleSubmit"
      style="margin-bottom: 16px"
    >
      <a-form-item name="spaceName" label="空间名称">
        <a-input v-model:value="spaceForm.spaceName" placeholder="请输入空间名称" allow-clear />
      </a-form-item>
      <a-form-item name="spaceLevel" label="空间级别">
        <a-select
          v-model:value="spaceForm.spaceLevel"
          :options="SPACE_LEVEL_OPTIONS"
          placeholder="请选择空间级别"
          style="min-width: 180px"
          allow-clear
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%" :loading="loading">{{
          route.query?.id ? '修改' : '创建'
        }}</a-button>
      </a-form-item>
    </a-form>
    <!-- 空间级别介绍 -->
    <a-card title="空间级别介绍">
      <a-typography-paragraph>
        * 目前仅支持开通普通版，如需升级空间，请联系
        <a href="#" target="_blank">程序员薯条</a>。
      </a-typography-paragraph>
      <a-typography-paragraph v-for="spaceLevel in spaceLevelList">
        {{ spaceLevel.text }}： 大小 {{ formatSize(spaceLevel.maxSize) }}， 数量
        {{ spaceLevel.maxCount }}
      </a-typography-paragraph>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import type {SpaceAddRequest, SpaceEditRequest, SpaceLevel, SpaceVO} from '@/api/EntityType.ts'
import {addSpace, getSpaceVOById, listSpaceLevel, updateSpace} from '@/api/SpaceAPI.ts'
import {message} from 'ant-design-vue'
import {useRoute, useRouter} from 'vue-router'
import {SPACE_LEVEL_OPTIONS} from '@/constants/space.ts'
import {formatSize} from '@/utils'

const router = useRouter()
const route = useRoute()

const space = ref<SpaceVO>()
const spaceForm = reactive<SpaceAddRequest | SpaceEditRequest>({})
const loading = ref<boolean>(false)
const spaceLevelList = ref<SpaceLevel[]>([])

/**
 * 获取空间级别列表
 */
const fetchSpaceLevelList = async () => {
  const response = await listSpaceLevel()
  if (response.code === 0 && response.data) {
    spaceLevelList.value = response.data
  } else {
    message.error('获取空间级别失败，' + response.message)
  }
}

/**
 * 提交表单
 */
const handleSubmit = async () => {
  loading.value = true
  const spaceId = space.value?.spaceId
  let response
  if (spaceId) {
    // 更新
    response = await updateSpace({
      id: spaceId,
      ...spaceForm,
    })
  } else {
    // 创建
    response = await addSpace({
      ...spaceForm,
    })
  }
  // 操作成功
  if (response.code === 0 && response.data) {
    message.success('操作成功')
    await router.push({
      path: `/space/${response.data}`,
    })
  } else {
    message.error('操作失败，' + response.message)
  }
  loading.value = false
}

/**
 * 获取老数据
 */
const getOldSpace = async () => {
  // 获取到ID
  const id = route.query?.id as string
  if (id) {
    const response = await getSpaceVOById(id)
    if (response.code === 0 && response.data) {
      const data = response.data
      space.value = data
      spaceForm.spaceName = data.spaceName
      spaceForm.spaceLevel = data.spaceLevel
    }
  }
}

onMounted(() => {
  getOldSpace()
  fetchSpaceLevelList()
})
</script>

<style scoped>
#addSpacePage {
  max-width: 720px;
  margin: 0 auto;
}
</style>
