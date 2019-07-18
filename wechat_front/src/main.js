// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vant from 'vant';
import 'vant/lib/index.css';
import store from "./store/"
import App from './App'
import 'sweetalert2/dist/sweetalert2.min.css'
import swal from 'sweetalert2'
import router from './router'
import wechat from 'weixin-js-sdk'
import {
  getOAuth2UserInfo,
  createJsapiSignature
} from '@/api/wechat'
import {
  http,
  imgPath
} from "./plugins/http"
Vue.use(Vant);
Vue.prototype.$wx = wechat;
Vue.prototype.swal = swal;
Vue.prototype.imgPath = imgPath;
Vue.config.productionTip = false;
window.http = http;
window.imgPath = imgPath;
router.afterEach((router) => {
  document.title = router.meta.title || "";
  if (router.meta.title) {
    document.title = router.meta.title;
    // 如果是 iOS 设备，则使用如下 hack 的写法实现页面标题的更新
    var mobile = navigator.userAgent.toLowerCase()
    if (/iphone|ipad|ipod/.test(mobile)) {
      const hackIframe = document.createElement('iframe');
      hackIframe.style.display = 'none';
      hackIframe.src = 'favicon.ico?r=' + Math.random();
      document.body.appendChild(hackIframe);
      setTimeout(_ => {
        document.body.removeChild(hackIframe)
      }, 300)
    }
  }
});

new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  beforeCreate(){
    Date.prototype.Format = function (fmt) { //author: meizz
      var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
    };
  },
  created() {
    //对地址栏的url进行封装(在#/前加上?为了解决微信支付中无法识别子目录的问题)
    let url = location.href;
    let param = this.queryJson();
    /* alert("url:"+url);
     alert("search:"+location.search);
     alert("param:"+JSON.stringify(param));*/
    if (url.indexOf("/#/") > -1) {
      window.location.replace(url.replace("\/#\/", "\/?#\/"))
    }

    if (param.code) {
      var data = getOAuth2UserInfo(param);
      if (data.datum.id) {
        this.$store.commit('updateUserInfo', data.datum);
      }
    }


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
  },
  methods: {
    queryJson: function () {
      let url = location.href; //获取url中"?"符后的字串
      let theRequest = {};
      if (url.indexOf("?") != -1) {
        for(var i=0;i<url.split("?").length;i++){
          for (let j = 0; j < url.split("?")[i].split("&").length; j++) {
            let str = url.split("?")[i].split("&")[j];
            let strs = str.split("=");
            theRequest[strs[0]] = unescape(strs[1]);
          }
        }
      }
      return theRequest;
    },
  },

  template: '<App/>'
});



