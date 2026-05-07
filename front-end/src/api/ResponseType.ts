import type {
  ImageSearchResult,
  LoginUserVO,
  PictureTagCategory,
  PictureVO,
  SpaceLevel,
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
