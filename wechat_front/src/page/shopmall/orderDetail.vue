<template>
  <div class="orderDetail">
    <div class="state_pick" :class="{a:status==1,b:status==2,c:status==3,d:status==4}">
      <span v-if="status==1">待付款</span>
      <span v-if="status==2">待发货</span>
      <span v-if="status==3">待收货</span>
      <span v-if="status==4">已完成</span>
    </div>
    <div class="top_cash">
			<span class="location">
				<img src="../../images/shopmall/location.png"/>
			</span>
      <div class="middle">
        <p>{{orderDetail.userName}}&nbsp;&nbsp;&nbsp;{{orderDetail.mobile}}</p>
        <span>{{orderDetail.address}}</span>
      </div>
    </div>
    <div class="goodList" @click="goGoodList">
      <ul>
        <li v-for="(obj,index) in orderObj.goodsList"><img :src="imgPath+obj.GOODS_IMG"/></li>
      </ul>
      <div class="middle">
        共{{total}}件
      </div>
      <span>
				<img src="../../images/shopmall/arrow-right.png"/>
			</span>
    </div>
    <div class="bottom_text">
      <span>商品金额</span>
      <span>¥{{orderDetail.totalFee}}</span>
    </div>
    <div v-if="status==1" class="dfk_bootom">
      <span @click="goFk">去付款</span>
      <span @click="cancelOrder">取消订单</span>
    </div>
    <div v-if="status==2" class="dfk_bootom">
      <span>提醒发货</span>
    </div>
    <div v-if="status==3" class="dfk_bootom">
      <span @click="queryOrder">确认收货</span>
      <span @click="goWl">查看物流</span>
    </div>
    <div v-if="status==4" class="dfk_bootom">
      <span @click="keepShop">再次购买</span>
      <span @click="goWl">查看物流</span>
    </div>
  </div>
</template>
<script>
	import {cancelOrder,applyMall,getOrderDetail,queryOrder} from "@/api/shopmall"
	import {Toast} from "vant"
	import {Dialog} from "vant"
	import { mapGetters } from 'vuex'
  export default{
    data(){
      return {
        orderObj:{},
        orderDetail:{},
        goodsList:[],
        total:0,
        ip:'0.0.0.0',
        status:0

      }
    },
    created() {
      getOrderDetail({orderId:this.$route.query.orderId}).then(data=>{
        this.orderObj=data;
        this.orderDetail=this.orderObj.orders;
        this.status=this.orderObj.orders.status;
        this.totalFiler();
      })
    },
    mounted() {

    },
    computed:{
    	...mapGetters([
        'user'
      ])
    },
    methods: {
    	goGoodList(){
    		this.$router.push({path:"/goodsList",query:{zsList:this.orderObj.goodsList,showContent:2}})
    	},
    	totalFiler(){
    		this.total=0;
    		for(var i=0;i<this.orderObj.goodsList.length;i++){
    			this.total+=this.orderObj.goodsList[i].counts;
    		}
    	},
    	queryOrder(){
    		var loadObj={
    			orderId:this.$route.query.orderId
    		};
    		queryOrder(loadObj).then(data=>{
						if(data){
							Toast("确认收货成功!");
							this.$router.push({path:"/myOrderList"})
						}else{
							Toast("确认收货失败!");
						}
					})
    	},
    	cancelOrder(){
    		var loadObj={
    			orderId:this.orderObj.orders.id
    		};
    		var that=this;
				Dialog.confirm({
	        message: '确定取消该订单?'
	      }).then(() => {
	        cancelOrder(loadObj).then(data=>{
						if(data){
							Toast("取消成功!");
							this.$router.push({path:"/myOrderList"})
						}else{
							Toast("取消失败!");
						}
					})
	      });


    	},
    	goWl(){
      	this.$router.push({path:"/logistics",query:{message:this.orderObj.orders}})
     },
     keepShop(){
      	this.$router.push({path:"/shopmall"})
      },
     goFk(){
      	//写入订单,方便订单详细
    		this.$store.commit("updateOrderDetail",this.orderObj)
      	//微信支付
      	var that=this;
	      let prepareParam = {
	        orders: JSON.stringify(this.orderObj.orders),
	        body: "订单支付",
	        ip: this.ip,
	        openid: this.user.openId
	      };
	      console.log(prepareParam)
        applyMall(prepareParam).then(data=>{
	        WeixinJSBridge.invoke(
	          'getBrandWCPayRequest', data,
	          function (res) {
	            if(res.err_msg == "get_brand_wcpay_request:ok"){
	            	this.$router.push({path:"/cashSuccessful",query:{orderId:this.orderObj.orders.id,payOr:true}})
	            }else{
	            	this.$router.push({path:"/cashSuccessful",query:{orderId:this.orderObj.orders.id,payOr:false}})
	            }
	          }
	        );
	      })
      }
    },
    components: {}
  }
</script>
<style scoped>
  .orderDetail {
    height: 100%;
  }

  .top_cash {
    height: 88px;
    border-bottom: 1px solid #eee;
  }

  .top_cash > span:first-child {
    width: 30px;
    display: inline-block;
    height: 100%;
    line-height: 88px;

  }

  .top_cash > span:last-child {
    width: 50px;
    display: inline-block;
    height: 100%;
    line-height: 88px;

  }

  .top_cash > span {
    float: left;
  }

  .top_cash > span:first-child > img {
    margin-left: 12px;
  }

  .top_cash > span:last-child > img {
    margin-left: 15px;
  }

  .top_cash > span > img {
    display: inline-block;
  }

  .top_cash > div {
    width: calc(100% - 80px);
    float: left;
    font-size: 14px;
    height: 100%;
  }

  .top_cash > div > p {
    height: 30px;
    line-height: 40px;

  }

  .top_cash > div > span {
    margin-top: 10px;
    display: inline-block;
    width: 100%;
    height: calc(100% - 40px);
    line-height: 20px;

  }

  .goodList {
    height: 84px;
    border-bottom: 1px solid #eee;
  }

  .goodList > ul {
    width: calc(100% - 100px);
    height: 100%;
    float: left;
  }

  .goodList > ul > li {
    width: 60px;
    height: 60px;
    float: left;
    margin-top: 12px;
    margin-left: 8px;
  }

  .goodList > ul > li > img {
    width: 100%;
    height: 100%;
  }

  .goodList > span {
    width: 50px;
    display: inline-block;
    height: 100%;
    line-height: 84px;
    float: left;
  }

  .goodList > span > img {
    margin-left: 15px;
  }

  .goodList > span > img {
    display: inline-block;
  }

  .goodList .middle {
    width: 50px;
    height: 100%;
    text-align: center;
    line-height: 84px;
    float: left;
    font-size: 14px;
  }

  .bottom_text {
    height: 46px;
    line-height: 46px;
    border-bottom: 1px solid #eee;
  }

  .bottom_text > span {
    display: inline-block;
    height: 100%;
    line-height: 46px;
    float: left;
  }

  .bottom_text > span:first-child {
    width: calc(100% - 100px);
    text-indent: 10px;
  }

  .bottom_text > span:last-child {
    width: 100px;
    text-align: center;
    color: red;
  }

  .dfk_bootom {
    height: 38px;
    width: 100%;
    border-bottom: 1px solid #eee;
  }

  .dfk_bootom > span {
    height: 28px;
    width: 78px;
    border: 1px solid #41C7DB;
    display: inline-block;
    text-align: center;
    line-height: 28px;
    border-radius: 4px;
    margin-top: 5px;
    float: right;
    margin-right: 10px;
    color: #999;
    font-size: 14px;
  }

  .state_pick {
    height: 80px;
    font-size: 15px;
    color: white;
    text-indent: 40px;
    line-height: 80px;
  }

  .a {
    background: #66ccff url(../../images/shopmall/dfk_detail.png) no-repeat 80%;
  }

  .b {
    background: #66ccff url(../../images/shopmall/dfh_detail.png) no-repeat 80%;
  }

  .c {
    background: #66ccff url(../../images/shopmall/dsh_detail.png) no-repeat 80%;
  }

  .d {
    background: #66ccff url(../../images/shopmall/ywc_detail.png) no-repeat 80%;
  }
</style>

