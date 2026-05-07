<template>
  <div id="searchPicturePage">
    <h2 style="margin-bottom: 16px">以图搜图</h2>
    <h3 style="margin-bottom: 16px">原图</h3>
    <!-- 展示图片 -->
    <a-card hoverable style="width: 240px">
      <template #cover>
        <img
          style="height: 180px; object-fit: cover"
          :alt="picture.name"
          :src="picture.thumbnailUrl ?? picture.url"
        />
      </template>
    </a-card>
    <h3 style="margin: 16px 0">识图结果</h3>
    <!-- 图片结果列表 -->
    <a-list
      :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }"
      :data-source="dataList"
      :loading="loading"
    >
      <template #renderItem="{ item: picture }">
        <a-list-item style="padding: 0">
          <a :href="picture.fromUrl" target="_blank"
            ><!-- 单张图片 -->
            <a-card hoverable>
              <template #cover>
                <img
                  style="height: 180px; object-fit: cover"
                  :alt="picture.name"
                  :src="picture.thumbUrl"
                />
              </template> </a-card
          ></a>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import type {ImageSearchResult, PictureVO} from '@/api/EntityType.ts'
import {message} from 'ant-design-vue'
import {getPictureVOById, searchPictureByPicture} from '@/api/PictureAPI.ts'
import {useRoute} from 'vue-router'

const route = useRoute()

const picture = ref<PictureVO>({})
const pictureId = computed(() => {
  return route.query?.pictureId
})

// 获取图片详情
const fetchPictureDetail = async () => {
  try {
    const response = await getPictureVOById(pictureId.value as unknown as string)
    if (response.code === 0 && response.data) {
      picture.value = response.data
    } else {
      message.error('获取图片详情失败，' + response.message)
    }
  } catch (e: any) {
    message.error('获取图片详情失败：' + e.message)
  }
}

const dataList = ref<ImageSearchResult[]>([])
const loading = ref<boolean>(false)
const fetchSearchPictureResult = async () => {
  loading.value = true
  try {
    const response = await searchPictureByPicture({
      pictureId: pictureId.value as unknown as number,
    })
    if (response.code === 0 && response.data) {
      dataList.value = response.data
    } else {
      message.error('获取数据失败，' + response.message)
    }
  } catch (e: any) {
    message.error('获取数据失败：' + e.message)
  }
  loading.value = false
}

onMounted(() => {
  fetchPictureDetail()
  fetchSearchPictureResult()
})
</script>

<style scoped></style>
