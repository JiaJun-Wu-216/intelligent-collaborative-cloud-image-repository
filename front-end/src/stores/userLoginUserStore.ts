import { defineStore } from 'pinia'
import { ref } from 'vue'

export const userLoginUserStore = defineStore('loginUser', () => {
  const loginUser = ref<any>({
    userName: '未登录',
  })

  /**
   * 设置登陆用户
   * @param newLoginUser
   */
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  async function fetchLoginUser() {
    /*const response = await getCurrentUser()
    if (response.data.code === 0 && response.data.data) {
      loginUser.value = response.data.data
    }*/
    setTimeout(()=>{
      loginUser.value = {
        username:'测试用户',
        id:1
      }
    },3000)
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
