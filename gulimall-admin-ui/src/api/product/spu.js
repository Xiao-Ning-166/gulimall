import request from '@/utils/request'

// 保存品牌信息
export function saveSpu(data) {
  return request({
    url: '/product/spuinfo/save',
    method: 'post',
    data
  })
}

// 分页查询spu信息
export function getSpuList(params) {
  return request({
    url: '/product/spuinfo/list',
    method: 'get',
    params
  })
}
