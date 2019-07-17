<template>
    <div>
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
                <div></div>
            </div>
        </div>

        <div class="single" @click="$router.push('/myAgent')">
            <img src="../../images/shopmall/agent.png" alt="">
            <div class="info">
                <div>我的代理与加盟</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>

        <div class="single" @click="showEwm=true">
            <img src="../../images/shopmall/recommend.png" alt="">
            <div class="info">
                <div>我要推荐</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>

        <div class="single" @click="$router.push('/recommendRebate')">
            <img src="../../images/shopmall/rebate1.png" alt="">
            <div class="info">
                <div>推荐返利</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>

        <div class="single" @click="$router.push('/agentRebate')">
            <img src="../../images/shopmall/rebate2.png" alt="">
            <div class="info">
                <div>代理返利</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>

        <div class="single" @click="$router.push('/myOrderList')">
            <img src="../../images/shopmall/list.png" alt="">
            <div class="info">
                <div>我的订单</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>

        <div class="single" @click="$router.push('/addressPerson')">
            <img src="../../images/shopmall/address.png" alt="">
            <div class="info">
                <div>收货地址</div>
                <img src="../../images/shopmall/arrow-right.png" alt="">
            </div>
        </div>
        <ewm v-if="showEwm" @close="hidePop"></ewm>
    </div>
</template>
<script>
import ewm from './recommend';
import { mapGetters } from 'vuex';
export default {
    data() {
        return {
            showEwm: false,
        }
    },
    created() {


    },
    mounted() {

    },
    computed: {
        ...mapGetters([
            'user'
        ]),

    },
    methods: {
        hidePop() {
            this.showEwm = false;
        }
    },
    components: {
        ewm
    }
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
  height: 150px;
  background: mediumseagreen;
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  padding-left: 20px;
  justify-content: space-between;
}
.userInfo {
  color: white;
  font-size: 15px;
  width: 70%;
}
.userInfo div span {
  float: left;
}
.userInfo div img {
  float: left;

  width: 20px;
  height: 20px;
  margin-top: 10px;
  margin-left: 5px;
}
.logo {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 1px solid white;
  display: flex;
  align-items: center;
  justify-content: center;
  background: slateblue;
}
.logo img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
}
.userInfo div {
  height: 40px;
  line-height: 40px;
}
.single {
  height: 50px;
  border-bottom: 5px solid #f0f0f0;
  padding-left: 10px;
  padding-right: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #000000;
}
.single > img {
  width: 20px;
  height: 20px;
}
.info {
  width: calc(100% - 30px);
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 15px;
  color: #333333;
}
</style>
