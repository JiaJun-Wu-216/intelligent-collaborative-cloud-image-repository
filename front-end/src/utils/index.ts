import {saveAs} from 'file-saver'

/**
 * 格式化文件大小
 * @param size  文件大小
 */
export const formatSize = (size?: number) => {
  if (!size) return '未知'
  if (size < 1024) return size + ' B'
  if (size < 1024 * 1024) return (size / 1024).toFixed(2) + ' KB'
  return (size / (1024 * 1024)).toFixed(2) + ' MB'
}

/**
 * 下载图片
 * @param url 图片下载地址
 * @param fileName 要保存为的文件名
 */
export const downloadImage = (url?: string, fileName?: string) => {
  if (!url) {
    return
  }
  saveAs(url, fileName)
}

/**
 * 转换颜色
 * @param input
 */
export const toHexColor = (input:any) => {
  console.log('toHexColor called with:', input) // 👈 看看到底传了啥

  if (!input || typeof input === 'undefined' || input === '') {
    return '#000000' // 或返回透明 #00000000，或抛错
  }

  // 去掉 0x 前缀
  const colorValue = input.startsWith('0x') ? input.slice(2) : input

  // 将剩余部分解析为十六进制数，再转成 6 位十六进制字符串
  const hexColor = parseInt(colorValue, 16).toString(16).padStart(6, '0')

  // 返回标准 #RRGGBB 格式
  return `#${hexColor}`
}
