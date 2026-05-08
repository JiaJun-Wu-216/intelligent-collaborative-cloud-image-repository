import type {
  CreateOutPaintingTaskResponse,
  GetOutPaintingTaskResponse,
  ImageSearchResult,
  LoginUserVO,
  PictureTagCategory,
  PictureVO,
  Space,
  SpaceCategoryAnalyzeResponse,
  SpaceLevel,
  SpaceSizeAnalyzeResponse,
  SpaceTagAnalyzeResponse,
  SpaceUsageAnalyzeResponse,
  SpaceUserAnalyzeResponse,
  SpaceVO,
} from '@/api/EntityType.ts'

export interface ResponseLoginUserVO {
  code?: number
  data?: LoginUserVO
  message?: string
}

export interface ResponseString {
  code?: number
  data?: string
  message?: string
}

export interface ResponseBoolean {
  code?: number
  data?: boolean
  message?: string
}

export interface ResponseNumber {
  code?: number
  data?: number
  message?: string
}

export interface ResponseAny {
  code?: number
  data?: any
  message?: string
}

export interface ResponsePictureVO {
  code?: number
  data?: PictureVO
  message?: string
}

export interface ResponsePictureVOList {
  code?: number
  data?: PictureVO[]
  message?: string
}

export interface ResponsePictureTagCategory {
  code?: number
  data?: PictureTagCategory
  message?: string
}

export interface ResponseSpaceVO {
  code?: number
  data?: SpaceVO
  message?: string
}

export interface ResponseSpaceLevelList {
  code?: number
  data?: SpaceLevel[]
  message?: string
}

export interface ResponseImageSearchResult {
  code?: number
  data?: ImageSearchResult[]
  message?: string
}

export interface ResponseCreateOutPaintingTaskResponse {
  code?: number
  data?: CreateOutPaintingTaskResponse
  message?: string
}

export interface ResponseGetOutPaintingTaskResponse {
  code?: number
  data?: GetOutPaintingTaskResponse
  message?: string
}

export interface ResponseSpaceUsageAnalyzeResponse {
  code?: number
  data?: SpaceUsageAnalyzeResponse
  message?: string
}

export interface ResponseSpaceCategoryAnalyzeResponse {
  code?: number
  data?: SpaceCategoryAnalyzeResponse[]
  message?: string
}

export interface ResponseSpaceTagAnalyzeResponse {
  code?: number
  data?: SpaceTagAnalyzeResponse[]
  message?: string
}

export interface ResponseSpaceSizeAnalyzeResponse {
  code?: number
  data?: SpaceSizeAnalyzeResponse[]
  message?: string
}

export interface ResponseSpaceUserAnalyzeResponse {
  code?: number
  data?: SpaceUserAnalyzeResponse[]
  message?: string
}

export interface ResponseSpace {
  code?: number
  data?: Space[]
  message?: string
}
