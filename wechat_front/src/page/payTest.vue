<template>
  <div>
    <input type="text" v-model="fee">
    <button @click="payTest">测试支付</button>
  </div>
</template>
<script>
  import {unifiedOrder} from "@/api/wechat.js"
  export default{
    data(){
      return {
        fee: 1
      }
    },
    created() {
    },
    mounted() {

    },
    methods: {
      payTest: function () {
        var payParam = {
          orders: "{orderNo:'11111222211',totalFee:'0.01'}",
          body: "测试",
          ip: "192.168.10.98",
          openid: "os7_p0cAAKf6qD66Lb7JwnFEfxjc"
        };
        unifiedOrder(payParam).then(data => {
          alert(JSON.stringify(data))
          WeixinJSBridge.invoke(
            'getBrandWCPayRequest', data,
            function (res) {
              alert(JSON.stringify(res))
            }
          );
        })

      }
    },
    components: {}
  }
</script>
<style scoped>

</style>
