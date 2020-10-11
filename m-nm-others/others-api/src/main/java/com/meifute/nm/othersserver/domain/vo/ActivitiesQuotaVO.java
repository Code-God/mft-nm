package com.meifute.nm.othersserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname activitiesQuotaVO
 * @Description
 * @Date 2020-08-06 14:15
 * @Created by MR. Xb.Wu
 */
@Data
public class ActivitiesQuotaVO implements Serializable {

    private String id;

    private String agentPhone;

    private String agentName;

    @ApiModelProperty("类型0参会人，1达标人，2达标退代，3其他活动抵扣名额")
    private Integer type;

    @ApiModelProperty("关联时间，type=2时为退代时间，type=3时为升级总代时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime associationTime;
}
