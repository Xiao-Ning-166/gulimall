import request from '@/utils/request'

// 查询规格参数列表数据
export function getSpecificationList(params) {
  return request({
    url: '/product/attr/list',
    method: 'get',
    params: params
  })
}

// 新增属性
export function addAttr(data) {
  return request({
    url: '/product/attr/save',
    method: 'post',
    data
  })
}

// 修改属性
export function updateById(data) {
  return request({
    url: '/product/attr/update',
    method: 'put',
    data
  })
}

// 删除属性
export function deleteById(ids) {
  return request({
    url: '/product/attr/delete',
    method: 'delete',
    data: ids
  })
}

// 查询分类下的所有销售属性
export function searchSaleAttributes(catalogId) {
  return request({
    url: `/product/attr/sales/${catalogId}`,
    method: 'get'
  })
}

