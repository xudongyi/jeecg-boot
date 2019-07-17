<template>
  <div class="cash_sucessful">
    <div class="top_bg">
    	<span class="border_out">
    		<img src="../../images/shopmall/cash_successful.png" alt="" />
    		<!--<img src="../../images/shopmall/cash_faild.png" alt="" />-->
    	</span>
    </div>
    <div class="middle_message">
    	<span v-if="payOr">支付成功,您可以选择</span>
    	<span v-if="!payOr">支付失败,您可以选择</span>
    </div>
    <div class="bottom_action">
    	<ul v-if="payOr">
    		<li @click="keepShop">继续购物</li>
    		<li @click="goOrderDetail">查看订单</li>
    	</ul>
    	<ul v-if="!payOr">
    		<li @click="goOrderList">继续支付</li>
    		<li @click="goOrderDetail">查看订单</li>
    	</ul>
    </div>
  </div>
</template>
<script>

  export default{
    data(){
      return {
        payOr:false
      }
    },
    created() {
    	this.payOr=this.$route.query.payOr;

    },
    mounted() {

    },
    methods: {
      keepShop(){
      	this.$router.push({path:"/shopmall"})
      },
      goOrderList(){
      	this.$router.push({path:"/myOrderList",query:{orderId:this.$route.query.orderId}})
      },
      goOrderDetail(){
      	this.$router.push({path:"/orderDetail",query:{orderId:this.$route.query.orderId}})
      },


    },
    components: {}
  }
</script>
<style scoped>
  .cash_sucessful{
  	width:100%;
  	height: 100%;
}
.top_bg{
	height: 180px;
	width: 100%;
	position: relative;
}
.border_out{
	display: inline-block;
	width: 100px;
	height: 100px;
	border: 1px dashed black;
	position: absolute;
	left: 50%;
	top: 50%;
	border-radius: 50%;
	margin-left: -50px;
	margin-top: -50px;
}
.border_out>img{
	width: 60px;
	height: 60px;
	margin: 20px;
}
.middle_message{
	height: 28px;
	text-align: center;
	line-height: 28px;
}
.bottom_action{
	margin-top: 20px;
}
.bottom_action>ul{
	width: 100%;
	height: 36px;
	line-height: 36px;
	text-align: center;
}
.bottom_action>ul>li{
	width: 100px;
	height: 34px;
	line-height: 34px;
	display: inline-block;
	font-size: 14px;
	border-radius: 4px;

}
.bottom_action>ul>li:first-child{
	border: 1px solid #ff6600;
	margin-right: 10px;
}
.bottom_action>ul>li:last-child{
	border: 1px solid #0D97F3;
	margin-left: 10px;
}
</style>

