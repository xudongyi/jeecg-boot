<template>
  <div class="go_cash">
    <div class="top_cash" v-if="addressList" @click="pickAddress">
			<span class="location">
				<img src="../../images/shopmall/location.png"/>
			</span>
      <div class="middle">
        <p>{{address.name}}&nbsp;&nbsp;&nbsp;{{address.tel}}</p>
        <span>{{address.address}}</span>
      </div>
      <span>
				<img src="../../images/shopmall/arrow-right.png"/>
			</span>
    </div>
    <div v-if="!addressList" class="top_cash" @click="pickAddress" style="line-height: 89px;text-indent: 20px;">
     		暂无地址请添加地址!
     		<span style="float: right;margin-right: 20px;">
					<img src="../../images/shopmall/arrow-right.png"/>
				</span>
    </div>
    <div class="goodList" @click="goGoodList">
      <ul>
        <li v-for="(obj,index) in targetArray" v-if="index<3"><img :src="imgPath+obj.GOODS_IMG"/></li>
      </ul>
      <div class="middle">
        共{{totalNum}}件
      </div>
      <span>
				<img src="../../images/shopmall/arrow-right.png"/>
			</span>
    </div>
    <div class="bottom_text">
      <span>商品金额</span>
      <span>¥{{totalPrice}}</span>
    </div>
    <div class="goLastCash">
      <span>实付款:</span>
      <span>¥{{totalPrice}}</span>
      <span @click="goCash">去结算</span>
    </div>
  </div>
</template>
<script>
  import {getAddress,createOrderList,applyMall} from "@/api/shopmall"
	import {Toast} from "vant"
  import { mapGetters } from 'vuex'
  export default{
    data(){
      return {
        address: {},
        targetArray: [],
        totalNum: 0,
        totalPrice: 0,
        list:[],
        addressList:false,
        addressMessage:{}
      }
    },
    created() {
    	var loadObj={
    		userId:this.user.id
    	};
      this.addressMessage = this.$store.state.storeData.address;

      //if(this.$store.state.storeData.lastArray==undefined||this.$store.state.storeData.lastArray.length==0){
//    	this.targetArray = this.$route.query.target;
//    	this.totalNum = this.$route.query.totalNum;
//    	this.totalPrice = this.$route.query.totalPrice;
//    }else{
      	this.targetArray=this.$store.state.storeData.lastArray;
      	this.totalNum = this.$store.state.storeData.totalNum;
      	this.totalPrice = this.$store.state.storeData.totalPrice;
//    }

      console.log(this.targetArray)

      if (this.addressMessage != undefined && this.addressMessage.id == undefined) {
      	getAddress(loadObj).then(data=>{

      		if(data.length==0){
      			this.addressList=false;
      		}else{
      			this.addressList=true;
      		};
					for(var i=0;i<data.length;i++){
						var obj={
							id: '',
					        name: '',
					        tel: '',
					        address: ''
						};
						obj.id=data[i].id;
						obj.name=data[i].userName;
						obj.tel=data[i].mobile;
						obj.address=data[i].unionAddress+data[i].address;
						obj.province=data[i].province;
						obj.county=data[i].area;
						obj.city=data[i].city;
						obj.address_detail=data[i].address;
						obj.area_code=data[i].province;
						obj.is_default=	data[i].isDefault
						this.list.push(obj);
						if(data[i].isDefault==1){
							this.address = this.list[i];
							this.addressMessage=data[i]
						}else{
							this.address = this.list[0];
							this.addressMessage=data[0]
						}
						console.log( this.address)
					}


				})

      } else {

    this.address.id=this.addressMessage.id;
		this.address.name=this.addressMessage.userName;
		this.address.tel=this.addressMessage.mobile;
		this.address.address=this.addressMessage.unionAddress+this.addressMessage.address;
		this.address.province=this.addressMessage.province;
		this.address.county=this.addressMessage.area;
		this.address.city=this.addressMessage.city;
		this.address.address_detail=this.addressMessage.address;
		this.address.area_code=this.addressMessage.province;
		this.address.is_default=this.addressMessage.isDefault;
		console.log( this.address)
        this.addressList=true;
      }

    },
    mounted() {

    },
    computed:{
      ...mapGetters([
        'user'
      ])
    },
    methods: {
      pickAddress(){
        this.$router.replace({path: "/address"});
        this.$store.commit("updateCashGoods",this.targetArray)
        this.$store.commit("updateCashNum",this.totalNum)
        this.$store.commit("updateCashPrice",this.totalPrice)

      },
      goCash(){

        var getGoodList=this.$store.state.storeData.shopGoods;

        //拼需要穿过去的goodList
        var goodList=[];
        var totalPrice=0;

        for(var i=0;i<getGoodList.length;i++){
        	for(var j=0;j<getGoodList[i].goodsList.length;j++){
        		if(getGoodList[i].goodsList[j].show==true&&getGoodList[i].goodsList[j].count>0){
        			var obj={
        				goodsId:getGoodList[i].goodsList[j].ID,
        				goodsSpec:getGoodList[i].goodsList[j].GOODS_SPEC,
        				salePrice:getGoodList[i].goodsList[j].SALE_PRICE,
        				counts:getGoodList[i].goodsList[j].count

        			};
        			totalPrice=totalPrice+(getGoodList[i].goodsList[j].SALE_PRICE*getGoodList[i].goodsList[j].count)
        		goodList.push(obj)

        		}
        	}
        }
        var orders={
        	userId:this.user.id,
        	userName:this.addressMessage.userName,
        	mobile:this.addressMessage.mobile,
        	address:this.addressMessage.address,
        	createUser:this.user.userName,
        	totalFee:totalPrice,
        };
        console.log(orders)
        console.log(getGoodList)
        var loadObj={
        	goodsList:JSON.stringify(goodList),
        	userAddress:JSON.stringify(this.addressMessage),
        	orders:JSON.stringify(orders),
        }
        console.log(loadObj)
        createOrderList(loadObj).then(data=>{
        	if(data.userName!=undefined&&data.userName==this.addressMessage.userName){
            this.goFk(data);
        	}else{
        		Toast("订单创建失败")
        	}
        })
      },
      goGoodList(){
      	console.log(this.$store.state.storeData.shopGoods)
        this.$router.push({path: "/goodsList",query:{zsList:this.$store.state.storeData.shopGoods,showContent:1}});
      },
      goFk(order){
        let that = this;
        //注册orderDetail
        this.$store.commit("updateOrderDetail",order);
        order.updateTime = null;
        //微信支付
        let prepareParam = {
          orders: JSON.stringify(order),
          body: "订单支付",
          ip: '0.0.0.0',
          openid: this.user.openId
        };
        applyMall(prepareParam).then(data=>{
          WeixinJSBridge.invoke(
            'getBrandWCPayRequest', data,
            function (res) {
              if(res.err_msg == "get_brand_wcpay_request:ok" ){
                that.$router.replace({path:"/cashSuccessful",query:{payOr:true,orderId:order.id}})
              }else{
                that.$router.replace({path:"/cashSuccessful",query:{payOr:false,orderId:order.id}})
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

  .goLastCash {
    width: 100%;
    height: 44px;
    line-height: 44px;
    position: fixed;
    bottom: 0px;

  }

  .goLastCash > span {
    height: 44px;
    line-height: 44px;
    display: inline-block;
    float: left;
  }

  .goLastCash > span:first-child {
    width: 100px;
    text-align: center;
  }

  .goLastCash > span:nth-child(2) {
    width: calc(100% - 192px);
    text-align: left;
    color: #e43a3d;
    font-size: 16px;
    font-weight: 700;
  }

  .goLastCash > span:last-child {
    width: 92px;
    text-align: center;
    background: #ff0000;
    color: white;
  }
</style>

