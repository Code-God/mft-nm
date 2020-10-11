package com.meifute.nm.othersserver.domain.vo.amoy;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname AmoyActivityEnrollAppVO
 * @Description
 * @Date 2020-08-20 10:44
 * @Created by MR. Xb.Wu
 */
@Data
public class AmoyActivityEnrollAppVO implements Serializable {

    @ApiModelProperty(value = "报名id", required = true)
    private String id;
    @ApiModelProperty(value = "代理姓名", required = true)
    private String name;
    @ApiModelProperty(value = "代理手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "报名时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollTime;
    @ApiModelProperty("报名状态, 0待付款，1支付中，2已完成，3已取消")
    private Integer enrollStatus;
    @ApiModelProperty("付费金额")
    private BigDecimal amount;
    @ApiModelProperty("是否退款 0否，1退款中, 2已退款")
    private Integer refund;
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
    @ApiModelProperty("0客户报名，1后台报名")
    private Integer enrollType;
    @ApiModelProperty(value = "同行人信息")
    private List<AmoyActivitySomeoneVO> someoneVOS;
}
