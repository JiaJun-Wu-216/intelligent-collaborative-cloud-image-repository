import {request} from '@/request'
import type {
  ResponseAny,
  ResponseBoolean,
  ResponseNumber,
  ResponseSpaceLevelList,
  ResponseSpaceVO,
} from '@/api/ResponseType.ts'
import type {
  DeleteRequest,
  SpaceAddRequest,
  SpaceEditRequest,
  SpaceQueryRequest,
  SpaceUpdateRequest,
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
