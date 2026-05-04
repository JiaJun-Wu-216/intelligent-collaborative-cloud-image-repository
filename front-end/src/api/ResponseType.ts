import type {LoginUserVO} from '@/api/EntityType.ts'

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

export interface ResponseAny {
  code?: number
  data?: any
  message?: string
}
