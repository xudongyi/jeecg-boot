import {
  getOAuth2UserInfo
} from "@/api/wechat";
/**
 数据模块
 */
export default {
  state: {
    //基于缓存的用户信息，和缓存一致(初始化加载数据)
    //user: {id:'45a5e1295c0b4cb680dd54e7b90d8673',nickname:'蓝雪魄',userName:'徐冬益',sex:1,qrcode:'162f7d13aec'},
    user: {},
    isLogin: false, //是否登录
    shopGoods: [], //
    address: {},
    lastArray: [],
    totalNum: 0,
    totalPrice: 0,
    pickAddressId: "",
    orderDetail: {}
  },
  mutations: {
    //更新用户的信息
    updateUserInfo(state, user) {
      state.user = user;
    },
    //更新用户是否登录状态
    updateLoginState(state, Login) {
      state.isLogin = Login;
    },
    //保存购物车信息
    updateShopGoods(state, shopGoods) {
      state.shopGoods = shopGoods;
    },
    //最后结算商品信息
    updateCashGoods(state, lastArray) {
      state.lastArray = lastArray;
    },
    //最后结算商品共计
    updateCashNum(state, totalNum) {
      state.totalNum = totalNum;
    },
    //最后结算商品价格
    updateCashPrice(state, totalPrice) {
      state.totalPrice = totalPrice;
    },
    //最后订单详细
    updateOrderDetail(state, orderDetail) {
      state.orderDetail = orderDetail;
    },
    //保存选中地址的信息
    updateAddress(state, address) {
      state.address = address;
    },
  },
  actions: {
  }
}
