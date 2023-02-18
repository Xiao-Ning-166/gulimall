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

// 根据属性分组id获取对应属性列表
export function getAttrsByAttrGroupId(params) {
  return request({
    url: `/product/attrgroup/attrs/attrgroup/${params.attrGroupId}`,
    method: 'get',
    params: { current: params.current, size: params.size }
  })
}

// 查询当前分类下没有绑定分组的属性
export function getAttrsNoAttrGroup(params) {
  return request({
    url: `/product/attrgroup/attrs/category/${params.catelogId}`,
    method: 'get',
    params: { current: params.current, size: params.size }
  })
}

// 批量保存属性、属性分组关系
export function saveRelationBatch(data) {
  return request({
    url: '/product/attrattrgrouprelation/save/relation',
    method: 'post',
    data
  })
}

// 批量解除属性、属性分组的关联
export function deleteBatchById(data) {
  return request({
    url: `/product/attrattrgrouprelation/delete/${data.attrGroupId}`,
    method: 'delete',
    data: data.attrIds
  })
}

// 得到分类下的所有属性分组和属性集合
export function getAttrGroupsWithAttr(catelogId) {
  return request({
    url: '/product/attrgroup/attrs',
    method: 'get',
    params: { catelogId: catelogId }
  })
}
