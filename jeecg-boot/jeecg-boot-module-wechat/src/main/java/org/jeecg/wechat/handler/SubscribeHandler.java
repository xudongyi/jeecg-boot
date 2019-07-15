package org.jeecg.wechat.handler;

import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.wxuser.entity.WxUser;
import org.jeecg.modules.wxuser.service.IWxUserService;
import org.jeecg.wechat.builder.TextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class SubscribeHandler extends AbstractHandler {
    @Autowired
    private IWxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // TODO 可以添加关注用户到本地数据库
                //1.增加一张表记录已关注的用户信息
                try {
                    WxUser wxUser = new WxUser();
                    wxUser.setCity(userWxInfo.getCity());
                    wxUser.setCountry(userWxInfo.getCountry());
                    wxUser.setCreateBy("system");
                    wxUser.setCreateTime(new Date());
                    wxUser.setGroupid(userWxInfo.getGroupId());
                    wxUser.setHeadImgUrl(userWxInfo.getHeadImgUrl());
                    wxUser.setLanguage(userWxInfo.getLanguage());
                    wxUser.setNickname(userWxInfo.getNickname());
                    wxUser.setOpenId(userWxInfo.getOpenId());
                    wxUser.setProvince(userWxInfo.getProvince());
                    wxUser.setRemark(userWxInfo.getRemark());
                    wxUser.setQrscene(userWxInfo.getQrScene());
                    wxUser.setQrscenestr(userWxInfo.getQrSceneStr());
                    wxUser.setUnionid(userWxInfo.getUnionId());
                    wxUser.setSex(userWxInfo.getSex());
                    wxUser.setSubscribeTime(new Date());
                    wxUser.setSubscribescene(userWxInfo.getSubscribeScene());
                    wxUserService.save(wxUser);
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }

            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //TODO
        return null;
    }

}
