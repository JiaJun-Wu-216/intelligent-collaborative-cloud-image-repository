import {defineStore} from 'pinia'
import {ref} from 'vue'
import type {LoginUserVO} from '@/api/EntityType.ts'
import {getLoginUser} from '@/api/UserAPI.ts'

export const userLoginUserStore = defineStore('loginUser', () => {
  const loginUser = ref<LoginUserVO>()

  /**
   * 设置登陆用户
   * @param newLoginUser
   */
  function setLoginUser(newLoginUser: LoginUserVO) {
    loginUser.value = newLoginUser
  }

  async function fetchLoginUser() {
    const response = await getLoginUser()
    if (response.code === 0 && response.data) {
      loginUser.value = response.data
    }
  }

  /**
   * 用户退出登录
   */
  function LoginUserLogout(){
    loginUser.value = {
      username: '',
      createTime: '',
      editTime: '',
      id: '',
      inviteUser: '',
      shareCode: '',
      updateTime: '',
      userAccount: '',
      userAvatar: '',
      userProfile: '',
      userRole: '',
      vipCode: '',
      vipExpireTime: '',
      vipNumber: '',
    }
  }

  return { loginUser, setLoginUser, fetchLoginUser, LoginUserLogout }
})
