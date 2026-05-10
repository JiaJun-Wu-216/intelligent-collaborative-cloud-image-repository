<template>
  <div id="addPicturePage">
    <h2 style="margin-bottom: 16px">
      {{ route.query?.id ? '修改图片' : '创建图片' }}
    </h2>
    <a-typography-paragraph v-if="spaceId" type="secondary">
      保存至空间：<a :href="`/space/${spaceId}`" target="_blank">{{ spaceId }}</a>
    </a-typography-paragraph>
    <!-- 选择上传方式 -->
    <a-tabs v-model:activeKey="uploadType">
      <a-tab-pane key="file" tab="文件上传">
        <!-- 图片上传组件 -->
        <picture-upload :picture="picture" :spaceId="spaceId" :on-success="onSuccess" />
      </a-tab-pane>
      <a-tab-pane key="url" tab="URL 上传" force-render>
        <!-- URL 图片上传组件 -->
        <url-picture-upload :picture="picture" :spaceId="spaceId" :on-success="onSuccess" />
      </a-tab-pane>
    </a-tabs>
    <!-- 图片编辑区域 -->
    <div v-if="picture" class="edit-bar">
      <a-space size="middle">
        <a-button :icon="h(EditOutlined)" @click="doEditPicture">编辑图片</a-button>
        <a-button type="primary" :icon="h(FullscreenOutlined)" @click="doImagePainting"
          >AI 扩图</a-button
        >
      </a-space>
    </div>
    <ImageCropper
      ref="imageCropperRef"
      :imageUrl="picture?.url"
      :picture="picture"
      :spaceId="spaceId"
      :space="space"
      :onSuccess="onCropSuccess"
    />
    <ImageOutPainting
      ref="imageOutPaintingRef"
      :picture="picture"
      :spaceId="spaceId"
      :onSuccess="onImageOutPaintingSuccess"
    />
    <!-- 图片信息表单 -->
    <a-form
      layout="vertical"
      name="pictureForm"
      :model="pictureForm"
      @finish="handleSubmit"
      style="margin-bottom: 16px"
      v-if="picture"
    >
      <a-form-item name="name" label="图片名称">
        <a-input v-model:value="pictureForm.name" placeholder="输入图片名称" allow-clear />
      </a-form-item>
      <a-form-item name="introduction" label="图片简介">
        <a-textarea
          v-model:value="pictureForm.introduction"
          placeholder="输入图片简介"
          :auto-size="{ minRows: 2, maxRows: 5 }"
          allow-clear
          :rows="2"
        />
      </a-form-item>
      <a-form-item name="category" label="图片分类">
        <a-auto-complete
          v-model:value="pictureForm.category"
          placeholder="请输入分类"
          :options="categoryOptions"
          allow-clear
        />
      </a-form-item>
      <a-form-item name="tags" label="图片标签">
        <a-select
          v-model:value="pictureForm.tags"
          mode="tags"
          :options="tagOptions"
          placeholder="请输入标签"
          allow-clear
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">{{
          route.query?.id ? '修改' : '创建'
        }}</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import PictureUpload from '@/components/PictureUpload.vue'
import {computed, h, onMounted, reactive, ref, watchEffect} from 'vue'
import type {PictureEditRequest, PictureVO, SpaceVO} from '@/api/EntityType.ts'
import {editPicture, getPictureVOById, getTagCategory} from '@/api/PictureAPI.ts'
import {message} from 'ant-design-vue'
import {useRoute, useRouter} from 'vue-router'
import UrlPictureUpload from '@/components/UrlPictureUpload.vue'
import ImageCropper from '@/components/ImageCropper.vue'
import {EditOutlined, FullscreenOutlined} from '@ant-design/icons-vue'
import ImageOutPainting from '@/components/ImageOutPainting.vue'
import {getSpaceVOById} from '@/api/SpaceAPI.ts'

const router = useRouter()
const route = useRoute()

const uploadType = ref<'file' | 'url'>('file')

const picture = ref<PictureVO>()
const pictureForm = reactive<PictureEditRequest>({})

// 空间 ID
const spaceId = computed(() => {
  return route.query?.spaceId
})

/**
 * 图片上传成功回调
 * @param newPicture  图片信息
 */
const onSuccess = (newPicture: PictureVO) => {
  picture.value = newPicture
  pictureForm.name = newPicture.name
}

/**
 * 提交表单
 * @param values  表单内容信息
 */
const handleSubmit = async (values: any) => {
  const pictureId = picture.value?.id
  if (!pictureId) {
    return
  }
  const response = await editPicture({
    id: pictureId,
    spaceId: spaceId.value,
    ...values,
  })
  // 操作成功
  if (response.code === 0 && response.data) {
    message.success('创建成功')
    await router.push({
      path: `/picture/${pictureId}`,
    })
  } else {
    message.error('创建失败，' + response.message)
  }
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

/**
 * 获取老数据
 */
const getOldPicture = async () => {
  // 获取到ID
  const id = route.query?.id as string
  if (id) {
    const response = await getPictureVOById(id)
    if (response.code === 0 && response.data) {
      const data = response.data
      picture.value = data
      pictureForm.name = data.name
      pictureForm.introduction = data.introduction
      pictureForm.category = data.category
      pictureForm.tags = data.tags
    }
  }
}

// -------------- 图片编辑器引用 --------------
const imageCropperRef = ref()

// 编辑图片
const doEditPicture = () => {
  if (imageCropperRef.value) {
    imageCropperRef.value?.openModal()
  }
}

// 编辑成功事件
const onCropSuccess = (newPicture: PictureVO) => {
  picture.value = newPicture
  console.log(picture.value)
}

// -------------- AI 扩图引用 --------------
const imageOutPaintingRef = ref()

// 打开 AI 扩图弹窗
const doImagePainting = () => {
  if (imageOutPaintingRef.value) {
    imageOutPaintingRef.value?.openModal()
  }
}

// AI 扩图成功保存事件
const onImageOutPaintingSuccess = (newPicture: PictureVO) => {
  picture.value = newPicture
  console.log(picture.value)
}

const space = ref<SpaceVO>()

// 获取空间信息
const fetchSpace = async () => {
  // 获取数据
  if (spaceId.value) {
    const response = await getSpaceVOById(spaceId.value as string)
    if (response.code === 0 && response.data) {
      space.value = response.data
    }
  }
}

watchEffect(() => {
  fetchSpace()
})

onMounted(() => {
  getTagCategoryOptions()
  getOldPicture()
})
</script>

<style scoped>
#addPicturePage {
  max-width: 720px;
  margin: 0 auto;
}

#addPicturePage .edit-bar {
  text-align: center;
  margin: 16px 0;
}
</style>
