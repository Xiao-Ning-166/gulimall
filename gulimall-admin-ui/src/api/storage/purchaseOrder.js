import request from '@/utils/request'

// 分页查询采购单列表
export function getPurchaseOrderList(params) {
  return request({
    url: '/storage/purchase/orders',
    method: 'get',
    params: params
  })
}

// 保存采购单信息
export function addPurchaseOrder(data) {
  return request({
    url: '/storage/purchase/orders',
    method: 'post',
    data
  })
}

// 更新采购单信息
export function updatePurchaseOrderById(data) {
  return request({
    url: '/storage/purchase/orders',
    method: 'put',
    data
  })
}

// 删除采购删除信息
export function deletePurchaseOrderById(ids) {
  return request({
    url: '/storage/purchase/orders',
    method: 'delete',
    data: ids
  })
}

// 或有所有新建/已分配的采购单列表
export function getPurchaseOrders() {
  return request({
    url: '/storage/purchase/orders/available',
    method: 'get'
  })
}
