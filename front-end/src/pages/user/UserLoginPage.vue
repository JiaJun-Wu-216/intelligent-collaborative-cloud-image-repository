<template>
  <div id="userLoginPage">
    <h2 class="title">智能协同云图库 - 用户登陆</h2>
    <div class="desc">企业级智能协同云图库</div>
    <a-form :model="formState" name="basic" @finish="handleSubmit">
      <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入账号' }]">
        <a-input
          v-model:value="formState.userAccount"
          placeholder="请输入账号"
          autocomplete="username"
        />
      </a-form-item>
      <a-form-item
        name="userPassword"
        :rules="[
          { required: true, message: '请输入密码' },
          { min: 8, message: '密码长度不能小于 8 位' },
        ]"
      >
        <a-input-password
          v-model:value="formState.userPassword"
          placeholder="请输入密码"
          autocomplete="current-password"
        />
      </a-form-item>
      <div class="tips">
        没有账号？
        <router-link to="/user/register">去注册</router-link>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">登陆</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {reactive} from 'vue'
import type {UserLoginRequest} from '@/api/EntityType.ts'
import {userLogin} from '@/api/UserAPI.ts'
import {userLoginUserStore} from '@/stores/userLoginUserStore.ts'
import {message} from 'ant-design-vue'
import router from '@/router'

const formState = reactive<UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

const loginUserStore = userLoginUserStore()

/**
 * 提交表单
 * @param values  表单内容信息
 */
const handleSubmit = async (values: any) => {
  const response = await userLogin(values)
  console.log('登陆响应结果：', response)
  // 登陆成功，把登陆状态保存到全局状态中
  if (response.code === 0 && response.data) {
    await loginUserStore.fetchLoginUser()
    message.success('登陆成功')
    await router.push({
      path: '/',
      replace: true,
    })
  } else {
    message.error('登陆失败：' + response.message)
  }
}
</script>

<style scoped>
#userLoginPage {
  max-width: 360px;
  margin: 0 auto;
}

.title {
  text-align: center;
  margin-bottom: 16px;
}

.desc {
  color: #bbb;
  text-align: center;
  margin-bottom: 16px;
}

.tips {
  text-align: right;
  font-size: 13px;
  color: #bbb;
  margin-bottom: 16px;
}
</style>
