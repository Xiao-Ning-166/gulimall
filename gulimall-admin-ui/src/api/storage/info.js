import request from '@/utils/request'

// 分页查询仓库信息列表
export function getStorageInfoList(params) {
  return request({
    url: '/storage/ware-info/storages',
    method: 'get',
    params: params
  })
}

// 保存仓库信息
export function addStorage(data) {
  return request({
    url: '/storage/ware-info/storages',
    method: 'post',
    data
  })
}

// 保存仓库信息
export function updateStorageById(data) {
  return request({
    url: '/storage/ware-info/storages',
    method: 'put',
    data
  })
}

// 删除仓库信息
export function deleteStorageById(ids) {
  return request({
    url: '/storage/ware-info/storages',
    method: 'delete',
    data: ids
  })
}
