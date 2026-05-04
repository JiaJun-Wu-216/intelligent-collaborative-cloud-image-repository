import {request} from '@/request'
import type {
  ResponseAny,
  ResponseBoolean,
  ResponseNumber,
  ResponsePictureTagCategory,
  ResponsePictureVO,
} from '@/api/ResponseType.ts'
import type {
  DeleteRequest,
  PictureEditRequest,
  PictureQueryRequest,
  PictureReviewRequest,
  PictureUploadByBatchRequest,
  PictureUploadRequest,
} from '@/api/EntityType.ts'

/**
 * 上传图片
 * @param file
 * @param pictureUploadRequest
 */
export const uploadPicture = (
  file: File,
  pictureUploadRequest: PictureUploadRequest,
): Promise<ResponsePictureVO> => {
  const formData = new FormData()
  formData.append('file', file) // key 必须是 "file"，与 @RequestPart("file") 对应
  const blob = new Blob([JSON.stringify(pictureUploadRequest)], { type: 'application/json' })
  formData.append('pictureUploadRequest', blob)
  return request.post('/picture/upload', formData)
}

/**
 * 使用 URL 上传图片
 * @param pictureUploadRequest  图片上传请求信息
 */
export const uploadPictureByUrl = (
  pictureUploadRequest: PictureUploadRequest,
): Promise<ResponsePictureVO> => {
  return request.post('/picture/upload/url', pictureUploadRequest)
}

/**
 * 批量抓取并上传图片
 * @param pictureUploadByBatchRequest 图片批量上传请求信息
 */
export const uploadPictureByBatch = (
  pictureUploadByBatchRequest: PictureUploadByBatchRequest,
): Promise<ResponseNumber> => {
  return request.post('/picture/upload/batch', pictureUploadByBatchRequest)
}

/**
 * 编辑图片
 * @param pictureEditRequest  编辑图片请求信息
 */
export const editPicture = (pictureEditRequest: PictureEditRequest): Promise<ResponseBoolean> => {
  return request.post('/picture/edit', pictureEditRequest)
}

/**
 * 获取图片分类和标签
 */
export const getTagCategory = (): Promise<ResponsePictureTagCategory> => {
  return request.get('/picture/tag-category')
}

/**
 * 根据图片主键获取图片（封装类）
 * @param id  图片主键
 */
export const getPictureVOById = (id: string): Promise<ResponsePictureVO> => {
  return request.get('/picture/get/vo', {
    params: {
      id,
    },
  })
}

/**
 * 分页获取图片列表（仅管理员可用）
 * @param pictureQueryRequest 图片查询请求信息
 */
export const listPictureByPage = (
  pictureQueryRequest: PictureQueryRequest,
): Promise<ResponseAny> => {
  return request.post('/picture/list/page', pictureQueryRequest)
}

/**
 * 分页获取图片列表（封装类）
 * @param pictureQueryRequest 图片查询请求信息
 */
export const listPictureVOByPage = (
  pictureQueryRequest: PictureQueryRequest,
): Promise<ResponseAny> => {
  return request.post('/picture/list/page/vo', pictureQueryRequest)
}

/**
 * 删除图片
 * @param deleteRequest 删除请求信息
 */
export const deletePicture = (deleteRequest: DeleteRequest): Promise<ResponseBoolean> => {
  return request.post('/picture/delete', deleteRequest)
}

/**
 * 审核图片
 * @param pictureReviewRequest  图片审核请求信息
 */
export const doPictureReview = (
  pictureReviewRequest: PictureReviewRequest,
): Promise<ResponseBoolean> => {
  return request.post('/picture/review', pictureReviewRequest)
}
