package com.meifute.nm.othersserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname ActivitiesStatisticsVo
 * @Description
 * @Date 2020-08-06 12:05
 * @Created by MR. Xb.Wu
 */
@Data
public class ActivitiesStatisticsVO implements Serializable {

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

    @ApiModelProperty("参会人")
    private List<ActivitiesQuotaVO> participants;

    @ApiModelProperty("达标人")
    private List<ActivitiesQuotaVO> qualifiedPerson;

    @ApiModelProperty("付费金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty("已扣金额")
    private BigDecimal deductedAmount;

    @ApiModelProperty("抵用名额")
    private List<ActivitiesQuotaVO> deductedPerson;

    @ApiModelProperty("备注")
    private String remark;

}
