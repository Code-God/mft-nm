package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname ActivitiesStatisticsDto
 * @Description
 * @Date 2020-08-06 12:05
 * @Created by MR. Xb.Wu
 */
@Data
public class ActivitiesStatisticsDto implements Serializable {

    private String id;

    @ApiModelProperty("代理手机号")
    private String agentPhone;

    @ApiModelProperty("代理姓名")
    private String agentName;

    @ApiModelProperty("所属商贸公司")
    private String companyName;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityTime;

    @ApiModelProperty("活动结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;

    @ApiModelProperty("参会人数")
    private Integer participants = 0;

    @ApiModelProperty("达标人数")
    private Integer qualifiedPerson = 0;

    @ApiModelProperty("付费金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty("达标名额是否有退代")
    private Integer retrogression = 0;

    @ApiModelProperty("是否扣除活动费用")
    private BigDecimal deductedAmount;

    @ApiModelProperty("是否用其他名额抵扣")
    private Integer deductedPerson = 0;

}
