export interface LoginUserVO {
  id: string
  userAccount: string
  username: string
  userAvatar: string
  userProfile: string
  userRole: string
  vipExpireTime: string
  vipCode: string
  vipNumber: string
  shareCode: string
  inviteUser: string
  editTime: string
  createTime: string
  updateTime: string
}

export interface UserVO {
  id: string
  userAccount: string
  username: string
  userAvatar: string
  userProfile: string
  userRole: string
  createTime: string
}

export interface UserLoginRequest {
  userAccount: string
  userPassword: string
}

export interface UserRegisterRequest {
  userAccount: string
  userPassword: string
  checkPassword: string
}

export interface PageRequest {
  current: number
  pageSize: number
  sortField: string
  sortOrder: string
}

export interface UserQueryRequest extends PageRequest {
  id: string
  username: string
  userAccount: string
  userProfile: string
  userRole: string
}

export interface DeleteRequest {
  id: string
}
