import {
  http
} from '../plugins/http'
import config from "../../config/dev.env.js"
import '../plugins/jquery-1.12.4'
export function getOAuth2UserInfo(params) {
  var url = config.domainUrl + "/jeecg-boot/wechat/getOAuth2UserInfo.do";
  var result;
  $.ajax({
    type: "POST",
    url: url,
    dataType: "json",
    async: false,
    data: params,
    success: function (data) {
      result = data;
    }
  })
  return result;

}
export function createJsapiSignature(params) {
  return http({
    url: '/wx/portal/createJsapiSignature.do',
    method: 'get',
    params: params
  })
}
/**
 * 统一支付接口
 * @param params
 */
export function unifiedOrder(params) {
  return http({
    url: '/wx/portal/unifiedOrder.do',
    method: 'post',
    params
  })
}

/**
 * 代理申请微信支付
 * @param params
 */
export function applyPay(params) {
  return http({
    url: '/wx/portal/applyPay.do',
    method: 'post',
    params
  })
}
