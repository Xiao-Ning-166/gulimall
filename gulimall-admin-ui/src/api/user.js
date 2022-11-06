import request from '@/utils/request'

// 用户登录
export function login(data) {
  return request({
    url: '/sys/login',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getInfo() {
  return request({
    url: '/sys/user/info',
    method: 'get'
  })
}

// 退出登录
export function logout() {
  return request({
    url: '/sys/logout',
    method: 'post'
  })
}
