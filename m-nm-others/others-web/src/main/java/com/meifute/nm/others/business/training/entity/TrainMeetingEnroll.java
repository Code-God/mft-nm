package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname TrainMeetingEnroll
 * @Description 报名数据表
 * @Date 2020-07-07 10:15
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("train_meeting_enroll")
public class TrainMeetingEnroll implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("会务id")
    private String meetingId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("商务编号")
    private String adminCode;

    @ApiModelProperty("报名时间")
    private LocalDateTime enrollTime;

    @ApiModelProperty("上级id")
    private String leaderUserId;

    @ApiModelProperty("所在城市")
    private String city;

    @ApiModelProperty("报名成功，0未成功，1已成功")
    private String enrollStatus;

    @ApiModelProperty("是否签到 0未签到，1已签到")
    private String status;

    @ApiModelProperty("费用退回，0未退款，1退款中，2已退款")
    private String costBacked;

    @ApiModelProperty("费用")
    private BigDecimal cost;

    @ApiModelProperty("costBackedTime")
    private LocalDateTime costBackedTime;

    @ApiModelProperty("备注说明")
    private String remark;

    @ApiModelProperty("是否支付，0未支付，1已支付")
    private String payStatus;

    @ApiModelProperty("支付方式")
    private String payType;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("第三方支付编号")
    private String payTradeNo;

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

    @TableField(exist = false)
    @ApiModelProperty("支付方式")
    private List<Integer> payTypes;

    @TableField(exist = false)
    private MallUser user;

    @TableField(exist = false)
    private MallUser leaderUser;

    @TableField(exist = false)
    @ApiModelProperty("会务名称")
    private String meetingName;

    @TableField(exist = false)
    @ApiModelProperty("会务开始时间")
    private LocalDateTime meetingStartTime;

    @TableField(exist = false)
    @ApiModelProperty("会务结束时间")
    private LocalDateTime meetingEndTime;

    @TableField(exist = false)
    @ApiModelProperty("报名截止时间")
    private LocalDateTime enrollEndTime;

    @TableField("close_status")
    @ApiModelProperty("0正常。1已取消")
    private String closeStatus;
}
