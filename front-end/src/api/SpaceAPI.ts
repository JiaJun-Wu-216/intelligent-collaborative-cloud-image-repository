import {request} from '@/request'
import type {
  ResponseAny,
  ResponseBoolean,
  ResponseNumber,
  ResponseSpace,
  ResponseSpaceCategoryAnalyzeResponse,
  ResponseSpaceLevelList,
  ResponseSpaceSizeAnalyzeResponse,
  ResponseSpaceTagAnalyzeResponse,
  ResponseSpaceUsageAnalyzeResponse,
  ResponseSpaceUserAnalyzeResponse,
  ResponseSpaceVO,
} from '@/api/ResponseType.ts'
import type {
  DeleteRequest,
  SpaceAddRequest,
  SpaceCategoryAnalyzeRequest,
  SpaceEditRequest,
  SpaceQueryRequest,
  SpaceRankAnalyzeRequest,
  SpaceSizeAnalyzeRequest,
  SpaceTagAnalyzeRequest,
  SpaceUpdateRequest,
  SpaceUsageAnalyzeRequest,
  SpaceUserAnalyzeRequest,
} from '@/api/EntityType.ts'

/**
 * 创建空间
 * @param spaceAddRequest 创建空间请求信息
 */
export const addSpace = (spaceAddRequest: SpaceAddRequest): Promise<ResponseNumber> => {
  return request.post('/space/add', spaceAddRequest)
}

/**
 * 更新空间（仅管理员可用）
 * @param spaceUpdateRequest 空间更新请求信息
 */
export const updateSpace = (spaceUpdateRequest: SpaceUpdateRequest): Promise<ResponseBoolean> => {
  return request.post('/space/upload', spaceUpdateRequest)
}

/**
 * 编辑空间
 * @param spaceEditRequest  编辑空间请求信息
 */
export const editSpace = (spaceEditRequest: SpaceEditRequest): Promise<ResponseBoolean> => {
  return request.post('/space/edit', spaceEditRequest)
}

/**
 * 根据空间主键获取空间（封装类）
 * @param id  空间主键
 */
export const getSpaceVOById = (id: string): Promise<ResponseSpaceVO> => {
  return request.get('/space/get/vo', {
    params: {
      id,
    },
  })
}

/**
 * 分页获取空间列表（仅管理员可用）
 * @param spaceQueryRequest 空间查询请求信息
 */
export const listSpaceByPage = (spaceQueryRequest: SpaceQueryRequest): Promise<ResponseAny> => {
  return request.post('/space/list/page', spaceQueryRequest)
}

/**
 * 分页获取空间列表（封装类）
 * @param spaceQueryRequest 空间查询请求信息
 */
export const listSpaceVOByPage = (spaceQueryRequest: SpaceQueryRequest): Promise<ResponseAny> => {
  return request.post('/space/list/page/vo', spaceQueryRequest)
}

/**
 * 删除空间
 * @param deleteRequest 删除请求信息
 */
export const deleteSpace = (deleteRequest: DeleteRequest): Promise<ResponseBoolean> => {
  return request.post('/space/delete', deleteRequest)
}

/**
 * 获取空间级别列表
 */
export const listSpaceLevel = (): Promise<ResponseSpaceLevelList> => {
  return request.get('/space/list/level')
}

/**
 * 获取空间的使用状态
 * @param spaceUsageAnalyzeRequest  空间资源使用分析请求信息
 */
export const getSpaceUsageAnalyze = (
  spaceUsageAnalyzeRequest: SpaceUsageAnalyzeRequest,
): Promise<ResponseSpaceUsageAnalyzeResponse> => {
  return request.post('/space/analyze/usage', spaceUsageAnalyzeRequest)
}

/**
 * 获取空间图片分类分析
 * @param spaceCategoryAnalyzeRequest 空间图片分类分析请求信息
 */
export const getSpaceCategoryAnalyze = (
  spaceCategoryAnalyzeRequest: SpaceCategoryAnalyzeRequest,
): Promise<ResponseSpaceCategoryAnalyzeResponse> => {
  return request.post('/space/analyze/category', spaceCategoryAnalyzeRequest)
}

/**
 * 获取空间图片标签分析
 * @param spaceTagAnalyzeRequest 空间图片标签分析请求信息
 */
export const getSpaceTagAnalyze = (
  spaceTagAnalyzeRequest: SpaceTagAnalyzeRequest,
): Promise<ResponseSpaceTagAnalyzeResponse> => {
  return request.post('/space/analyze/tag', spaceTagAnalyzeRequest)
}

/**
 * 获取空间图片大小分析
 * @param spaceSizeAnalyzeRequest 空间图片大小分析请求信息
 */
export const getSpaceSizeAnalyze = (
  spaceSizeAnalyzeRequest: SpaceSizeAnalyzeRequest,
): Promise<ResponseSpaceSizeAnalyzeResponse> => {
  return request.post('/space/analyze/size', spaceSizeAnalyzeRequest)
}

/**
 * 获取空间用户上传行为分析
 * @param spaceUserAnalyzeRequest 用户上传行为分析请求信息
 */
export const getSpaceUserAnalyze = (
  spaceUserAnalyzeRequest: SpaceUserAnalyzeRequest,
): Promise<ResponseSpaceUserAnalyzeResponse> => {
  return request.post('/space/analyze/user', spaceUserAnalyzeRequest)
}

/**
 * 获取空间使用排行分析
 * @param spaceRankAnalyzeRequest 空间使用排行分析请求信息
 */
export const getSpaceRankAnalyze = (
  spaceRankAnalyzeRequest: SpaceRankAnalyzeRequest,
): Promise<ResponseSpace> => {
  return request.post('/space/analyze/rank', spaceRankAnalyzeRequest)
}
