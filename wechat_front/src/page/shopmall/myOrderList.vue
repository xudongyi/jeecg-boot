<template>
  <div class="orderList">
    <ul class="top">
      <li :class="{a:pickActive!=0,a_1:pickActive==0}" @click="pickContent('0')">待付款</li>
      <li :class="{b:pickActive!=1,b_1:pickActive==1}" @click="pickContent('1')">代发货</li>
      <li :class="{c:pickActive!=2,c_1:pickActive==2}" @click="pickContent('2')">待收货</li>
      <li :class="{d:pickActive!=3,d_1:pickActive==3}" @click="pickContent('3')">已完成</li>
    </ul>
    <div class="content" v-if="pickActive==0">
      <div class="dfk_list" v-for="(obj,index) in dfkArray">
        <div class="dfk_top">
          <span>{{new Date(obj.orders.createTime).Format("yyyy-MM-dd hh:mm:ss")}}</span>
          <span>共{{obj.orders.total}}件</span>
          <div><span>合计:</span><span>¥{{obj.orders.totalFee}}</span></div>
        </div>
        <div class="middle" @click="goOrderDetail(obj)">
          <ul>
            <li v-for="(obj1,index) in obj.goodsList"><img :src="imgPath+obj1.GOODS_IMG"/></li>
          </ul>
          <span>
	    				<img src="../../images/shopmall/right.png"/>
	    			</span>
        </div>

        <div class="dfk_bootom">
          <span @click="goFk(index)">去付款</span>
          <span @click="cancelOrder(index)">取消订单</span>
        </div>
      </div>
    </div>
    <div class="content" v-if="pickActive==1">
      <div class="dfk_list" v-for="(obj,index) in dfhArray">
        <div class="dfk_top">
          <span>{{new Date(obj.orders.createTime).Format("yyyy-MM-dd hh:mm:ss")}}</span>
          <span>共{{obj.orders.total}}件</span>
          <div><span>合计:</span><span>¥{{obj.orders.totalFee}}</span></div>
        </div>
        <div class="middle" @click="goOrderDetail(obj)">
          <ul>
            <li v-for="(obj1,index) in obj.goodsList"><img :src="imgPath+obj1.GOODS_IMG"/></li>
          </ul>
          <span>
	    				<img src="../../images/shopmall/right.png"/>
	    			</span>
        </div>

        <div class="dfk_bootom">
          <span>提醒发货</span>
        </div>
      </div>
    </div>
    <div class="content" v-if="pickActive==2">
      <div class="dfk_list" v-for="(obj,index) in dshArray">
        <div class="dfk_top">
          <span>{{new Date(obj.orders.createTime).Format("yyyy-MM-dd hh:mm:ss")}}</span>
          <span>共{{obj.orders.total}}件</span>
          <div><span>合计:</span><span>¥{{obj.orders.totalFee}}</span></div>
        </div>
        <div class="middle" @click="goOrderDetail(obj)">
          <ul>
            <li v-for="(obj1,index) in obj.goodsList"><img :src="imgPath+obj1.GOODS_IMG"/></li>
          </ul>
          <span>
	    				<img src="../../images/shopmall/right.png"/>
	    			</span>
        </div>

        <div class="dfk_bootom">
          <span @click="queryOrder(index)">确认收货</span>
          <span @click="goWl(obj.orders)">查看物流</span>
        </div>
      </div>
    </div>
    <div class="content" v-if="pickActive==3">
      <div class="dfk_list" v-for="(obj,index) in ywcArray">
        <div class="dfk_top">
          <span>{{new Date(obj.orders.createTime).Format("yyyy-MM-dd hh:mm:ss")}}</span>
          <span>共{{obj.orders.total}}件</span>
          <div><span>合计:</span><span>¥{{obj.orders.totalFee}}</span></div>
        </div>
        <div class="middle" @click="goOrderDetail(obj)">
          <ul>
            <li v-for="(obj1,index) in obj.goodsList"><img :src="imgPath+obj1.GOODS_IMG"/></li>
          </ul>
          <span>
	    				<img src="../../images/shopmall/right.png"/>
	    			</span>
        </div>

        <div class="dfk_bootom">
          <span>再次购买</span>
          <span @click="goWl(obj.orders)">查看物流</span>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
	import {orderDetail,cancelOrder,applyMall,queryOrder} from "@/api/shopmall"
	import { mapGetters } from 'vuex'
	import {Dialog} from 'vant';
	import {Toast} from 'vant';
  export default{
    data(){
      return {
        pickActive: 0,
				dfkArray:[],
				dfhArray:[],
				dshArray:[],
				ywcArray:[],
				ip:'0.0.0.0'


      }
    },
    created() {
			this.initData()

    },
    mounted() {

    },
    computed:{
    	...mapGetters([
        'user'
      ])
    },
    methods: {
    	initData(){

    		var loadObj1={
    			userId:this.user.id,
    			status:1
    		};
    		orderDetail(loadObj1).then(data=>{
						this.dfkArray=data;
						var price=0;
						for(var i=0;i<this.dfkArray.length;i++){
							var total=0;
							for(var j=0;j<this.dfkArray[i].goodsList.length;j++){
								total+=this.dfkArray[i].goodsList[j].counts;
								price+=(this.dfkArray[i].goodsList[j].counts*this.dfkArray[i].goodsList[j].SALE_PRICE)
							}

							this.$set(this.dfkArray[i].orders,"total",total)
							this.$set(this.dfkArray[i].orders,"price",price.toFixed(2))
						};
						console.log(this.dfkArray)

				})
    		var loadObj2={
    			userId:this.user.id,
    			status:2
    		};
    		orderDetail(loadObj2).then(data=>{
						this.dfhArray=data;
						var price=0;
						for(var i=0;i<this.dfkArray.length;i++){
							var total=0;
							for(var j=0;j<this.dfkArray[i].goodsList.length;j++){
								total+=this.dfkArray[i].goodsList[j].counts;
								price+=(this.dfkArray[i].goodsList[j].counts*this.dfkArray[i].goodsList[j].SALE_PRICE)
							}

							this.$set(this.dfkArray[i].orders,"total",total)
							this.$set(this.dfkArray[i].orders,"price",price.toFixed(2))
						};
				})
    		var loadObj3={
    			userId:this.user.id,
    			status:3
    		};
    		orderDetail(loadObj3).then(data=>{
						this.dshArray=data;
						var price=0;
						for(var i=0;i<this.dshArray.length;i++){
							var total=0;
							for(var j=0;j<this.dshArray[i].goodsList.length;j++){
								total+=this.dshArray[i].goodsList[j].counts;
								price+=(this.dshArray[i].goodsList[j].counts*this.dshArray[i].goodsList[j].SALE_PRICE)
							}
							this.$set(this.dshArray[i].orders,"total",total)
							this.$set(this.dshArray[i].orders,"price",price.toFixed(2))
						};
				})
    		var loadObj4={
    			userId:this.user.id,
    			status:4
    		};
    		orderDetail(loadObj4).then(data=>{
						this.ywcArray=data;
						var price=0;
						for(var i=0;i<this.ywcArray.length;i++){
							var total=0;
							for(var j=0;j<this.ywcArray[i].goodsList.length;j++){
								total+=this.ywcArray[i].goodsList[j].counts;
								price+=(this.ywcArray[i].goodsList[j].counts*this.ywcArray[i].goodsList[j].SALE_PRICE)
							}

							this.$set(this.ywcArray[i].orders,"total",total)
							this.$set(this.ywcArray[i].orders,"price",price.toFixed(2))
						};
				})
    	},
    	cancelOrder(index){
    		var loadObj={
    			orderId:this.dfkArray[index].orders.id
    		};
    		var that=this;
				Dialog.confirm({
	        message: '确定删除改商品?'
	      }).then(() => {
	        cancelOrder(loadObj).then(data=>{
						if(data){
							Toast("取消成功!");
							that.initData();
						}else{
							Toast("取消失败!");
						}
					})
	      });


    	},
    	queryOrder(index){
    		var that=this;
    		var loadObj={
    			orderId:this.dshArray[index].orders.id
    		};
    		queryOrder(loadObj).then(data=>{
						if(data){
							Toast("确认收货成功!");
							that.initData();
						}else{
							Toast("确认收货失败!");
						}
					})
    	},
    	goOrderDetail(obj){
    		console.log(obj)
      	this.$router.push({path:"/orderDetail",query:{orderId:obj.orders.id}})
    		this.$store.commit("updateOrderDetail",obj)
    	},
      pickContent(index){
        this.pickActive = index;
      },
      goWl(obj){
      	this.$router.push({path:"/logistics",query:{message:obj}})
      },
      goFk(index){
        let that = this;
      	//注册orderDetail
      	this.$store.commit("updateOrderDetail",this.dfkArray[index]);
      	//微信支付
      	console.log(this.dfkArray[index].orders)
	      let prepareParam = {
	        orders: JSON.stringify(this.dfkArray[index].orders),
	        body: "提交订单",
	        ip: this.ip,
	        openid: this.user.openId
	      };
	      console.log(prepareParam)
        applyMall(prepareParam).then(data=>{
	        WeixinJSBridge.invoke(
	          'getBrandWCPayRequest', data,
	          function (res) {
	            if(res.err_msg == "get_brand_wcpay_request:ok" ){
                that.$router.push({path:"/cashSuccessful",query:{payOr:true}})
	            }else{
                that.$router.push({path:"/cashSuccessful",query:{payOr:false}})
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
  .orderList {
    height: 100%;
  }

  .orderList > ul {
    height: 62px;
    border-bottom: 1px solid #eee;

  }

  .orderList > ul > li {
    height: 46px;
    margin-top: 8px;
    text-align: center;
    width: calc(25% - 1px);
    float: left;
    font-size: 14px;
    line-height: 76px;
  }

  .orderList > ul > li:first-child {
    border-right: 1px solid #eee;
  }

  .orderList > ul > li:nth-child(2) {
    border-right: 1px solid #eee;
  }

  .orderList > ul > li:nth-child(3) {
    border-right: 1px solid #eee;
  }

  .content {
    height: calc(100% - 62px);
  }

  .a {
    color: #999999;
    background: url(../../images/shopmall/dfk_1.png) no-repeat center 5px;
  }

  .a_1 {
    color: #56a9b7;
    background: url(../../images/shopmall/dfk.png) no-repeat center 5px;
  }

  .b {
    color: #999999;
    background: url(../../images/shopmall/dfh_1.png) no-repeat center 5px;
  }

  .b_1 {
    color: #56a9b7;
    background: url(../../images/shopmall/dfh.png) no-repeat center 5px;
  }

  .c {
    color: #999999;
    background: url(../../images/shopmall/dsh_1.png) no-repeat center 5px;
  }

  .c_1 {
    color: #56a9b7;
    background: url(../../images/shopmall/dsh.png) no-repeat center 5px;
  }

  .d {
    color: #999999;
    background: url(../../images/shopmall/ywc_1.png) no-repeat center 5px;
  }

  .d_1 {
    color: #56a9b7;
    background: url(../../images/shopmall/ywc.png) no-repeat center 5px;
  }

  .dfk_list .dfk_top {
    height: 34px;
    line-height: 34px;
    font-size: 12px;
  }

  .dfk_list .dfk_top > span {
    display: inline-block;
    height: 100%;
    float: left;
  }

  .dfk_list .dfk_top > span:first-child {
    width: 130px;
    margin-left: 20px;
    color: #999999;
  }

  .dfk_list .dfk_top > span:nth-child(2) {
    width: 90px;
    text-align: right;
  }

  .dfk_list .dfk_top > div {
    width: calc(100% - 240px);
    float: left;
  }

  .dfk_list .dfk_top > div > span:first-child {
    width: 36px;
    display: inline-block;
    float: left;
    margin-left: 5px;
  }

  .dfk_list .dfk_top > div > span:last-child {
    width: calc(100% - 41px);
    display: inline-block;
    float: left;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-weight: 700;

  }

  .dfk_list > .middle {
    height: 80px;
    background: #e4e4e4;
  }

  .dfk_list > .middle > ul {
    float: left;
    width: calc(100% - 40px);
    height: 100%;
    float: left;
  }

  .dfk_list > .middle > ul > li {
    width: 60px;
    height: 60px;
    margin-top: 10px;
    margin-left: 10px;
    float: left;

  }

  .dfk_list > .middle > ul > li > img {
    width: 60px;
    height: 60px;

  }

  .dfk_list > .middle > span {
    display: inline-block;
    width: 40px;
    line-height: 80px;
    height: 100%;
    float: left;
    text-align: center;

  }

  .dfk_list > .middle > span > img {
    display: inline-block;
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
</style>

