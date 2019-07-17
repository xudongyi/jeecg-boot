<template>
  <div class="myContainer">
    <div class="main">
      <div class="top">
        <div class="logo">
          <img src="../../images/shopmall/shoppingmark.png" alt="">
        </div>
        <div class="userInfo">
          <div>{{user.userName}}</div>
          <div>{{birthday}}</div>
        </div>
      </div>

      <div class="ewm">
        <div id="qrcode" style="width: 180px;height: 180px"></div>
        <div>扫一扫上面的二维码，填入推荐人编号</div>
      </div>
      <div class="code">
        {{user.qrcode}}
      </div>
    </div>

    <div class="delete">
      <img src="../../images/shopmall/popDelete.png" alt="" @click="hidePop">
    </div>
  </div>
</template>

<script>
import QRCode from 'qrcodejs2'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      birthday: '',
    }
  },
  created() {
    this.birthday = (this.user.birthday != '' && this.user.birthday != undefined) ? new Date(this.user.birthday).Format('yyyy-MM-dd') : '';
  },
  mounted() {
    this.qrcode();
  },
  methods: {
    qrcode() {
      let qrcode = new QRCode('qrcode', {
        width: 180,
        height: 180, // 高度
        text: this.user.qrcode, // 二维码内容
      })
      console.log(qrcode)
    },
    hidePop() {
      this.$emit("close", false);
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])

  },
  components: {}
}
</script>

<style scoped>
.myContainer {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.main {
  width: 80%;
  height: 500px;
  background: white;
}

.top {
  height: 150px;
  display: flex;
  align-items: center;
  padding-left: 20px;
  justify-content: space-between;
}

.userInfo {
  font-size: 14px;
  width: 70%;
}

.logo {
  width: 80px;
  height: 80px;
  border: 1px solid white;
  display: flex;
  align-items: center;
  justify-content: center;
  background: slateblue;
}

.userInfo div {
  height: 40px;
  line-height: 40px;
  font-weight: bold;
}

.ewm img {
  width: 180px;
  height: 180px;
  display: block;
  margin: 0 auto;
}

.ewm div {
  text-align: center;
  font-size: 12px;
  margin: 20px auto;
}

.code {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #4b0;
}

.delete {
  position: absolute;
  bottom: 20px;
  width: 40px;
  height: 40px;
  background: white;
  border-radius: 50%;
}
.delete img {
  width: 100%;
  height: 100%;
}
</style>
