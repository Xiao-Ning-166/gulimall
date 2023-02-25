import request from '@/utils/request'

// 分页查询采购需求列表
export function getPurchaseNeedList(params) {
  return request({
    url: '/storage/purchase-detail/needs',
    method: 'get',
    params: params
  })
}

// 保存采购需求信息
export function addPurchaseNeed(data) {
  return request({
    url: '/storage/purchase-detail/needs',
    method: 'post',
    data
  })
}

// 更新采购需求信息
export function updatePurchaseNeedById(data) {
  return request({
    url: '/storage/purchase-detail/needs',
    method: 'put',
    data
  })
}

// 更新采购需求信息
export function deletePurchaseNeedById(ids) {
  return request({
    url: '/storage/purchase-detail/needs',
    method: 'delete',
    data: ids
  })
}

// 合并采购需求
export function mergePurchaseNeed(data) {
  return request({
    url: '/storage/purchase-detail/needs/merge',
    method: 'put',
    data
  })
}
