import {createRouter, createWebHistory} from 'vue-router'
import {ACCESS_ENUM} from '@/access/accessEnum.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/pages/HomePage.vue'),
    },
    {
      path: '/user/login',
      name: 'userLogin',
      component: () => import('@/pages/user/UserLoginPage.vue'),
    },
    {
      path: '/user/register',
      name: 'userRegister',
      component: () => import('@/pages/user/UserRegisterPage.vue'),
    },
    {
      path: '/add-picture',
      name: 'addPicture',
      component: () => import('@/pages/AddPicturePage.vue'),
    },
    {
      path: '/add-picture/batch',
      name: 'addPictureBatch',
      component: () => import('@/pages/AddPictureBatchPage.vue'),
    },
    {
      path: '/picture/:id',
      name: 'pictureDetail',
      props: true,
      component: () => import('@/pages/PictureDetailPage.vue'),
    },
    {
      path: '/add-space',
      name: 'addSpace',
      component: () => import('@/pages/AddSpacePage.vue'),
    },
    {
      path: '/my-space',
      name: 'mySpace',
      component: () => import('@/pages/MySpacePage.vue'),
    },
    {
      path: '/space/:id',
      name: 'spaceDetail',
      props: true,
      component: () => import('@/pages/SpaceDetailPage.vue'),
    },
    {
      path: '/admin/user-manage',
      name: 'userManage',
      component: () => import('@/pages/admin/UserManagePage.vue'),
      meta: {
        access: ACCESS_ENUM.ADMIN,
      },
    },
    {
      path: '/admin/picture-manage',
      name: 'pictureManage',
      component: () => import('@/pages/admin/PictureManagePage.vue'),
      meta: {
        access: ACCESS_ENUM.ADMIN,
      },
    },
    {
      path: '/admin/space-manage',
      name: 'spaceManage',
      component: () => import('@/pages/admin/SpaceManagePage.vue'),
      meta: {
        access: ACCESS_ENUM.ADMIN,
      },
    },
  ],
})

export default router
