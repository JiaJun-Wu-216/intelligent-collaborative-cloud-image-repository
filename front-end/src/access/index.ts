import router from '@/router/index.ts'
import {userLoginUserStore} from '@/stores/userLoginUserStore.ts'
import {ACCESS_ENUM} from '@/access/accessEnum.ts'
import {message} from 'ant-design-vue'
import {checkAccess} from '@/access/checkAccess.ts'

/**
 * 全局权限校验，每次切换页面都会执行
 */
router.beforeEach(async (to, from) => {
  const loginUserStore = userLoginUserStore()
  let loginUser = loginUserStore.loginUser
  console.log('登陆用户信息', loginUser, to.fullPath)
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN
  // 要跳转的页面必须登陆
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // 如果之前没登陆过，自动登录
    if (!loginUser || !loginUser.userRole) {
      // 加 await 是为了等用户登录成功之后，再执行后续的代码
      console.log('获取用户登陆信息')
      await loginUserStore.fetchLoginUser()
      loginUser = loginUserStore.loginUser
    }
    // 如果没登录，跳转到登陆页面
    if (!loginUser || !loginUser.userRole || loginUser.userRole === ACCESS_ENUM.NOT_LOGIN) {
      message.warning('未登录，请登陆后再进行操作')
      return `/user/login?redirect=${encodeURIComponent(to.fullPath)}`
    }
    // 如果已登录，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      message.error('无权限进行此操作')
      return '/noAuth'
    }
  }
})
