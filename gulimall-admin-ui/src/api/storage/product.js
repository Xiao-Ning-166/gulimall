import request from '@/utils/request'

// 分页查询商品库存列表
export function getProductStockList(params) {
  return request({
    url: '/storage/ware-sku/stocks',
    method: 'get',
    params: params
  })
}