<template>
  <div class="myContainer">
    <div class="top">
      <div class="logo">
        <img :src="user.headimgUrl" alt="">
      </div>
      <div class="userInfo">
        <div>
          <span>{{user.nickname}}</span>
          <img v-if="user.sex==1" src="../../images/shopmall/man1.png" alt="">
          <img v-else src="../../images/shopmall/women.png" alt="">
        </div>
        <div>{{birthday}}</div>
      </div>
    </div>
    <div class="info">您有以下代理申请需要付款</div>
    <div class="allApply">
      <div>
        <span>区域：</span>
        <span>{{newApply.area}}</span>
      </div>
      <div>
        <span>费用：</span>
        <span>{{newApply.applyFee}}</span>
      </div>

    </div>
    <div class="allMoney">
      <div>合计：{{newApply.applyFee}}</div>
      <div>元</div>
    </div>
    <div class="info">
      请选择付款方式
    </div>
    <div class="allApply type">
      <van-radio-group v-model="payType">
        <van-radio :name="1" class="check">微信支付
          <span class="grey">在线支付，即刻完成付款</span>
        </van-radio>
        <van-radio :name="2" class="check">凭证支付
          <span class="grey">上传支付凭证，等待人工审核</span>
        </van-radio>
      </van-radio-group>
    </div>

    <div class="card" v-if="payType==2">
      <div class="line1">
        线下打款账户
      </div>
      <div class="line2">
        <span>户名</span>
        <span>{{cardInfo.cardName}}</span>
      </div>
      <div class="line2">
        <span>账号</span>
        <span>{{cardInfo.cardNo}}</span>
      </div>
    </div>
    <div class="btns">
      <button @click="sure">{{payType == 2 ? '去上传' : '去支付'}}</button>
    </div>
  </div>
</template>

<script>
  import {Toast} from 'vant';
  import {applyPay} from "@/api/wechat.js"
  import {mapGetters} from 'vuex'
  import localip from 'externalip'
  export default {
    data() {
      return {
        applyList: [],
        birthday: '',
        type: '',
        payType: 1,
        cardInfo: {},
        newApply: {},
        ip: '0.0.0.0',
        fee: ''
      }
    },
    created() {
      //判断加盟商还是代理商
      this.type = this.$route.query.type ? this.$route.query.type : 1;
      //获取用户信息
      this.birthday = this.user.birthday ? new Date(this.user.birthday).Format('yyyy-MM-dd') : '';
      //获取等待审核的记录
      http.get('api/apply/getNewApply.do', {
        params: {
          userId: this.user.id,
          type: this.type
        }
      }).then(data => {
        this.newApply = data;
      });

      ///获取线下打款的信息
      http.get('api/apply/getPlatformInfo.do', {
        params: {
          userId: this.user.id,
          type: this.type
        }
      }).then(data => {
        this.cardInfo = data;
      })
    },
    computed: {
      ...mapGetters([
        'user'
      ])

    },
    methods: {
      sure() {
        if (this.payType == 1) {
          //微信支付
          let prepareParam = {
            applyInfo: JSON.stringify(this.newApply),
            body: this.user.userName + this.newApply.area + "的申请",
            ip: this.ip,
            openid: this.user.openId
          }
          var that = this;
          applyPay(prepareParam).then(data => {
            WeixinJSBridge.invoke(
              'getBrandWCPayRequest', data,
              function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                  //支付成功跳转到
                  that.$router.replace({path: "/myAgent"})
                } else {
                  alert('支付失败')
                }
              }
            );
          })

        } else if (this.payType == 2) {
          //凭证支付
          this.$router.replace({path: '/credencePay', query: {money: this.newApply.applyFee,applyId:this.newApply.id}});
        }


      }
    },
  }
</script>

<style scoped>
  .myContainer {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    padding-bottom: 40px;
    overflow-y: scroll;
  }

  .top {
    height: 100px;
    background: mediumseagreen;
    background-size: 100% 100%;
    display: flex;
    align-items: center;
    padding-left: 20px;
    justify-content: center;
  }

  .userInfo {
    color: white;
    font-size: 15px;
    width: 70%;
    margin-left: 5%;
  }

  .userInfo div span {
    float: left;
  }

  .userInfo div img {
    float: left;
    width: 20px;
    height: 20px;
    margin-top: 6px;
    margin-left: 5px;
  }

  .logo {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 1px solid white;
    display: flex;
    align-items: center;
    justify-content: center;
    background: slateblue;
  }

  .logo img {
    width: 60px;
    height: 60px;
    border-radius: 50%;
  }

  .userInfo div {
    height: 30px;
    line-height: 30px;
  }

  .info {
    margin-top: 10px;
    height: 40px;
    line-height: 40px;
    font-size: 15px;
    margin-left: 5%;
  }

  .allApply {
    margin-left: 30px;
    font-size: 13px;
  }

  .check {
    margin-bottom: 10px;
  }

  .type .check {
    margin-bottom: 30px;
  }

  .allMoney {
    padding-left: 10px;
    padding-right: 10px;
    height: 40px;
    line-height: 40px;
    background: mediumseagreen;
    color: white;
    display: flex;
    justify-content: space-between;
  }

  .card {
    width: 90%;
    height: 130px;
    margin: 10px auto;
    border-radius: 5px;
    background: #0d97f3;
    color: white;
    padding-top: 10px;
    box-sizing: border-box;
  }

  .line1 {
    font-size: 13px;
    font-weight: bold;
    padding-left: 10px;
    height: 20px;
    line-height: 20px;
    margin-bottom: 10px;
    font-size: 15px;
  }

  .line2 {
    font-size: 14px;
    padding-left: 10px;
    height: 40px;
    line-height: 40px;
  }

  .line2 span:first-child {
    /* color: #666666; */
    margin-right: 10px;
  }

  .btns {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 40px;
  }

  .btns button {
    height: 100%;
    width: 100%;
    border: 0;
    color: white;
    background: rgb(9, 165, 226);
  }

  .grey {
    color: grey;
    margin-left: 20px;
  }
</style>
