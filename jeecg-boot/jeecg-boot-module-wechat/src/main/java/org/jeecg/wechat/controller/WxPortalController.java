package org.jeecg.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.wxuser.entity.WxUser;
import org.jeecg.modules.wxuser.service.IWxUserService;
import org.jeecg.wechat.utils.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wx/portal")
public class WxPortalController extends GenericController{
    private final WxMpService wxService;
    private final WxMpMessageRouter messageRouter;
    @Autowired
    private IWxUserService wxUserService;
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
            timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestParam String appid,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }

    @RequestMapping(value = "/redirectWxUrl.do")
    public String redirectWxUrl(String router, String code){
        StringBuilder redirect = new StringBuilder();
        redirect.append("redirect:/wechat/index.html?d="+new Date().getTime());
        if(router!=null){
            if(router.contains("_")){
                String routerPath = router.split("_")[0];
                redirect.append("/?#/").append(routerPath);
                String params = router.substring(router.indexOf("_")+1);
                if(params.length()>0){
                    int i =0;
                    for(String par : params.split("_")){
                        if(i==0){
                            redirect.append("?"+par.split(":")[0]+"="+par.split(":")[1]);
                        }else{
                            redirect.append("&"+par.split(":")[0]+"="+par.split(":")[1]);
                        }
                        i++;
                    }
                }
            }else{
                redirect.append("/?#/").append(router);
            }
        }
        if(code!=null){
            if(router.contains("_")){
                redirect.append("&code="+code);
            }else{
                redirect.append("?code="+code);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return redirect.toString();
    }

    /**
     * 通过openid获得基本用户信息 详情请见:
     * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param openid
     *            openid
     * @param lang
     *            zh_CN, zh_TW, en
     */
    @RequestMapping(value = "/getUserInfo.do")
    public WxMpUser getUserInfo(HttpServletResponse response, @RequestParam(value = "openid") String openid,
                                @RequestParam(value = "lang", defaultValue = "zh_CN") String lang) {
        ReturnModel returnModel = new ReturnModel();
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = this.wxService.getUserService().userInfo(openid, lang);
            returnModel.setResult(true);
            returnModel.setDatum(wxMpUser);
            renderString(response, returnModel);
        } catch (WxErrorException e) {
            returnModel.setResult(false);
            returnModel.setReason(e.getError().toString());
            renderString(response, returnModel);
            log.error(e.getError().toString());
        }
        return wxMpUser;
    }

    /**
     * 通过code获得基本用户信息 详情请见:
     * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param code
     *            code
     * @param lang
     *            zh_CN, zh_TW, en
     */
    @RequestMapping(value = "/getOAuth2UserInfo.do")
    public void getOAuth2UserInfo(HttpServletResponse response, @RequestParam(value = "code") String code,
                                  @RequestParam(value = "lang", defaultValue = "zh_CN") String lang) {
        ReturnModel returnModel = new ReturnModel();
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        try {
            accessToken = this.wxService.oauth2getAccessToken(code);
            wxMpUser = this.wxService.getUserService().userInfo(accessToken.getOpenId(), lang);
            returnModel.setResult(true);
            if (wxMpUser != null) {
                try {
                    QueryWrapper<WxUser> queryWrapper = new QueryWrapper<WxUser>();
                    queryWrapper.eq("open_id", wxMpUser.getOpenId());
                    WxUser user = wxUserService.getOne(queryWrapper);
                    returnModel.setDatum(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            renderString(response, returnModel);
        } catch (WxErrorException e) {
            returnModel.setResult(false);
            returnModel.setReason(e.getError().toString());
            renderString(response, returnModel);
            this.log.error(e.getError().toString());
        }
    }

    /**
     * 用code换取oauth2的openid 详情请见:
     * http://mp.weixin.qq.com/wiki/1/8a5ce6257f1d3b2afb20f83e72b72ce9.html
     *
     * @param code
     *            code
     */
    @RequestMapping(value = "/getOpenid.do")
    public void getOpenid(HttpServletResponse response, @RequestParam(value = "code") String code) {
        ReturnModel returnModel = new ReturnModel();
        WxMpOAuth2AccessToken accessToken;
        this.log.info("code:" + code);
        try {
            accessToken = this.wxService.oauth2getAccessToken(code);
            returnModel.setResult(true);
            returnModel.setDatum(accessToken.getOpenId());
            renderString(response, returnModel);
        } catch (WxErrorException e) {
            returnModel.setResult(false);
            returnModel.setReason(e.getError().toString());
            renderString(response, returnModel);
            this.log.error(e.getError().toString());
        }
    }

    @RequestMapping(value = "/getAccessToken.do")
    public String getAccessToken() {
        try {
            String accessToken = wxService.getAccessToken();
            return accessToken;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getJsapiTicket.do")
    @ResponseBody
    public String getJsapiTicket() {
        try {
            String jsapiTicket = wxService.getJsapiTicket();
            return jsapiTicket;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/createJsapiSignature.do")
    @ResponseBody
    public WxJsapiSignature createJsapiSignature(String url) {
        try {
            WxJsapiSignature signature = wxService.createJsapiSignature(url);
            return signature;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
