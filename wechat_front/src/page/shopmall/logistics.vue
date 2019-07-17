<template>
  <div class="logistics">
    <ul>
      <li><span>发货时间:</span><span>{{this.logistics.deliveryTime==null?"":new Date(this.logistics.deliveryTime).Format("yyyy-MM-dd hh:mm:ss")}}</span></li>
      <li><span>物流公司:</span><span>{{this.logistics.expressType}}</span></li>
      <li><span>物流单号:</span><span>{{this.logistics.expressNo}}</span></li>
    </ul>
  </div>
</template>
<script>
  export default{
    data(){
      return {
      	logistics:{}
      }
    },
    created() {
			this.logistics=this.$route.query.message;
			console.log(this.logistics)
    },
    mounted() {

    },
    methods: {},
    components: {}
  }
</script>
<style scoped>
  .logistics {
    height: 100%;
  }

  .logistics > ul > li {
    height: 44px;
    line-height: 44px;
    width: 100%;
    border-bottom: 1px solid #eee;
  }

  .logistics > ul > li > span:first-child {
    width: 86px;
    text-align: right;
    display: inline-block;
    height: 100%;
    font-size: 15px;
    color: #999;
    float: left;
  }

  .logistics > ul > li > span:last-child {
    width: calc(100% - 86px);
    text-align: left;
    display: inline-block;
    height: 100%;
    font-size: 15px;
    float: left;
    text-indent: 10px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;

  }
</style>

