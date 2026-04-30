<template>
  <div id="userRegisterPage">
    <h2 class="title">智能协同云图库 - 用户注册</h2>
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
      <a-form-item
        name="checkPassword"
        :rules="[
          { required: true, message: '请输入确认密码' },
          { min: 8, message: '确认密码长度不能小于 8 位' },
        ]"
      >
        <a-input-password
          v-model:value="formState.checkPassword"
          placeholder="请输入确认密码"
          autocomplete="current-password"
        />
      </a-form-item>
      <div class="tips">
        已有账号？
        <router-link to="/user/login">去登陆</router-link>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">注册</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {reactive} from 'vue'
import type {UserRegisterRequest} from '@/api/EntityType.ts'
import {userRegister} from '@/api/UserAPI.ts'
import {message} from 'ant-design-vue'
import router from '@/router'

const formState = reactive<UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

/**
 * 提交表单
 * @param values  表单内容信息
 */
const handleSubmit = async (values: any) => {
  if (values.userPassword !== values.checkPassword) {
    message.error('两次输入的密码不一致')
    return
  }
  const response = await userRegister(values)
  console.log('注册响应结果：', response)
  // 注册成功，跳转到登录页
  if (response.code === 0 && response.data) {
    message.success('注册成功')
    await router.push({
      path: '/user/login',
      replace: true,
    })
  } else {
    message.error('注册失败：' + response.message)
  }
}
</script>

<style scoped>
#userRegisterPage {
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
