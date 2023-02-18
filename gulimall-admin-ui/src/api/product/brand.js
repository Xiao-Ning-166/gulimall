import request from '@/utils/request'

// 查询商品品牌列表数据
export function getBrandList(params) {
  return request({
    url: '/product/brand/list',
    method: 'get',
    params: params
  })
}

// 保存品牌信息
export function addBrand(data) {
  return request({
    url: '/product/brand/save',
    method: 'post',
    data
  })
}

// 更新品牌信息
export function updateById(data) {
  return request({
    url: '/product/brand/update',
    method: 'put',
    data
  })
}

// 更新品牌显示状态
export function updateStatusById(data) {
  return request({
    url: '/product/brand/status',
    method: 'put',
    data
  })
}

// 删除品牌
export function deleteById(ids) {
  return request({
    url: '/product/brand/delete',
    method: 'delete',
    data: ids
  })
}

// 得到品牌关联的分类列表
export function getRelationCategoryList(brandId) {
  return request({
    url: `/product/categorybrandrelation/category/${brandId}`,
    method: 'get'
  })
}

// 保存品牌、分类关联关系
export function saveBrandCategoryRelation(data) {
  return request({
    url: '/product/categorybrandrelation/save',
    method: 'post',
    data
  })
}

// 删除关联关系
export function removeRelation(ids) {
  return request({
    url: '/product/categorybrandrelation/delete',
    method: 'delete',
    data: ids
  })
}

// 删除关联关系
export function getBrandsByCategory(categoryId) {
  return request({
    url: `/product/categorybrandrelation/brand/${categoryId}`,
    method: 'get'
  })
}
