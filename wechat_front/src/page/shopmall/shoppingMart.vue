<template>
  <div class="shopping_market">
    <div class="list_goods">
      <div class="ever_goods" v-for="(obj,index) in shoppingList">
        <div v-if="ele.count!=0" class="ever_type" v-for="(ele,index1) in  obj.goodsList"
             @touchstart="touchStart(index,index1)" @touchend="touchEnd">
          <div class="pick_area" @click="pickGoPrice(index,index1)">
            <span :class="{pickStyle:ele.show,normalStyle:!ele.show}"></span>
          </div>
          <div class="left_ever">
            <img :src="imgPath+ele.GOODS_IMG" alt="商品图片"/>
          </div>
          <div class="right_ever">
            <span>{{ele.GOODS_NAME}}</span>
            <div class="bottom_right">
              <ul>
                <li>{{ele.count}}{{ele.GOODS_SPEC}}</li>
              </ul>
              <span>¥{{ele.SALE_PRICE}}</span>
              <div class="num_change">
                <span class="img"><img @click="decreaseNum(index,index1)" v-show="ele.count!=0"
                                       src="../../images/shopmall/mart_jian.png"/></span>
                <span v-show="ele.count!=0">{{ele.count}}</span>
                <span class="img"><img @click="increaseNum(index,index1)"
                                       src="../../images/shopmall/mark_jia.png"/></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="goCash">
      <div class="pick_area" @click="pickAll">
        <span :class="{pickStyle:allPick,normalStyle:!allPick}"></span>
      </div>
      <span>全选</span>
      <div class="total_price">
        <span>合计:</span>
        <span>¥{{totalPrice}}</span>
      </div>
      <div class="go_price" @click="goCash">
        去结算
      </div>
    </div>
  </div>
</template>
<script>
  import {Dialog} from 'vant';
  import {Toast} from "vant"
  export default{
    data(){
      return {
        shoppingList: [],
        allPick: false,
        totalPrice: 0,
        totalNum:0,
        timeOut: 0,
        lastArray:[]
      }
    },
    created() {

      this.shoppingList = this.$store.state.storeData.shopGoods;
      this.filerArray();
      console.log(this.shoppingList)
    },
    mounted() {

    },
    beforeRouterLeave(){

    },
    methods: {
      pickGoPrice(index, index1){
        this.shoppingList[index].goodsList[index1].show = !this.shoppingList[index].goodsList[index1].show;
        this.filerArray();
      },
      increaseNum(index, index1){
        this.shoppingList[index].goodsList[index1].count++;
        this.filerArray();
        this.$store.commit("updateShopGoods", this.shoppingList);
        this.$store.commit("updateCashGoods",this.lastArray)
        this.$store.commit("updateCashNum",this.totalNum)
        this.$store.commit("updateCashPrice",this.totalPrice)
      },
      decreaseNum(index, index1){
        this.shoppingList[index].goodsList[index1].count--;
        this.filerArray();
        this.$store.commit("updateShopGoods", this.shoppingList)
        this.$store.commit("updateCashGoods",this.lastArray)
        this.$store.commit("updateCashNum",this.totalNum)
        this.$store.commit("updateCashPrice",this.totalPrice)

      },
      filerArray(){
      	console.log(this.shoppingList)
        this.totalPrice = 0;
        this.totalNum = 0;
        this.lastArray=[];
        for (var i = 0; i < this.shoppingList.length; i++) {
          for (var j = 0; j < this.shoppingList[i].goodsList.length; j++) {
            if (this.shoppingList[i].goodsList[j].show == true&&this.shoppingList[i].goodsList[j].count!=0) {
              this.totalPrice = this.totalPrice + this.shoppingList[i].goodsList[j].count * this.shoppingList[i].goodsList[j].price
           		this.totalNum=this.totalNum+this.shoppingList[i].goodsList[j].count;
           		this.lastArray.push(this.shoppingList[i].goodsList[j])
            }else{
            	this.shoppingList[i].goodsList[j].show == false;
            }
          }
        }
      },
      pickAll(){
        if (this.allPick == false) {
          this.allPick = true;
          console.log(this.shoppingList)
          for (var i = 0; i < this.shoppingList.length; i++) {
            for (var j = 0; j < this.shoppingList[i].goodsList.length; j++) {
              if (this.shoppingList[i].goodsList[j].count != 0) {
                this.shoppingList[i].goodsList[j].show = true;
              }
            }
          }
        } else {
          this.allPick = false;
          for (var i = 0; i < this.shoppingList.length; i++) {
            for (var j = 0; j < this.shoppingList[i].goodsList.length; j++) {
              if (this.shoppingList[i].goodsList[j].count != 0) {
                this.shoppingList[i].goodsList[j].show = false;
              }
            }
          }
        }
        this.$store.commit("updateShopGoods", this.shoppingList)
        this.filerArray()
      },
      goCash(){
      	this.filerArray();
      	console.log("1")
      	console.log(this.shoppingList)
      		console.log("1")
        if(this.totalNum!=0){
        	console.log(this.lastArray)
      		 this.$router.push({
	          path: "/goCash"
	        });
     			this.$store.commit("updateShopGoods", this.shoppingList);
     			this.$store.commit("updateCashGoods",this.lastArray)
	        this.$store.commit("updateCashNum",this.totalNum)
	        this.$store.commit("updateCashPrice",this.totalPrice)
      	}else{
      		Toast('您还未选购任何商品!');
      	}
      },
      touchStart(index, index1){
        var that = this;
        this.timeOut = setTimeout(function () {
          Dialog.confirm({
            message: '确定删除改商品?'
          }).then(() => {
            that.shoppingList[index].goodsList[index1].count = 0;
            that.$store.commit("updateShopGoods", that.shoppingList)
          });
        }, 1000)
      },
      touchEnd(){
        clearTimeout(this.timeOut)
      }

    },
    components: {}
  }
</script>
<style scoped>
  .pick_area {
    width: 44px;
    height: 100%;
    float: left;
    text-align: center;
    line-height: 44px;
  }

  .shopping_market {
    width: 100%;
    height: 100%;
  }

  .ever_type {
    height: 84px;
    border-bottom: 1px solid #eee;
  }

  .style {
    padding-bottom: 44px;
    width: 100%;
  }

  .ever_type .left_ever {
    width: 80px;
    height: 100%;
    float: left;
  }

  .ever_type .left_ever > img {
    width: 60px;
    height: 60px;
    margin: 12px 20px 12px 0px;
  }

  .right_ever {
    width: calc(100% - 124px);
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
    width: 70px;
    height: 100%;
    float: left;
  }

  .right_ever > .bottom_right ul > li {
    height: 100%;
    float: left;
    font-size: 12px;
    color: #666;
    width: 100%;
  }

  .right_ever > .bottom_right > span {
    color: #009900;
    width: calc(100% - 190px);
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

  .num_change > .img {
    display: inline-block;
    width: 24px;
    height: 24px;
    border: 1px solid #eee;
  }

  .num_change > .img > img {
    width: 18px;
    height: 18px;
    margin: 3px;
  }

  .num_change > .img:first-child {
    margin-left: 20px;
    margin-top: 10px;
    float: left;
  }

  .num_change > .img:last-child {
    margin-right: 10px;
    float: right;
    margin-top: 10px;
  }

  .num_change > span {
    display: inline-block;
    width: calc(100% - 82px);
    text-align: center;
    height: 24px;
    line-height: 24px;
    margin-top: 10px;
    vertical-align: top;
    float: left;
    border-top: 1px solid #EEEEEE;
    border-bottom: 1px solid #eee;
    color: #009900;
  }

  .pick_area > span {
    height: 18px;
    width: 18px;
    display: inline-block;
    vertical-align: top;
    margin-top: 33px;
    border-radius: 50%;
    background-size: 18px 18px;
  }

  .pickStyle {
    background: url(../../images/shopmall/xz.png) no-repeat center;
  }

  .normalStyle {
    background: url(../../images/shopmall/wxz.png) no-repeat center;
  }

  .list_goods {
    height: calc(100% - 44px);
    overflow: scroll;
  }

  .goCash {
    width: 100%;
    height: 44px;
    position: fixed;
    bottom: 0px;
    border-top: 1px solid #eee;
  }

  .goCash .pick_area {
    width: 31px;
    float: left;
  }

  .goCash .pick_area > span {
    height: 18px;
    width: 18px;
    display: inline-block;
    vertical-align: top;
    margin-top: 13px;
    margin-left: 13px;
    border-radius: 50%;
    background-size: 18px 18px;
  }

  .goCash > span {
    width: 36px;
    display: inline-block;
    float: left;
    height: 44px;
    line-height: 44px;
    font-size: 14px;
    text-align: center;
  }

  .total_price {
    width: calc(100% - 179px);
    height: 44px;
    line-height: 44px;
    float: left;
    font-size: 14px;
    margin-left: 20px;
  }

  .total_price > span:first-child {
    width: 36px;
    display: inline-block;
    height: 100%;
    text-align: center;
    float: left;
  }

  .total_price > span:last-child {
    width: calc(100% - 36px);
    display: inline-block;
    height: 100%;
    float: left;
    text-align: left;
    color: red;
    font-size: 16px;
    text-indent: 10px;
    font-weight: 700;
  }

  .go_price {
    width: 92px;
    height: 44px;
    float: left;
    line-height: 44px;
    background: #FF0000;
    text-align: center;
    color: white;
  }
</style>
