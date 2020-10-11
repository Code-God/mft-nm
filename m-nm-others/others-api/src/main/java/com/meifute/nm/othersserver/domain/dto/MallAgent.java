package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname MallAgent
 * @Description
 * @Date 2020-08-06 15:16
 * @Created by MR. Xb.Wu
 */
@Data
public class MallAgent implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("代理等级（0，1，2，3，4）")
    private String agentLevel;

    @ApiModelProperty("行业")
    private String agentIndustry;

    @ApiModelProperty("子行业")
    private String agentSubIndustry;

    @ApiModelProperty("代理职业")
    private String agentProfession;

    @ApiModelProperty("学校")
    private String agentSchool;

    @ApiModelProperty("专业")
    private String agentMajor;

    @ApiModelProperty("学历")
    private String agentEducation;

    @ApiModelProperty("单位")
    private String agentCompany;

    @ApiModelProperty("上级节点id")
    private String parentId;

    @ApiModelProperty("通讯地址")
    private String address;

    @ApiModelProperty("推荐人电话")
    private String referrerPhone;

    @ApiModelProperty("推荐人等级")
    private String referrerLevel;

    @ApiModelProperty("推荐人性别")
    private String referrerSex;

    @ApiModelProperty("推荐人年龄 按照生日来计算")
    private String referrerAge;

    @ApiModelProperty("推荐人是否资深")
    private String referrerSenserAdviser;

    @ApiModelProperty("推荐人居住地址")
    private String referrerAddress;

    @ApiModelProperty("推荐人省")
    private String referrerProvince;

    private String myCompanyName;
    @ApiModelProperty("退总代时间")
    private Date backRoleDate;
    @ApiModelProperty("申请退总代时间")
    private Date applyBackRoleDate;
    @ApiModelProperty("升级总代时间")
    private Date upRoleDate;
    @ApiModelProperty("商务编号")
    private String adminCode;

}
