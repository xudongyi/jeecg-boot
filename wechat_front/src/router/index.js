import Vue from "vue";
import Router from "vue-router";
const _import = require('./_import_development');
Vue.use(Router);

export default new Router({
  mode: 'hash',
  base: "?#",//防止微信支付无法识别对应的支付目录
  routes: [
    {
      path: '/404', component: _import('error/404')
      , meta: {
      title: "404",
    }, hidden: true
    },
    {
      path: '/payTest',
      meta: {
        title: "测试微信支付",
      },
      component: _import('payTest')
    },
    {
      path: '/guide',
      meta: {
        title: "",
      },
      component: _import('apply/guide')
    },
    {
      path: '/agentFirstApply',
      meta: {
        title: "申请",
      },
      component: _import('apply/agentFirstApply')
    },
    {
      path: '/agentWaitPay',
      meta: {
        title: "等待付款",
      },
      component: _import('apply/agentWaitPay')
    },
    {
      path: '/credencePay',
      meta: {
        title: "支付",
      },
      component: _import('apply/credencePay')
    },
    {
      path: '/checking',
      meta: {
        title: "审核中",
      },
      component: _import('apply/checking')
    },
    {
      path: '/shopmall',
      meta: {
        title: "商城",
      },
      component: _import('shopmall/index')
    },
    {
      path: '/shoppingMart',
      meta: {
        title: "购物车",
      },
      component: _import('shopmall/shoppingMart')
    },
    {
      path: '/goCash',
      meta: {
        title: "填写订单",
      },
      component: _import('shopmall/goCash')
    },
    {
      path: '/address',
      meta: {
        title: "选择收货地址",
      },
      component: _import('shopmall/address')
    },
    {
      path: '/goodsList',
      meta: {
        title: "商品清单",
      },
      component: _import('shopmall/goodsList')
    },
    {
      path: '/myOrderList',
      meta: {
        title: "订单列表",
      },
      component: _import('shopmall/myOrderList')
    },
    {
      path: '/logistics',
      meta: {
        title: "物流信息",
      },
      component: _import('shopmall/logistics')
    },
    {
      path: '/addAdress',
      meta: {
        title: "新建地址",
      },
      component: _import('shopmall/addAdress')
    },
    {
      path: '/orderDetail',
      meta: {
        title: "订单详情",
      },
      component: _import('shopmall/orderDetail')
    },
   {
      path: '/cashSuccessful',
      meta: {
        title: "支付详情",
      },
      component: _import('shopmall/cashSuccessful')
    },
    {
      path: '/addressPerson',
      meta: {
        title: "收货地址",
      },
      component: _import('shopmall/addressPerson')
    },
    {
      path: '/center',
      meta: {
        title: "个人中心",
      },
      component: _import('center/index')
    },
    {
      path: '/myAgent',
      meta: {
        title: "我的代理与加盟",
      },
      component: _import('center/myAgent')
    },
    {
      path: '/recommendRebate',
      meta: {
        title: "推荐返利",
      },
      component: _import('center/recommendRebate')
    },
    {
      path: '/agentRebate',
      meta: {
        title: "代理返利",
      },
      component: _import('center/agentRebate')
    },

    {
      path: '/routerGuide',
      meta: {
        title: "代理返利",
      },
      component: _import('router-test/guide')
    },

    {
      path: '/routerTest1',
      meta: {
        title: "routerTest1",
      },
      component: _import('router-test/routerTest1')
    },
    {
      path: '/routerTest2',
      meta: {
        title: "routerTest2",
      },
      component: _import('router-test/routerTest2')
    },
    {
      path: '/agentRebate',
      meta: {
        title: "代理返利",
      },
      component: _import('center/agentRebate')
    },

    {
      path: '*', redirect: '/404',
      hidden: true
    }
  ]

})
