import request from '@/utils/request'

// 查询商品品牌列表数据
export function getMemberLevelList(params) {
  return request({
    url: '/member/memberLevel/list',
    method: 'get',
    params: params
  })
}
