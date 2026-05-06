<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <!-- 头部导航栏 -->
      <a-layout-header class="header">
        <global-header />
      </a-layout-header>
      <a-layout>
        <!-- 侧边栏 -->
        <a-layout-sider
          class="sidebar"
          v-if="userLoginUserStore().loginUser.id"
          breakpoint="lg"
          collapsed-width="0"
        >
          <global-sidebar />
        </a-layout-sider>
        <!-- 内容 -->
        <a-layout-content class="content">
          <router-view />
        </a-layout-content>
      </a-layout>
      <!-- 页底 -->
      <a-layout-footer class="footer"> 智能协同云图库 By WuJiaJun </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalSidebar from '@/components/GlobalSidebar.vue'
import {userLoginUserStore} from '@/stores/userLoginUserStore.ts'
</script>

<style scoped>
#basicLayout .header {
  background: white;
  color: unset;
  margin-bottom: 1px;
  padding-inline: 20px;
}

#basicLayout .sidebar {
  background: #fff;
  border-right: 1px solid #eee;
  padding-top: 28px;
}

#basicLayout .sidebar :deep(.ant-menu-root) {
  border-bottom: none !important;
  border-inline-end: none !important;
}

#basicLayout .content {
  padding: 28px;
  background: linear-gradient(to right, #fefefe, #fff);
  margin-bottom: 28px;
}

#basicLayout .footer {
  background: #efefef;
  padding: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
