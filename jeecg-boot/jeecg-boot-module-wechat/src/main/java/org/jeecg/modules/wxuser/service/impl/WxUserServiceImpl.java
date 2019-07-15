package org.jeecg.modules.wxuser.service.impl;

import org.jeecg.modules.wxuser.entity.WxUser;
import org.jeecg.modules.wxuser.mapper.WxUserMapper;
import org.jeecg.modules.wxuser.service.IWxUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2019-07-15
 * @Version: V1.0
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

}
