package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname AmoyActivityEnroll
 * @Description
 * @Date 2020-08-19 17:19
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("amoy_activity_enroll")
public class AmoyActivityEnroll implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户手机号")
    private String phone;
    @ApiModelProperty("报名时间")
    private LocalDateTime enrollTime;
    @ApiModelProperty("报名状态, 0待付款，1支付中，2已完成，3已取消")
    private Integer enrollStatus;
    @ApiModelProperty("付费金额")
    private BigDecimal amount;
    @ApiModelProperty("是否退款 0否，1退款中, 2已退款")
    private Integer refund;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;
    @ApiModelProperty("第三方支付编号")
    private String payTradeNo;
    @ApiModelProperty("0客户报名，1后台报名")
    private Integer enrollType;
    @ApiModelProperty("常驻地址")
    private String address;
    @ApiModelProperty("职业")
    private String occupation;
    @ApiModelProperty("上级姓名")
    private String leaderName;
    @ApiModelProperty("上级手机号")
    private String leaderPhone;
    @ApiModelProperty("所属团队")
    private String team;
    @ApiModelProperty("住宿")
    private String accommodation;
    @ApiModelProperty("是否参加9月6号星光闪耀活力跑，1参加，2不参加")
    private Integer joinEnergyRun;
    @ApiModelProperty("身份证号")
    private String idCard;
    @ApiModelProperty("用户特殊情况说明")
    private String specialDesc;
    @ApiModelProperty("是否带其他人参加，0否，1带其他人通行")
    private Integer isSomeoneElse;
    @ApiModelProperty("同行人数")
    private Integer someoneElseNumber;
    @ApiModelProperty("备注说明")
    private String remark;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @Version
    private Long version;
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

}
