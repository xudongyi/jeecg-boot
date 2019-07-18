package org.jeecg.modules.wxuser.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2019-07-15
 * @Version: V1.0
 */
@Data
@TableName("wx_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="wx_user对象", description="微信用户")
public class WxUser extends JeecgEntity {
    
	/**主键id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键id")
	private String id;
	/**openid*/
	@Excel(name = "openid", width = 15)
    @ApiModelProperty(value = "openid")
	private String openId;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
	private String nickname;
	/**sex*/
	@Excel(name = "sex", width = 15)
    @ApiModelProperty(value = "sex")
	private Integer sex;
	/**language*/
	@Excel(name = "language", width = 15)
    @ApiModelProperty(value = "language")
	private String language;
	/**city*/
	@Excel(name = "city", width = 15)
    @ApiModelProperty(value = "city")
	private String city;
	/**province*/
	@Excel(name = "province", width = 15)
    @ApiModelProperty(value = "province")
	private String province;
	/**country*/
	@Excel(name = "country", width = 15)
    @ApiModelProperty(value = "country")
	private String country;
	/**headImgUrl*/
	@Excel(name = "headImgUrl", width = 15)
    @ApiModelProperty(value = "headImgUrl")
	private String headImgUrl;
	/**subscribeTime*/
	@Excel(name = "subscribeTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "subscribeTime")
	private Date subscribeTime;
	/**unionid*/
	@Excel(name = "unionid", width = 15)
    @ApiModelProperty(value = "unionid")
	private String unionid;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
	private String remark;
	/**groupid*/
	@Excel(name = "groupid", width = 15)
    @ApiModelProperty(value = "groupid")
	private Integer groupid;
	/**subscribescene*/
	@Excel(name = "subscribescene", width = 15)
    @ApiModelProperty(value = "subscribescene")
	private String subscribescene;
	/**qrscene*/
	@Excel(name = "qrscene", width = 15)
    @ApiModelProperty(value = "qrscene")
	private String qrscene;
	/**qrscenestr*/
	@Excel(name = "qrscenestr", width = 15)
    @ApiModelProperty(value = "qrscenestr")
	private String qrscenestr;
}
