import request from '@/utils/request'

// 获取登录用户菜单信息
export function getUserMenuList() {
  return request({
    url: '/sys/menu/nav',
    method: 'get'
  })
}

// 获取菜单列表
export function getMenuList(query) {
  return request({
    url: '/sys/menu/list',
    method: 'get',
    params: query
  })
}

// 获取菜单列表
export function getTreeData() {
  return request({
    url: '/sys/menu/select',
    method: 'get'
  })
}

// 根据id删除菜单
export function deleteMenuById(menuId) {
  return request({
    url: `/sys/menu/delete/${menuId}`,
    method: 'delete'
  })
}

// 保存菜单数据
export function addMenu(data) {
  return request({
    url: '/sys/menu/save',
    method: 'post',
    data
  })
}

// 修改菜单数据
export function editMenu(data) {
  return request({
    url: '/sys/menu/update',
    method: 'post',
    data
  })
}
