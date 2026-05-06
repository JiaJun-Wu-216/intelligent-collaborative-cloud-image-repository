<template>
  <div class="picture-list">
    <!-- 图片列表 -->
    <a-list
      :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }"
      :data-source="dataList"
      :loading="loading"
    >
      <template #renderItem="{ item: picture }">
        <a-list-item style="padding: 0">
          <!-- 单张图片 -->
          <a-card hoverable @click="doClickPicture(picture)">
            <template #cover>
              <img
                style="height: 180px; object-fit: cover"
                :alt="picture.name"
                :src="picture.thumbnailUrl ?? picture.url"
              />
            </template>
            <a-card-meta :title="picture.name">
              <template #description>
                <a-flex>
                  <a-tag color="green">
                    {{ picture.category ?? '默认' }}
                  </a-tag>
                  <a-tag v-for="tag in picture.tags" :key="tag">
                    {{ tag }}
                  </a-tag>
                </a-flex>
              </template>
            </a-card-meta>
            <template #actions v-if="showOption">
              <a-space @click="(e: any) => doEdit(e, picture)">
                <EditOutlined />
                编辑
              </a-space>
              <a-space @click="(e: any) => doDelete(e, picture)">
                <DeleteOutlined />
                删除
              </a-space>
            </template>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import type {PictureVO} from '@/api/EntityType.ts'
import {DeleteOutlined, EditOutlined} from '@ant-design/icons-vue'
import {useRouter} from 'vue-router'
import {deletePicture} from '@/api/PictureAPI.ts'
import {message} from 'ant-design-vue'

interface Props {
  dataList?: PictureVO[]
  loading?: boolean
  showOption?: boolean
  onReload?: () => void
}

const router = useRouter()

const props = withDefaults(defineProps<Props>(), {
  dataList: () => [],
  loading: false,
  showOption: false,
})

/**
 * 跳转至图片详情页
 * @param picture 图片信息
 */
const doClickPicture = (picture: PictureVO) => {
  router.push({
    path: `/picture/${picture.id}`,
  })
}

// 编辑
const doEdit = (e: any, picture: PictureVO) => {
  // 阻止冒泡
  e.stopPropagation()
  // 跳转时一定要携带 spaceId
  router.push({
    path: '/add-picture',
    query: {
      id: picture.id,
      spaceId: picture.spaceId,
    },
  })
}
// 删除
const doDelete = async (e: any, picture: PictureVO) => {
  // 阻止冒泡
  e.stopPropagation()
  const pictureId = picture.id as unknown as string
  if (!pictureId) {
    return
  }
  const response = await deletePicture({ id: pictureId })
  if (response.code === 0) {
    message.success('删除成功')
    props.onReload?.()
  } else {
    message.error('删除失败')
  }
}
</script>

<style scoped></style>
