<template>
  <div id="mySpacePage">
    <p>正在跳转，请稍等......</p>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'
import {userLoginUserStore} from '@/stores/userLoginUserStore.ts'
import {listSpaceVOByPage} from '@/api/SpaceAPI.ts'
import {message} from 'ant-design-vue'
import {onMounted} from 'vue'

const router = useRouter()
const loginUserStore = userLoginUserStore()

const checkUserSpace = async () => {
  // 1.用户是否登录，未登录直接跳转至登录页面
  const loginUser = loginUserStore.loginUser
  if (!loginUser?.id) {
    await router.replace('/user/login')
    return
  }
  // 2.用户已登录，会获取该用户已创建的空间
  const response = await listSpaceVOByPage({
    userId: loginUser.id,
    current: 1,
    pageSize: 1,
  })
  if (response.code === 0) {
    // 判断是否有已创建的空间
    if (response.data?.records?.length > 0) {
      // 有则跳转至已创建的空间
      const space = response.data.records[0]
      await router.replace(`/space/${space.id}`)
    } else {
      // 没有则跳转至创建空间
      await router.replace('/add-space')
      message.warning('请先创建空间')
    }
  } else {
    message.error('加载我的空间失败，' + response.message)
  }
}

onMounted(()=>{
  checkUserSpace()
})
</script>
