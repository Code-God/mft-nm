package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname TrainMeetingEnrollDto
 * @Description TODO
 * @Date 2020-07-07 10:31
 * @Created by MR. Xb.Wu
 */
@Data
public class TrainMeetingEnrollDto implements Serializable {

    private String id;

    @ApiModelProperty(value = "会务id",required = true)
    private String meetingId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("商务编号")
    private String adminCode;

    @ApiModelProperty("报名时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime costBackedTime;

    @ApiModelProperty("备注说明")
    private String remark;

    @ApiModelProperty("是否支付，0未支付，1已支付")
    private String payStatus;

    @ApiModelProperty("支付方式")
    private String payType;

    @ApiModelProperty("支付时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("第三方支付编号")
    private String payTradeNo;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long version;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;
}
