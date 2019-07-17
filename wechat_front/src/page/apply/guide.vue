<template>
  <div>

  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    created() {
      //判断是加盟申请还是代理申请
      var type = this.$route.query.type ? this.$route.query.type : 1;
      //判断是否有等待审核的记录
      http.get('api/apply/getNewApply.do', {
        params: {
          userId: this.user.id,
          type: type
        }
      }).then(res => {
        if (res == '') {
          //可以申请
          this.$router.replace({path: '/agentFirstApply', query: {type: type}});
        } else if (res.applyStatus == 1) {
          //    等待申请审核
          this.$router.replace({path: '/checking', query: {type: type}});
        } else if (res.applyStatus == 2 && res.payStatus == 1) {
          //    等待付款
          this.$router.replace({path: '/agentWaitPay', query: {type: type}})
        } else if (res.applyStatus == 2 && res.payStatus == 2) {
          //    等待支付审核
          this.$router.replace({path: '/checking', query: {type: 3}});

    }

      })

    },
    computed: {
      ...mapGetters([
        'user'
      ]),
    },
    methods: {

    },


  }
</script>

<style scoped>

</style>
