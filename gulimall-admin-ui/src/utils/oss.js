import request from '@/utils/request'

// 上传文件
export function upload(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/third/oss/upload',
    method: 'post',
    data: formData,
    config: {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }
  })
}
