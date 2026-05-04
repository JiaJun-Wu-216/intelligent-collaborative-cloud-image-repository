<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="200px">
        <router-link to="/">
          <div class="title-bar">
            <img class="logo" src="@/assets/logo.png" alt="logo" />
            <div class="title">智能协同云图库</div>
          </div>
        </router-link>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />
      </a-col>
      <a-col flex="120px">
        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser?.id">
            <a-dropdown>
              <a-space>
                <a-avatar :src="loginUserStore.loginUser?.userAvatar" />
                {{ loginUserStore.loginUser.username ?? '无名' }}
                <DownOutlined />
              </a-space>
              <template #overlay>
                <a-menu>
                  <a-menu-item @click="doLogout">
                    <a-space>
                      <LogoutOutlined />
                      退出登录
                    </a-space>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div v-else>
            <a-button type="primary" @click="router.push('/user/login')">登陆</a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import {computed, h, ref} from 'vue'
import {DownOutlined, HomeOutlined, LogoutOutlined} from '@ant-design/icons-vue'
import {type MenuProps, message} from 'ant-design-vue'
import {useRouter} from 'vue-router'
import {userLoginUserStore} from '@/stores/userLoginUserStore.ts'
import {userLogout} from '@/api/UserAPI.ts'

const loginUserStore = userLoginUserStore()
const router = useRouter()

// 菜单列表
const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/add-picture',
    label: '创建图片',
    title: '创建图片',
  },
  {
    key: '/admin/user-manage',
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/admin/picture-manage',
    label: '图片管理',
    title: '图片管理',
  },
]

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    const menuKey: string = menu?.key as string
    if (menuKey.startsWith('/admin')) {
      const loginUser = loginUserStore.loginUser
      if (!loginUser || loginUser.userRole !== 'admin') {
        return false
      }
    }
    return true
  })
}

// 展示在菜单的路由数组
const items = computed<MenuProps['items']>(() => filterMenus(originItems))

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

/**
 * 用户退出登录
 */
const doLogout = async () => {
  const response = await userLogout()
  if (response.code === 0) {
    loginUserStore.LoginUserLogout()
    message.success('退出登陆成功')
    await router.push({
      path: '/',
      replace: true,
    })
  } else {
    message.error('退出登录失败：' + response.message)
  }
}
</script>

<style scoped>
#globalHeader .title-bar {
  display: flex;
  align-items: center;
}

.logo {
  height: 48px;
}

.title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
}
</style>
