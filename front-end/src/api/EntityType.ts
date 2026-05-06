export interface LoginUserVO {
  id?: number
  userAccount?: string
  username?: string
  userAvatar?: string
  userProfile?: string
  userRole?: string
  vipExpireTime?: string
  vipCode?: string
  vipNumber?: string
  shareCode?: string
  inviteUser?: string
  editTime?: string
  createTime?: string
  updateTime?: string
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
  current?: number
  pageSize?: number
  sortField?: string
  sortOrder?: string
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

export interface PictureVO {
  /**
   * id
   */
  id: number

  /**
   * 空间 ID
   */
  spaceId: number

  /**
   * 图片 url
   */
  url: string

  /**
   * 图片名称
   */
  name: string

  /**
   * 简介
   */
  introduction: string

  /**
   * 标签
   */
  tags: string[]

  /**
   * 分类
   */
  category: string

  /**
   * 文件体积
   */
  picSize: number

  /**
   * 图片宽度
   */
  picWidth: number

  /**
   * 图片高度
   */
  picHeight: number

  /**
   * 图片比例
   */
  picScale: number

  /**
   * 图片格式
   */
  picFormat: string

  /**
   * 用户 id
   */
  userId: string

  /**
   * 创建时间
   */
  createTime: string

  /**
   * 编辑时间
   */
  editTime: string

  /**
   * 更新时间
   */
  updateTime: string

  /**
   * 创建用户信息
   */
  user: UserVO
}

/**
 * 图片上传请求类
 */
export interface PictureUploadRequest {
  /**
   * 图片主键（用于修改）
   */
  id?: number

  /**
   * 图片地址
   */
  fileUrl?: string

  /**
   * 图片名称
   */
  picName?: string

  /**
   * 空间主键
   */
  spaceId?: number
}

/**
 * 图片批量上传请求类
 */
export interface PictureUploadByBatchRequest {
  /**
   * 搜索词
   */
  searchText?: string

  /**
   * 抓取数量
   */
  count?: number

  /**
   * 图片名称前缀
   */
  namePrefix?: string
}

export interface PictureEditRequest {
  /**
   * id
   */
  id: number

  /**
   * 图片名称
   */
  name: string

  /**
   * 简介
   */
  introduction: string

  /**
   * 分类
   */
  category: string

  /**
   * 标签
   */
  tags: string[]
}

/**
 * 图片标签分类列表视图
 */
export interface PictureTagCategory {
  /**
   * 标签列表
   */
  tagList: string[]

  /**
   * 分类列表
   */
  categoryList: string[]
}

/**
 * 图片查询请求类
 */
export interface PictureQueryRequest extends PageRequest {
  /**
   * id
   */
  id?: number

  /**
   * 图片名称
   */
  name?: string

  /**
   * 简介
   */
  introduction?: string

  /**
   * 分类
   */
  category?: string

  /**
   * 标签
   */
  tags?: string[]

  /**
   * 文件体积
   */
  picSize?: number

  /**
   * 图片宽度
   */
  picWidth?: number

  /**
   * 图片高度
   */
  picHeight?: number

  /**
   * 图片比例
   */
  picScale?: number

  /**
   * 图片格式
   */
  picFormat?: string

  /**
   * 搜索词（同时搜名称、简介等）
   */
  searchText?: string

  /**
   * 用户 id
   */
  userId?: number

  /**
   * 状态：0-待审核; 1-通过; 2-拒绝
   */
  reviewStatus?: number

  /**
   * 审核信息
   */
  reviewMessage?: string

  /**
   * 审核人 id
   */
  reviewerId?: number

  /**
   * 审核时间
   */
  reviewTime?: string

  /**
   * 是否只查询 spaceId 为 null 的数据
   */
  nullSpaceId?:boolean
}

/**
 * 图片实体类
 */
export interface Picture {
  /**
   * id
   */
  id: number

  /**
   * 图片 url
   */
  url: string

  /**
   * 图片名称
   */
  name: string

  /**
   * 简介
   */
  introduction: string

  /**
   * 分类
   */
  category: string

  /**
   * 标签（JSON 数组）
   */
  tags: string

  /**
   * 图片体积
   */
  picSize: number

  /**
   * 图片宽度
   */
  picWidth: number

  /**
   * 图片高度
   */
  picHeight: number

  /**
   * 图片宽高比例
   */
  picScale: number

  /**
   * 图片格式
   */
  picFormat: string

  /**
   * 创建用户 id
   */
  userId: number

  /**
   * 创建时间
   */
  createTime: string

  /**
   * 编辑时间
   */
  editTime: string

  /**
   * 更新时间
   */
  updateTime: string

  /**
   * 是否删除
   */
  isDelete: number
}

/**
 * 图片审核请求类
 */
export interface PictureReviewRequest {
  /**
   * 图片主键
   */
  id: number

  /**
   * 状态：0-待审核, 1-通过, 2-拒绝
   */
  reviewStatus: number

  /**
   * 审核信息
   */
  reviewMessage: string
}

/**
 * 空间编辑请求类
 */
export interface SpaceEditRequest {
  /**
   * 空间 id
   */
  id?: number

  /**
   * 空间名称
   */
  spaceName?: string
}

/**
 * 空间视图包装类
 */
export interface SpaceVO {
  /**
   * id
   */
  id?: number

  /**
   * 空间名称
   */
  spaceName?: string

  /**
   * 空间级别：0-普通版 1-专业版 2-旗舰版
   */
  spaceLevel?: number

  /**
   * 空间图片的最大总大小
   */
  maxSize?: number

  /**
   * 空间图片的最大数量
   */
  maxCount?: number

  /**
   * 当前空间下图片的总大小
   */
  totalSize?: number

  /**
   * 当前空间下的图片数量
   */
  totalCount?: number

  /**
   * 创建用户 id
   */
  userId?: number

  /**
   * 空间 id
   */
  spaceId?: number

  /**
   * 创建时间
   */
  createTime?: string

  /**
   * 编辑时间
   */
  editTime?: string

  /**
   * 更新时间
   */
  updateTime?: string

  /**
   * 创建用户信息
   */
  user?: UserVO
}

/**
 * 空间查询请求类
 */
export interface SpaceQueryRequest extends PageRequest {
  /**
   * id
   */
  id?: number

  /**
   * 用户 id
   */
  userId?: number

  /**
   * 空间级别：0-普通版 1-专业版 2-旗舰版
   */
  spaceLevel?: number

  /**
   * 空间名称
   */
  spaceName?: string
}

/**
 * 空间创建请求类
 */
export interface SpaceAddRequest {
  /**
   * 空间名称
   */
  spaceName?: string

  /**
   * 空间级别：0-普通版 1-专业版 2-旗舰版
   */
  spaceLevel?: number
}

/**
 * 空间级别
 */
export interface SpaceLevel {
  /**
   * 值
   */
  value?: number

  /**
   * 描述
   */
  text?: string

  /**
   * 最大数量
   */
  maxCount?: number

  /**
   * 最大容量
   */
  maxSize?: number
}

export interface SpaceUpdateRequest {
  /**
   * id
   */
  id?: number

  /**
   * 空间名称
   */
  spaceName?: string

  /**
   * 空间级别：0-普通版 1-专业版 2-旗舰版
   */
  spaceLevel?: number

  /**
   * 空间图片的最大总大小
   */
  maxSize?: number

  /**
   * 空间图片的最大数量
   */
  maxCount?: number
}
