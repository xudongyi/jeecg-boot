<template>
  <div class="myContainer">
    <div class="line">
      <div class="label">
        申请人：
      </div>
      <div class="right">
        <input class="input" type="text" v-model="user.userName" placeholder="请填写申请人名称，不超过14个字">
      </div>
    </div>

    <div class="line">
      <div class="label">
        联系方式：
      </div>
      <div class="right">
        <input class="input" type="number" v-model.number="user.mobile" placeholder="请填写11位手机号">
      </div>
    </div>


    <div class="line">
      <div class="label">
        性别：
      </div>
      <div class="right">
        <van-radio-group v-model="user.sex" class="radio-group">
          <van-radio :name="1" class="inline-radio">男</van-radio>
          <van-radio :name="2" class="inline-radio">女</van-radio>
        </van-radio-group>
      </div>
    </div>

    <div class="line">
      <div class="label">
        出生年月：
      </div>
      <div class="right">
        <input v-model="birthday" unselectable="on" class="input" type="text" readonly="true" placeholder="请选择时间"
               @click="pickTime">
      </div>
    </div>

    <div class="line">
      <div class="label">
        推荐人：
      </div>
      <div class="right">
    <span class="fromUser">
    {{fromUser}}
    </span>
        <van-button class="scanBtn" @click="scanQr" slot="button" size="small" type="primary">扫码</van-button>
      </div>
    </div>

    <div class="line">
      <div class="label">
        {{type == 1 ? '代理' : '加盟'}}区域：
      </div>
      <div class="right">
        <input v-model="area" unselectable="on" class="input" type="text" readonly="true" placeholder="请选择区域"
               @click="pickArea">
      </div>
    </div>

    <div class="line">
      <div class="big_label">
        选择品牌：
      </div>
      <div class="right">
        <div class="rightCheck">
          <van-checkbox v-for="(ele,index) in brands" class="check" :key="index" v-model="ele.selected">
            {{ele.brandName + ' ' + ele.agentFee}}
          </van-checkbox>
        </div>
      </div>
    </div>

    <div class="line">
      <div class="label">
        {{type == 1 ? '代理' : '加盟'}}费用：
      </div>
      <div class="right">
        <input class="input" type="text" readonly="true" v-model="agentFee">
      </div>
    </div>

    <div class="line">
      <div class="big_label">
        备注：
      </div>
      <div class="right">
        <textarea name="" id="" cols="30" rows="10" v-model="user.remark"></textarea>
      </div>
    </div>
    <div class="btns">
      <button @click="apply">
        提交申请
      </button>
    </div>

    <transition name="fade">
      <van-datetime-picker v-show="showTime" class="time-class" @confirm="handleConfirm" @cancel="handleCancer"
                           v-model="currentDate" type="date" :min-date="minDate" :max-date="maxDate"/>
    </transition>
    <transition name="fade">
      <van-area class="time-class" v-show="showArea" @confirm="handleAreaConfirm" @cancel="handleAreaCancer"
                :area-list="areaList"/>
    </transition>

  </div>
</template>

<script>
  import areaList from "@/plugins/area"
  import {Toast} from 'vant';
  import {mapGetters} from 'vuex'
  import {
    getOAuth2UserInfo,
    createJsapiSignature
  } from '@/api/wechat'
  export default {
    data() {
      return {
        type: 1,
        showTime: false,
        showArea: false,
        minDate: new Date(1900, 1, 1),
        maxDate: new Date(),
        currentDate: new Date(),
        areaList: areaList,
        birthday: "",
        area: "",
        areaCode: "",
        brands: [],
        fromId: '',
        fromUser: '',
      }
    },
    beforeRouteEnter: (to, from, next) => {
      next()
    },
    created() {
      this.type = this.$route.query.type ? this.$route.query.type : 1;
      this.birthday = this.user.birthday?new Date(this.user.birthday).Format("yyyy-MM-dd"):'';
      if(!window.__wxjs_is_wkwebview){
        createJsapiSignature({
          url: location.href
        }).then(data => {
          this.$wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: data.appId, // 必填，公众号的唯一标识
            timestamp: data.timestamp, // 必填，生成签名的时间戳
            nonceStr: data.nonceStr, // 必填，生成签名的随机串
            signature: data.signature, // 必填，签名，见附录1
            jsApiList: [
              'checkJsApi',
              'onMenuShareTimeline',
              'onMenuShareAppMessage',
              'onMenuShareQQ',
              'onMenuShareWeibo',
              'onMenuShareQZone',
              'hideMenuItems',
              'showMenuItems',
              'hideAllNonBaseMenuItem',
              'showAllNonBaseMenuItem',
              'translateVoice',
              'startRecord',
              'stopRecord',
              'onVoiceRecordEnd',
              'playVoice',
              'onVoicePlayEnd',
              'pauseVoice',
              'stopVoice',
              'uploadVoice',
              'downloadVoice',
              'chooseImage',
              'previewImage',
              'uploadImage',
              'getLocalImgData',
              'downloadImage',
              'getNetworkType',
              'openLocation',
              'getLocation',
              'hideOptionMenu',
              'showOptionMenu',
              'closeWindow',
              'scanQRCode',
              'chooseWXPay',
              'openProductSpecificView',
              'addCard',
              'chooseCard',
              'openCard'
            ]
          });
        });
      }

      this.birthday = this.user.birthday ? new Date(this.user.birthday).Format("yyyy-MM-dd") : '';
    },
    watch: {
      user: {
        handler(v){
          if (v.userName.length > 14) {
            Toast('您输入的用户名过长');
          }
        },
        deep:true
      }
    },
    computed: {
      ...mapGetters([
        'user'
      ]),
      agentFee() {
        let sum = 0;
        for (let val of this.brands) {
          if (val.selected == true) {
            sum += val.agentFee;
          }
        }
        return sum;
      }
    },
    methods: {
      //获取区域下的全部品牌
      getBrands(areacode) {
        http.get('api/apply/list.do', {
          params: {
            userId: this.user.id,
            areaCode: areacode
          }
        }).then(data => {
          this.brands = data;
        })
      },
      pickTime: function () {
        this.showArea = false;
        this.showTime = true;
      },
      handleConfirm: function (val) {
        this.showTime = false;
        this.birthday = val.Format("yyyy-MM-dd");
      },
      handleCancer: function () {
        this.showTime = false;
      },
      pickArea: function () {
        this.showTime = false;
        this.showArea = true;
      },

      handleAreaConfirm: function (val) {
        if (val[0].name.indexOf("选择") > -1 || val[1].name.indexOf("选择") > -1 || val[2].name.indexOf("选择") > -1) {
          Toast('请选择完成的地址');
          return;
        }
        this.area = val[0].name + '-' + val[1].name + '-' + val[2].name;
        this.areaCode = val[2].code;
        this.getBrands(this.areaCode);
        this.showArea = false;
      },
      handleAreaCancer: function () {
        this.showArea = false;
      },
      scanQr: function () {
        var that = this;
        this.$wx.scanQRCode({
          needResult: 1,
          desc: 'scanQRCode desc',
          success: function (res) {
            http.get('api/apply/getRecommendUser.do', {
              params: {
                qrcode: res.resultStr
              }
            }).then(data => {
              that.fromId = data.id;
              that.fromUser = data.userName;
            })
          }
        });
      },
      checkInput() {
        if (this.user.username == '' || this.user.mobile == '' || this.birthday == '' || this.area == '' || this.areaCode == '' || this.fromId == '') {
          return false;
        } else {
          return true;
        }
      },
      apply() {
        if (this.checkInput()) {
          var brandIds = [];
          for (let val of this.brands) {
            if (val.selected == true) {
              brandIds.push(val.id)
            }
          }
          if (brandIds.length > 0) {

            var obj = {};
            obj.userId = this.user.id;
            obj.area = this.area;
            obj.areaCode = this.areaCode;
            obj.applyFee = this.agentFee;
            obj.remark = this.user.remark;
            obj.type = this.type;
            obj.type = this.type;
            var userInfo = {};
            userInfo.id = this.user.id;
            userInfo.userName = this.user.userName;
            userInfo.birthday = this.birthday;
            userInfo.sex = this.user.sex;
            userInfo.mobile = this.user.mobile;
            userInfo.fromId = this.fromId;


            http.get('api/apply/saveApply.do', {
              params: {
                json: JSON.stringify(obj),
                brandIds: brandIds.join(','),
                userInfo: JSON.stringify(userInfo)
              }
            }).then(data => {
              if (data == true) {
                Toast('提交成功');
                setTimeout(() => {
                  Toast.clear();
                  this.$router.replace({path: '/checking', query: {type: this.type}})
                }, 1000)
              } else {
                Toast('提交失败');
              }
            })
          } else {
            Toast('请选择品牌');
          }
        } else {
          Toast('您还有未填项');
        }

      }
    },
  }
</script>

<style scoped>
  .myContainer {
    background: #f0f0f0;
    padding-top: 10px;
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    overflow-y: scroll;
  }

  .line {
    background: white;
    overflow: hidden;
    margin: 0px 5px;
    margin-bottom: 5px;
    position: relative;
  }

  .label {
    float: left;
    width: 22%;
    height: 40px;
    line-height: 40px;
    color: white;
    background: mediumseagreen;
    text-align: right;
    font-size: 14px;
  }

  .big_label {
    position: absolute;
    width: 22%;
    top: 0;
    bottom: 0;
    left: 0;
    color: white;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    background: mediumseagreen;
    font-size: 14px;
  }

  .right {
    float: right;
    width: 78%;
  }

  .right .input {
    border: hidden;
    height: 40px;
    line-height: 40px;
    width: 100%;
    padding: 0;
    padding-left: 5px;
    color: #666666;
    font-size: 13px;
  }

  .right textarea {
    border: 0;
    height: 100px;
    width: 100%;
    font-size: 13px;
    color: #666666;
    padding: 10px;
    box-sizing: border-box;
  }

  .inline-radio {
    display: inline-block;
  }

  .radio-group {
    margin-top: 8px;
    margin-left: 10px;
  }

  .btns {
    margin: 10px 10px;
    margin-top: 30px;
  }

  .btns button {
    display: block;
    height: 40px;
    line-height: 40px;
    color: white;
    background: rgb(9, 165, 226);
    width: 100%;
    margin: 0 auto;
    border: 0;
    font-size: 16px;
    border-radius: 5px;
  }

  .scanBtn {
    float: right;
    margin-right: 5px;
    margin-top: 5px;
  }

  .rightCheck {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 10px;
    min-height: 40px;
    padding-bottom: 5px;
  }

  .rightCheck .check {
    margin-bottom: 5px;
    font-size: 13px;
  }

  .fade-enter-active {
    transition: 0.5s ease;
  }

  .fade-enter {
    transform: translateY(100px);
  }

  .time-class {
    position: fixed;
    top: 50%;
    bottom: 0;
    width: 100%;
  }

  .fromUser {
    height: 40px;
    line-height: 40px;
    font-size: 13px;
    padding-left: 5px;
  }
</style>
