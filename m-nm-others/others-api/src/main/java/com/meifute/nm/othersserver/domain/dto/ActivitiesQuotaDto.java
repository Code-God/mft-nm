package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname ActivitiesQuota
 * @Description
 * @Date 2020-08-06 11:18
 * @Created by MR. Xb.Wu
 */
@Data
public class ActivitiesQuotaDto implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("活动主表id")
    private String statisticsId;

    @ApiModelProperty("类型 0参会人，1达标人，2达标退代，3其他活动抵扣名额")
    private Integer type;

    @ApiModelProperty("代理姓名")
    private String agentName;

    @ApiModelProperty("代理手机号")
    private String agentPhone;

    @ApiModelProperty("关联时间，type=2为退代时间，type=3为升级总代时间")
    private LocalDateTime associationTime;

    @ApiModelProperty("抵用活动名称")
    private String deductedPersonAcName;

}
