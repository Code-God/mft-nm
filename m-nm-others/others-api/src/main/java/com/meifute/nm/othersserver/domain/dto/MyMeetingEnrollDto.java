package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname TrainMeetingAffairs
 * @Description 会务活动实体类
 * @Date 2020-07-06 17:33
 * @Created by MR. Xb.Wu
 */
@Data
public class MyMeetingEnrollDto {

    private String id;

    private String meetingId;

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

    @ApiModelProperty("费用")
    private BigDecimal cost;

    @ApiModelProperty("状态，0未报名，1活动已结束，2报名时间已截止，3活动已取消, 4已签到，报名费退回中，5已签到，报名费已退还，6已签到，7已报名")
    private String status;

    private Long version;

    @ApiModelProperty("是否签到 0未签到，1已签到")
    private String singUpStatus;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @ApiModelProperty("费用退回，签到后退回，0签到后返回账户，1不退")
    private Integer costBack;

    @ApiModelProperty("报名时间")
    private LocalDateTime enrollTime;


}
