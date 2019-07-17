<template>
  <div class="shop_mall">
  	<div class="bg_shopmall" v-if="goodList.length!=0">
	    <van-tabs sticky class="style" @click="onClick">
	      <van-tab v-for="(obj,index) in goodList" :key="index" :title="obj.brandName">
	        <div class="ever_type_css">
	          <div class="ever_type" v-for="(ele,index1) in  obj.goodsList">
	            <div class="left_ever">
	              <img :src="imgPath+ele.GOODS_IMG" alt="商品图片"/>
	            </div>
	            <div class="right_ever">
	              <span>{{ele.GOODS_NAME}}</span>
	              <div class="bottom_right">
	                <ul>
	                  <li>1{{ele.GOODS_SPEC}}</li>
	                  <li>2.14kg</li>
	                </ul>
	                <span>¥{{ele.SALE_PRICE}}</span>
	                <div class="num_change">
	                  <img @click="decreaseNum(index1)" v-show="ele.count!=0" src="../../images/shopmall/jian_shop.png"/>
	                  <span v-show="ele.count!=0">{{ele.count}}</span>
	                  <img @click="increaseNum(index1)" src="../../images/shopmall/jia_shop.png"/>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
	      </van-tab>
	    </van-tabs>
	    <div class="bottom_go_shoppingmarket">
	      <div class="market" @click="goShoppingMart">
	        <span>{{totalNum}}</span>
	      </div>
	      <div class="left_price">
	        ¥{{totalPrice}}
	      </div>
	      <div class="go_cash" @click="goCash">
	        去结算
	      </div>
	    </div>
    </div>
    <div class="bg_shopmall" v-if="goodList.length==0">
    	<span @click="$router.replace({path: '/agentFirstApply', query: {type: 2}})">成为加盟商,即刻选购商品>></span>
    </div>
  </div>
</template>
<script>
	import {getGoodList} from "@/api/shopmall"
	import { Toast } from 'vant';
  import { mapGetters } from 'vuex'
  export default{
    data(){
      return {
        goodList: [],
        active: 0,
        totalPrice: 0,
        totalNum: 0,
        lastArray: []
      }
    },
    created() {
    	console.log(this.user)
      var getArray = this.$store.state.storeData.shopGoods;
      if (getArray.length == 0) {
        this.initData();
      } else {
        this.goodList = getArray;
        this.filerArray();
        console.log(this.goodList)
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
      onClick(index, title){
        this.active = index;
      },
      initData(){
      	var loadObj={
	    		userId:this.user.id
	    	};
      	getGoodList(loadObj).then(data=>{
      		this.goodList=data;
      		for (var i = 0; i < this.goodList.length; i++) {
	          for (var j = 0; j < this.goodList[i].goodsList.length; j++) {
	            this.$set(this.goodList[i].goodsList[j], "count", 0);
	            this.$set(this.goodList[i].goodsList[j], "show", false)
	            this.$set(this.goodList[i].goodsList[j], "price", this.goodList[i].goodsList[j].SALE_PRICE)
	          }
	       }
      	})



      },
      increaseNum(index){
        this.goodList[this.active].goodsList[index].count++;
        this.totalNum++;
        this.totalPrice = this.totalPrice + this.goodList[this.active].goodsList[index].price;
        this.filerArray();

      },
      decreaseNum(index){
        this.goodList[this.active].goodsList[index].count--;
        this.totalNum--;
        this.totalPrice = this.totalPrice - this.goodList[this.active].goodsList[index].price;
        this.filerArray();

      },
      goShoppingMart(){
      	this.filerArray();
      	if(this.totalNum!=0){
      		this.$router.push({path: "/shoppingMart"});
        	this.$store.commit("updateShopGoods", this.goodList)
        	
      	}else{
      		Toast('您还未选购任何商品!');
      	}

      },
      filerArray(){
        this.totalNum = 0;
        this.totalPrice = 0
        this.lastArray = [];
        for (var i = 0; i < this.goodList.length; i++) {
          for (var j = 0; j < this.goodList[i].goodsList.length; j++) {
            if (this.goodList[i].goodsList[j].count != 0) {
            	this.goodList[i].goodsList[j].show=true;
              this.totalNum += this.goodList[i].goodsList[j].count;
              this.totalPrice += (this.goodList[i].goodsList[j].count * this.goodList[i].goodsList[j].price);
              this.lastArray.push(this.goodList[i].goodsList[j])
            }else{
            	this.goodList[i].goodsList[j].show=false;
            }
          }
        }
      },
      goCash(){
      	if(this.totalNum!=0){
      		console.log(this.lastArray)
      		 this.$router.push({
	          path: "/goCash"
	          //query: {target:this.lastArray, totalNum: this.totalNum, totalPrice: this.totalPrice}
	        });
	        this.$store.commit("updateShopGoods", this.goodList)
	        this.$store.commit("updateCashGoods", this.lastArray)
					this.$store.commit("updateCashNum",this.totalNum)
        	this.$store.commit("updateCashPrice",this.totalPrice)
      	}else{
      		Toast('您还未选购任何商品!');
      	}

      }


    },
    components: {}
  }
</script>
<style scoped>

  .shop_mall {
    width: 100%;
    height: 100%;
  }
  .bg_shopmall{
  	width: 100%;
  	height: 100%;
  }
  .bg_shopmall>span{
  	line-height: 400px;
  	display: inline-block;
  	width: 100%;
  	text-align: center;
  	color: #0D97F3;
  	text-decoration: underline;
  	
  }

  /*改变tab样式*/
  .shop_mall .van-tab--active {
    color: white !important;
  }

  .shop_mall .van-tabs__nav-bar {
    background-color: #41C7DB !important;
  }

  .ever_type_css {
    height: calc(100% - 88px);
    overflow: scroll;
  }

  .ever_type {
    height: 84px;
    border-bottom: 1px solid #eee;
  }

  .style {
    padding-bottom: 44px;
    width: 100%;
   	overflow: scroll;
  }

  .ever_type .left_ever {
    width: 100px;
    height: 100%;
    float: left;
  }

  .ever_type .left_ever > img {
    width: 60px;
    height: 60px;
    margin: 12px 20px;
  }

  .right_ever {
    width: calc(100% - 100px);
    height: 100%;
    float: left;
  }

  .right_ever > span {
    width: 100%;
    display: inline-block;
    height: 26px;
    font-size: 14px;
    overflow: hidden;
    text-align: left;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 12px;
  }

  .right_ever > .bottom_right {
    width: 100%;
    height: calc(100% - 40px);
  }

  .right_ever > .bottom_right ul {
    width: 100px;
    height: 100%;
    float: left;
  }

  .right_ever > .bottom_right ul > li {
    height: 100%;
    float: left;
    font-size: 12px;
    color: #666;
    width: 40%;
  }

  .right_ever > .bottom_right > span {
    color: #009900;
    width: calc(100% - 220px);
    height: 100%;
    float: left;
    line-height: 26px;
  }

  .right_ever > .bottom_right > .num_change {
    width: 120px;
    height: 100%;
    float: left;
    line-height: 44px;
  }

  .num_change > img {
    display: inline-block;
    width: 18px;
    height: 18px;
  }

  .num_change > img:first-child {
    margin-left: 20px;
    margin-top: 13px;
    float: left;
  }

  .num_change > img:last-child {
    margin-right: 10px;
    float: right;
    margin-top: 13px;
  }

  .num_change > span {
    display: inline-block;
    width: calc(100% - 66px);
    text-align: center;
    height: 44px;
    line-height: 44px;
    vertical-align: top;
    float: left;
  }

  .bottom_go_shoppingmarket {
    height: 44px;
    width: 100%;
    position: fixed;
    bottom: 0px;
    background: #000000;
  }

  .bottom_go_shoppingmarket .market {
    height: 55px;
    width: 55px;
    position: absolute;
    bottom: 10px;
    left: 14px;
    border-radius: 50%;
    background: #66cc99 url(../../images/shopmall/shoppingmark.png) no-repeat center;
  }

  .market > span {
    display: inline-block;
    height: 18px;
    width: 18px;
    font-size: 12px;
    text-align: center;
    background: red;
    color: white;
    border-radius: 50%;
    margin-left: 35px;
    position: relative;
    top: -8px;
  }

  .left_price {
    width: calc(100% - 92px);
    height: 100%;
    line-height: 44px;
    float: left;
    text-indent: 80px;
    color: #009900;
  }

  .go_cash {
    width: 92px;
    text-align: center;
    height: 100%;
    background: #66cc99;
    float: left;
    line-height: 44px;
    color: white;
  }
</style>
