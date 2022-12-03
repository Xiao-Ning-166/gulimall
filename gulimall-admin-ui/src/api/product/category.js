import request from '@/utils/request'

// 查询商铺分类列表数据
export function getCategoryList(params) {
  return request({
    url: '/product/category/list/tree',
    method: 'get',
    params: params
  })
}

// 新增商品分类
export function addCategory(data) {
  return request({
    url: '/product/category/save',
    method: 'post',
    data
  })
}

// 通过分类id更新
export function updateById(data) {
  return request({
    url: '/product/category/update',
    method: 'put',
    data
  })
}

// 根据id删除
export function deleteById(ids) {
  return request({
    url: '/product/category',
    method: 'delete',
    data: ids
  })
}

// 根据id删除
export function getSelectTreeData() {
  return request({
    url: '/product/category/select/tree',
    method: 'get'
  })
}

// 得到全部分类树形数据
export function getCategoryTreeData() {
  return request({
    url: '/product/category/tree',
    method: 'get'
  })
}
