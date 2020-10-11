package com.meifute.nm.othersserver.domain.vo.amoy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname AmoyActivityEnrollAppParam
 * @Description
 * @Date 2020-08-19 18:02
 * @Created by MR. Xb.Wu
 */
@Data
public class AmoyActivityEnrollParam implements Serializable {

    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户手机号")
    private String phone;
    @ApiModelProperty(value = "常驻地址",required = true)
    private String address;
    @ApiModelProperty(value = "职业", required = true)
    private String occupation;
    @ApiModelProperty(value = "上级姓名", required = true)
    private String leaderName;
    @ApiModelProperty(value = "上级手机号", required = true)
    private String leaderPhone;
    @ApiModelProperty(value = "所属团队", required = true)
    private String team;
    @ApiModelProperty(value = "住宿", required = true)
    private String accommodation;
    @ApiModelProperty("是否参加9月6号星光闪耀活力跑，1参加，2不参加")
    private Integer joinEnergyRun;
    @ApiModelProperty(value = "身份证号", required = true)
    private String idCard;
    @ApiModelProperty(value = "用户特殊情况说明", required = true)
    private String specialDesc;
    @ApiModelProperty(value = "是否带其他人参加，0否，1带其他人同行", required = true)
    private Integer isSomeoneElse;
    @ApiModelProperty(value = "同行人数, 选择带其他人同行时必填")
    private Integer someoneElseNumber;
    @ApiModelProperty(value = "同行人信息")
    private List<AmoyActivitySomeoneVO> someoneVOS;
}
