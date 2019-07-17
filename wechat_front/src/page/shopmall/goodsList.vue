<template>
  <div class="goodList">
    <div v-if="showContent==1" class="ever_type_css" v-for="(obj,index) in goodList">
      <div v-if="ele.show&&ele.count>0" class="ever_type" v-for="(ele,index1) in obj.goodsList">
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
              <span>x{{ele.count}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div  class="ever_type_css" v-if="showContent==2">
      <div class="ever_type" v-for="(ele,index) in  lastArray">
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
              <span>x{{ele.counts}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  export default{
    data(){
      return {
        goodList: [],
        lastArray:[],
        showContent:-1
      }
    },
    created() {


			 this.showContent=this.$route.query.showContent;
			 if(this.showContent==1){
			 	this.goodList = this.$route.query.zsList;
			 }else{
			 	this.lastArray = this.$route.query.zsList;
			 }

			 console.log(this.goodList)
    },
    beforeRouteEnter(to,from,next){
    	next(vm =>{
//  		var that=vm;
//  		console.log(from)
//  		if(from.path=="/goCash"){
//
//  		}else{
//  			 that.goodList = that.$route.query.zsList;
//  			 that.showContent=that.$route.query.showContent;
//
//  		}
    	})
    },
    mounted() {

    },
    methods: {},
    components: {}
  }
</script>
<style scoped>
  .goodList {
    height: 100%;
    overflow: scroll;
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
    width: calc(100% - 20px);
    text-align: right;
    height: 44px;
    line-height: 44px;
    vertical-align: top;
    float: left;
    margin-right: 20px;
  }

  .bottom_go_shoppingmarket {
    height: 44px;
    width: 100%;
    position: fixed;
    bottom: 0px;
    background: #000000;
  }
</style>

