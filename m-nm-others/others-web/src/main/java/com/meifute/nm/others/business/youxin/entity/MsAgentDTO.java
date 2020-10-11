package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: UserInfoDTO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 10:59
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsAgentDTO extends BaseResponse implements Serializable {

    @ApiModelProperty("用户系统，有多个系统时返回")
    private String type;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("用户编号")
    private String number;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("用户等级，0 代表粉丝，1、2、3 依次 代表更高的会员等级")
    private String level;

    @ApiModelProperty("是否顶级，1 代表最高级别，0 代表否")
    private String isTop;

    @ApiModelProperty("邀请人手机号码")
    private String invitorPhone;

    @ApiModelProperty("邀请人用户编号")
    private String invitorNumber;

    @ApiModelProperty("邀请人昵称")
    private String invitorName;

    @ApiModelProperty("邀请人等级")
    private String invitorLevel;

}