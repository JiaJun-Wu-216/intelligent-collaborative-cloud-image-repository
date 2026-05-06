<template>
  <div id="globalSidebar">
    <a-menu mode="inline" v-model:selectedKeys="current" :items="menuItems" @click="doMenuClick" />
  </div>
</template>

<script lang="ts" setup>
import {h, ref} from 'vue'
import {PictureOutlined, UserOutlined} from '@ant-design/icons-vue'
import {useRouter} from 'vue-router'

const router = useRouter()

// 菜单列表
const menuItems = [
  {
    key: '/',
    icon: () => h(PictureOutlined),
    label: '公共图库',
  },
  {
    key: '/my-space',
    icon: () => h(UserOutlined),
    label: '我的空间',
  },
]

/**
 * 路由跳转事件
 */
const doMenuClick = ({ key }: any) => {
  const targetPath = key as string
  router.push({
    path: targetPath,
  })
}

/**
 * 当前要高亮的菜单
 */
const current = ref<string[]>(['/'])

/**
 * 监听路由变化，更新高亮菜单项
 */
router.afterEach((to, from, next) => {
  current.value = [to.path]
})
</script>

<style scoped>
#globalSidebar .ant-layout-sider {
  background: none;
}
</style>
