import axios from "axios";
import Qs from "qs";
import config from "../../config/dev.env.js"
let http,isDebug = true;
let imgPath = config.domainUrl+"/mobile/";//图片服务地址
http = axios.create({
  baseURL: config.domainUrl + "/jeecg-boot",
  transformRequest: [function (data) {
    data = Qs.stringify(data);
    return data;
  }],
});

initInterceptors(http);
function initInterceptors(instance) {
  instance.interceptors.request.use(function (config) {
    //在发送请求之前做某事
    if (config.data) {
      if (isDebug) {
        console.log("请求参数:", config.data)
      }
    }
    return config;
  }, function (error) {
    console.log("请求发生异常!", error);
    return Promise.reject(error);
  });

  instance.interceptors.response.use(function (response) {
    if (response) {
      var status = response.status;
      if (status === 200 || status === 304) {
        if (isDebug) {
          console.log("请求url:" + response.config.url);
          console.log("返回结果:", response.data)
        }
        return response.data;
      } else if (status == 500) {
        alert("服务器内部错误!")
      } else {
        console.log("response结果:", response)
      }
      return response.data;
    }
  }, function (error) {
    //请求错误时做些事
    if (!isEmptyObject(error)) {
      console.log("发生异常:", error)
    }
    return Promise.reject(error);
  });
}

function isEmptyObject(e) {
  var t;
  for (t in e)
    return !1;
  return !0
}
export { http, imgPath};
