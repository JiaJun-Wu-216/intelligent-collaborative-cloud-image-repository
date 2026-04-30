import {request} from '@/request/index.ts'
import type {
  DeleteRequest,
  UserLoginRequest,
  UserQueryRequest,
  UserRegisterRequest,
} from '@/api/EntityType.ts'
import type {
  ResponseAny,
  ResponseBoolean,
  ResponseLoginUserVO,
  ResponseString,
} from '@/api/ResponseType.ts'

/**
 * 用户注册
 * @param userRegisterRequest 注册信息
 */
export const userRegister = (userRegisterRequest: UserRegisterRequest): Promise<ResponseString> => {
  return request.post('/user/register', userRegisterRequest)
}

/**
 * 用户登陆
 * @param userLoginRequest  登陆信息
 */
export const userLogin = (userLoginRequest: UserLoginRequest): Promise<ResponseLoginUserVO> => {
  return request.post('/user/login', userLoginRequest)
}

/**
 * 获取当前登录用户信息
 */
export const getLoginUser = (): Promise<ResponseLoginUserVO> => {
  return request.get('/user/get/current-user')
}

/**
 * 用户退出登录
 */
export const userLogout = (): Promise<ResponseBoolean> => {
  return request.post('/user/logout')
}

/**
 * 获取分页用户封装列表（仅管理员）
 * @param userQueryRequest  查询请求参数
 */
export const listUserVOByPage = (userQueryRequest: UserQueryRequest): Promise<ResponseAny> => {
  return request.post('/user/list/page/user-vo', userQueryRequest)
}

/**
 * 删除用户
 * @param deleteRequest 删除请求信息
 */
export const deleteUser = (deleteRequest: DeleteRequest): Promise<ResponseBoolean> => {
  return request.post('/user/delete', deleteRequest)
}
