package com.meifute.nm.othersserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meifute.nm.othersserver.domain.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname QueryActivitiesStatisticsParam
 * @Description
 * @Date 2020-08-06 11:47
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryActivitiesStatisticsParam extends BaseParam {

    private String activityName;

    @ApiModelProperty("是否付费参加, 0否，1是")
    private Integer isPaymentAmount;

    private String agentPhone;

    private String agentName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;
}
