import request from '@/utils/request'

// 查询商品规格列表数据
export function getAttrValuesBySpuId(spuId) {
  return request({
    url: `/product/product-attr/values/${spuId}`,
    method: 'get'
  })
}

// 查询商铺分类列表数据
export function updateAttrValuesBySpuId(spuId, data) {
  return request({
    url: `/product/product-attr/values/${spuId}`,
    method: 'put',
    data: data
  })
}
