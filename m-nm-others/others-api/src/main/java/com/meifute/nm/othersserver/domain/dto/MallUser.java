package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"authorities"})
public class MallUser implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    //等级 0普通用户，1一级代理，2二级代理，3三级代理，4总代理
    @ApiModelProperty(value = "级别")
    private String roleId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机")
    private String phone;


    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "职业")
    private String profession;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "微信登录ID")
    private String unionId;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "推荐人ID")
    private String referrerId;

    @ApiModelProperty(value = "推荐人名字")
    private String referrerName;

    @ApiModelProperty(value = "推荐人昵称")
    private String referrerNickName;

    @ApiModelProperty(value = "推荐人手机")
    private String referrerPhone;


    @ApiModelProperty(value = "推荐人等级")
    private String referrerLevel;

    @ApiModelProperty(value = "推荐人头像")
    private String referrerIcon;


    @ApiModelProperty(value = "推送标志")
    private String cid;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "最后更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "最后升级时间")
    private Date upgradeDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否被锁定 0=正常 1=被锁定")
    private String lock;

    @ApiModelProperty(value = "用户状态  0=正常 1=禁用  2=未完善个人信息")
    private String status;

    @ApiModelProperty(value = "是否实名制")
    private Boolean isRealName;

    private String isDel;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "微信号")
    private String weChat;

    @ApiModelProperty(value = "是否退代，0是，1否")
    private String backFlag;
    @ApiModelProperty(value = "区")
    private String cityArea;

    @ApiModelProperty(value = "历史手机号最多保留五个")
    private String historyPhoneNum;

    @ApiModelProperty(value = "最后一次登录时间")
    private Date lastLoginDate;

    @ApiModelProperty(value = "夫妻号名称")
    private String couplesAccounts;

    @ApiModelProperty(value = "座机号")
    private String landLineNumber;

    @ApiModelProperty(value = "是否为测试账号0是1否")
    private String isTestUser;

    @ApiModelProperty(value = "是否开启退贷申请")
    private Boolean openBack;

    @ApiModelProperty(value = "是否为资深顾问,0是，1否")
    private String Senior;

}
