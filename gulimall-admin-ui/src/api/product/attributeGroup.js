import request from '@/utils/request'

// 查询商铺分类列表数据
export function getAttrGroupList(params) {
  return request({
    url: '/product/attrgroup/list',
    method: 'get',
    params: params
  })
}

// 根据id删除属性分组
export function deleteById(ids) {
  return request({
    url: '/product/attrgroup/delete',
    method: 'delete',
    data: ids
  })
}

// 新增属性分组
export function addAttrGroup(data) {
  return request({
    url: '/product/attrgroup/save',
    method: 'post',
    data
  })
}

// 根据id更新信息
export function updateById(data) {
  return request({
    url: '/product/attrgroup/update',
    method: 'put',
    data
  })
}

// 根据分类id获取所属分组列表
export function getAttrGroups(catelogId) {
  return request({
    url: `/product/attrgroup/list/${catelogId}`,
    method: 'get'
  })
}
