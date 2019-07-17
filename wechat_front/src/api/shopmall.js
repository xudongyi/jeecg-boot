import {http} from '../plugins/http'

export function getUserList(params) {
  return http({
    url: '/user/getUserList.do',
    method: 'post',
    params
  })
}
//获取商品列表
export function getGoodList(params) {
  return http({
    url: '/api/shopmall/goodsList.do',
    method: 'post',
    params
  })
}
//保存地址、修改地址
export function saveAddress(params) {
  return http({
    url: '/api/shopmall/saveAddress.do',
    method: 'post',
    params
  })
}
//生成订单
export function createOrderList(params) {
  return http({
    url: '/api/shopmall/createOrder.do',
    method: 'post',
    params
  })
}
//取消订单
export function cancelOrder(params) {
  return http({
    url: '/api/shopmall/cancleOrder.do',
    method: 'post',
    params
  })
}
//查询订单
export function orderDetail(params) {
  return http({
    url: '/api/center/getOrderList.do',
    method: 'post',
    params
  })
}
//确认收货
export function queryOrder(params) {
  return http({
    url: '/api/shopmall/configOrder.do',
    method: 'post',
    params
  })
}
//查询订单
export function getOrderDetail(params) {
  return http({
    url: '/api/center/getOrderDetail.do',
    method: 'post',
    params
  })
}
//获取地址列表
export function getAddress(params) {
  return http({
    url: '/api/shopmall/getUserAddress.do',
    method: 'post',
    params
  })
}
//删除地址列表
export function deleteAddress(params) {
  return http({
    url: '/api/shopmall/deleteAddress.do',
    method: 'post',
    params
  })
}
//付款功能
export function applyMall(params) {
  return http({
    url: '/wechat/mallPay.do',
    method: 'post',
    params
  })
}
