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
  id?: number

  /**
   * 空间 ID
   */
  spaceId?: number

  /**
   * 图片 url
   */
  url?: string

  /**
   * 图片名称
   */
  name?: string

  /**
   * 简介
   */
  introduction?: string

  /**
   * 标签
   */
  tags?: string[]

  /**
   * 分类
   */
  category?: string

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
   * 用户 id
   */
  userId?: string

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

  /**
   * 缩略图
   */
  thumbnailUrl?: string

  /**
   * 主色调
   */
  picColor?: string

  /**
   * 权限列表
   */
  permissionList?: string[]
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
  nullSpaceId?: boolean

  /**
   * 开始编辑时间
   */
  startEditTime?: string

  /**
   * 结束编辑时间
   */
  endEditTime?: string
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
   * 空间类型：0-私有 1-团队
   */
  spaceType?: number

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

  /**
   * 权限列表
   */
  permissionList?: string[]
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

  /**
   * 空间类型：0-私有 1-团队
   */
  spaceType?: number
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

  /**
   * 空间类型：0-私有 1-团队
   */
  spaceType?: number
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

/**
 * 图片搜索结果类
 */
export interface ImageSearchResult {
  /**
   * 缩略图地址
   */
  thumbUrl?: string

  /**
   * 来源地址
   */
  fromUrl?: string
}

/**
 * 以图搜图请求类
 */
export interface SearchPictureByPictureRequest {
  /**
   * 图片 id
   */
  pictureId: number
}

/**
 * 根据图片颜色搜索请求类
 */
export interface SearchPictureByColorRequest {
  /**
   * 图片主色调
   */
  picColor?: string

  /**
   * 空间 id
   */
  spaceId?: number
}

/**
 * 批量修改图片信息请求类
 */
export interface PictureEditByBatchRequest {
  /**
   * 图片 id 列表
   */
  pictureIdList?: number[]

  /**
   * 空间 id
   */
  spaceId?: number

  /**
   * 分类
   */
  category?: string

  /**
   * 标签
   */
  tags?: string[]

  /**
   * 命名规则
   */
  nameRule?: string
}

export interface Parameters {
  /**
   * 可选，逆时针旋转角度，默认值 0，取值范围 [0, 359]
   */
  angle?: number

  /**
   * 可选，输出图像的宽高比，默认空字符串，不设置宽高比
   * 可选值：["", "1:1", "3:4", "4:3", "9:16", "16:9"]
   */
  outputRatio?: string

  /**
   * 可选，图像居中，在水平方向上按比例扩展，默认值 1.0，范围 [1.0, 3.0]
   */
  xScale?: number

  /**
   * 可选，图像居中，在垂直方向上按比例扩展，默认值 1.0，范围 [1.0, 3.0]
   */
  yScale?: number

  /**
   * 可选，在图像上方添加像素，默认值 0
   */
  topOffset?: number

  /**
   * 可选，在图像下方添加像素，默认值 0
   */
  bottomOffset?: number

  /**
   * 可选，在图像左侧添加像素，默认值 0
   */
  leftOffset?: number

  /**
   * 可选，在图像右侧添加像素，默认值 0
   */
  rightOffset?: number

  /**
   * 可选，开启图像最佳质量模式，默认值 false
   * 若为 true，耗时会成倍增加
   */
  bestQuality?: boolean

  /**
   * 可选，限制模型生成的图像文件大小，默认值 true
   * - 单边长度 <= 10000：输出图像文件大小限制为 5MB 以下
   * - 单边长度 > 10000：输出图像文件大小限制为 10MB 以下
   */
  limitImageSize?: boolean

  /**
   * 可选，添加 "Generated by AI" 水印，默认值 true
   */
  addWatermark?: boolean
}

/**
 * 创建扩图任务请求类
 */
export interface CreatePictureOutPaintingTaskRequest {
  /**
   * 图片 id
   */
  pictureId?: number

  /**
   * 扩图参数
   */
  parameters?: Parameters
}

export interface CreateOutPaintingTaskResponse {
  /**
   * 接口错误码。
   * <p>接口成功请求不会返回该参数。</p>
   */
  code?: string

  /**
   * 接口错误信息。
   * <p>接口成功请求不会返回该参数。</p>
   */
  message?: string

  /**
   * 请求唯一标识。
   * <p>可用于请求明细溯源和问题排查。</p>
   */
  requestId?: string

  output?: {
    /**
     * 任务 ID
     */
    taskId: string

    /**
     * 任务状态
     * <ul>
     *     <li>PENDING：排队中</li>
     *     <li>RUNNING：处理中</li>
     *     <li>SUSPENDED：挂起</li>
     *     <li>SUCCEEDED：执行成功</li>
     *     <li>FAILED：执行失败</li>
     *     <li>UNKNOWN：任务不存在或状态未知</li>
     * </ul>
     */
    taskStatus?: string
  }
}

export interface GetOutPaintingTaskResponse {
  /**
   * 请求唯一标识
   */
  requestId?: string

  /**
   * 输出信息
   */
  output?: {
    /**
     * 任务 ID
     */
    taskId?: string

    /**
     * 任务状态
     * <ul>
     *     <li>PENDING：排队中</li>
     *     <li>RUNNING：处理中</li>
     *     <li>SUSPENDED：挂起</li>
     *     <li>SUCCEEDED：执行成功</li>
     *     <li>FAILED：执行失败</li>
     *     <li>UNKNOWN：任务不存在或状态未知</li>
     * </ul>
     */
    taskStatus?: string

    /**
     * 提交时间
     * 格式：YYYY-MM-DD HH:mm:ss.SSS
     */
    submitTime?: string

    /**
     * 调度时间
     * 格式：YYYY-MM-DD HH:mm:ss.SSS
     */
    scheduledTime?: string

    /**
     * 结束时间
     * 格式：YYYY-MM-DD HH:mm:ss.SSS
     */
    endTime?: string

    /**
     * 输出图像的 URL
     */
    outputImageUrl?: string

    /**
     * 接口错误码
     * <p>接口成功请求不会返回该参数</p>
     */
    code?: string

    /**
     * 接口错误信息
     * <p>接口成功请求不会返回该参数</p>
     */
    message?: string

    /**
     * 任务指标信息
     */
    taskMetrics?: {
      /**
       * 总任务数
       */
      total?: number

      /**
       * 成功任务数
       */
      succeeded?: number

      /**
       * 失败任务数
       */
      failed?: number
    }
  }
}

/**
 * 空间资源使用分析响应类
 */
export interface SpaceUsageAnalyzeResponse {
  /**
   * 已使用大小
   */
  usedSize?: number

  /**
   * 总大小
   */
  maxSize?: number

  /**
   * 空间使用比例
   */
  sizeUsageRatio?: number

  /**
   * 当前图片数量
   */
  usedCount?: number

  /**
   * 最大图片数量
   */
  maxCount?: number

  /**
   * 图片数量占比
   */
  countUsageRatio?: number
}

/**
 * 通用空间分析请求类
 */
export interface SpaceAnalyzeRequest {
  /**
   * 空间 ID
   */
  spaceId?: number

  /**
   * 是否查询公共图库
   */
  queryPublic?: boolean

  /**
   * 全空间分析
   */
  queryAll?: boolean
}

/**
 * 空间资源使用分析请求类
 */
export interface SpaceUsageAnalyzeRequest extends SpaceAnalyzeRequest {}

/**
 * 空间图片分类分析响应类
 */
export interface SpaceCategoryAnalyzeResponse {
  /**
   * 图片分类
   */
  category?: string

  /**
   * 图片数量
   */
  count?: number

  /**
   * 分类图片总大小
   */
  totalSize?: number
}

/**
 * 空间图片分类分析请求封装类
 */
export interface SpaceCategoryAnalyzeRequest extends SpaceAnalyzeRequest {}

/**
 * 空间图片标签分析封装类
 */
export interface SpaceTagAnalyzeRequest extends SpaceAnalyzeRequest {}

/**
 * 空间图片标签分析响应类
 */
export interface SpaceTagAnalyzeResponse {
  /**
   * 标签名称
   */
  tag?: string

  /**
   * 使用次数
   */
  count?: number
}

/**
 * 空间图片大小分析响应类
 */
export interface SpaceSizeAnalyzeResponse {
  /**
   * 图片大小范围
   */
  sizeRange?: string

  /**
   * 图片数量
   */
  count?: number
}

/**
 * 空间图片大小分析请求类
 */
export interface SpaceSizeAnalyzeRequest extends SpaceAnalyzeRequest {}

/**
 * 用户上传行为分析响应类
 */
export interface SpaceUserAnalyzeResponse {
  /**
   * 时间区间
   */
  period?: string

  /**
   * 上传数量
   */
  count?: number
}

/**
 * 用户上传行为分析请求类
 */
export interface SpaceUserAnalyzeRequest extends SpaceAnalyzeRequest {
  /**
   * 用户 ID
   */
  userId?: number

  /**
   * 时间维度：day / week / month
   */
  timeDimension?: string
}

/**
 * 空间实体类
 */
export interface Space {
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
   * 是否删除
   */
  isDelete?: number
}

/**
 * 空间使用排行分析请求类
 */
export interface SpaceRankAnalyzeRequest extends SpaceAnalyzeRequest {
  /**
   * 排名前 N 的空间
   */
  topN?: number
}

/**
 * 空间用户封装类
 */
export interface SpaceUserVO {
  /**
   * id
   */
  id?: number

  /**
   * 空间 id
   */
  spaceId?: number

  /**
   * 用户 id
   */
  userId?: number

  /**
   * 空间角色：viewer/editor/admin
   */
  spaceRole?: string

  /**
   * 创建时间
   */
  createTime?: string

  /**
   * 更新时间
   */
  updateTime?: string

  /**
   * 用户信息
   */
  user?: UserVO

  /**
   * 空间信息
   */
  space?: SpaceVO
}

/**
 * 查询空间成员请求类
 */
export interface SpaceUserQueryRequest {
  /**
   * ID
   */
  id?: number

  /**
   * 空间 ID
   */
  spaceId?: number

  /**
   * 用户 ID
   */
  userId?: number

  /**
   * 空间角色：viewer/editor/admin
   */
  spaceRole?: string
}

/**
 * 编辑空间成员请求类
 */
export interface SpaceUserEditRequest {
  /**
   * id
   */
  id?: number

  /**
   * 空间角色：viewer/editor/admin
   */
  spaceRole?: string
}

/**
 * 添加空间成员请求类
 */
export interface SpaceUserAddRequest {
  /**
   * 空间 ID
   */
  spaceId?: number

  /**
   * 用户 ID
   */
  userId?: number

  /**
   * 空间角色：viewer/editor/admin
   */
  spaceRole?: string
}
