package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname TrainMeetingAffairs
 * @Description 会务活动实体类
 * @Date 2020-07-06 17:33
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("train_meeting_affairs")
public class TrainMeetingAffairs implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("banner图")
    private String meetingImg;

    @ApiModelProperty("会务名称")
    private String meetingName;

    @ApiModelProperty("会务开始时间")
    private LocalDateTime meetingStartTime;

    @ApiModelProperty("会务结束时间")
    private LocalDateTime meetingEndTime;

    @ApiModelProperty("报名截止时间")
    private LocalDateTime enrollEndTime;

    @ApiModelProperty("会务主负责人")
    private String meetingMasterCharge;

    @ApiModelProperty("会务地点")
    private String meetingAddress;

    @ApiModelProperty("会务介绍")
    private String meetingIntroduce;

    @ApiModelProperty("报名条件，0任何人，1总代以上，2一级以上以上，3VIP以上")
    private Integer enrollCondition;

    @ApiModelProperty("报名人数上限")
    private Integer numberOfEnroll;

    @ApiModelProperty("费用")
    private BigDecimal cost;

    @ApiModelProperty("费用退回，签到后退回，0签到后返回账户，1不退")
    private Integer costBack;

    @ApiModelProperty("状态，0待发布，1已发布，2已结束，3已取消")
    private String status;

    @ApiModelProperty("签到二维码")
    private String qrCode;

    @ApiModelProperty("发布时间")
    private LocalDateTime releaseTime;

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
