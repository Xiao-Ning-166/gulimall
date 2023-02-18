import request from '@/utils/request'

// 分页查询spu信息
export function getSkuList(params) {
  return request({
    url: '/product/skuinfo/list',
    method: 'get',
    params
  })
}
