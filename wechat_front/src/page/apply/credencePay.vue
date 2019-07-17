<template>
    <div>
        <div class="allMoney">
            <div>付款金额：{{fee}}</div>
            <div>元</div>
        </div>
        <div class="info">
            请上传付款凭证：
        </div>
        <div v-if="localData==''" class="credence" @click="showPop">
            添加凭证
        </div>
        <img v-else :src="localData" alt="" class="credence" @click="showPop">
        <div class='btns'>
            <button @click="submit">提交</button>
        </div>
        <van-actionsheet v-model="show" :actions="actions" />
    </div>
</template>

<script>
import { Toast } from "vant"
import {uploadCreditImg} from '@/api/apply'
import {
  createJsapiSignature
} from '@/api/wechat'
export default {
    data() {
        return {
            fee: '',
            show: false,
            localIds: '',
            localData:'',
            base64:'',
            actions: [
                {
                    name: '上传图片',
                    callback: this.submitImg
                },
                {
                    name: '拍摄',
                    callback: this.photo
                },
            ]
        }
    },
    created() {
      this.fee = this.$route.query.money;
      alert(location.href)
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

    },
    methods: {
        showPop() {
            this.show = true;
        },
        submit() {
          if(this.localIds==''){
            Toast('请添加图片')
          }else{
            var base64 = this.localData;
            uploadCreditImg({applyId:this.$route.query.applyId,base64:this.base64}).then(data=>{
              if(data){
                this.$router.replace({ path: 'checking', query: { type: 3 } });
              }else{
                Toast('上传失败!请重新上传!');
              }
            })
          }
        },
        submitImg(item) {
            var that = this;
            this.$wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    that.localIds = res.localIds[0]; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    that.convertToBase64(res.localIds);
                    that.show = false;
                }
            });
        },
        photo(item) {
            var that = this;
            this.$wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    that.localIds = res.localIds[0]; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    that.convertToBase64(res.localIds);
                    that.show = false;
                }
            });
        },
        convertToBase64(localIds){
            let that = this;
            that.$wx.getLocalImgData({
              localId: localIds[0], // 图片的localID
              success: function (res) {
                that.base64 = res.localData;
                if(window.__wxjs_is_wkwebview){
                  that.localData = res.localData; // localData是图片的base64数据，可以用img标签显示
                }else{
                  that.localData = localIds[0];
                }
              }

            });

        }
    },
}
</script>

<style scoped>
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
.info {
  margin-top: 20px;
  height: 40px;
  line-height: 40px;
  font-size: 15px;
  margin-left: 10%;
}
.credence {
  display: block;
  width: 90%;
  margin: 20px auto;
  background: #f3f3f3;
  color: #666666;
  height: 150px;
  line-height: 150px;
  text-align: center;
}
.btns {
  margin: 0 10px;
  margin-top: 50px;
}
.btns button {
  display: block;
  height: 40px;
  line-height: 40px;
  color: white;
  background: mediumseagreen;
  width: 100%;
  margin: 0 auto;
  border: 0;
  font-size: 13px;
  border-radius: 5px;
}
</style>
