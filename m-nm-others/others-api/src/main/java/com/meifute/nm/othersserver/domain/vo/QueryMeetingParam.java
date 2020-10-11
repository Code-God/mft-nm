package com.meifute.nm.othersserver.domain.vo;

import com.meifute.nm.othersserver.domain.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname QueryMeetingParam
 * @Description TODO
 * @Date 2020-07-06 17:44
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryMeetingParam extends BaseParam {

    @ApiModelProperty("会务名称")
    private String meetingName;

    @ApiModelProperty("会务开始时间")
    private LocalDateTime meetingStartTime;

    @ApiModelProperty("会务结束时间")
    private LocalDateTime meetingEndTime;

    @ApiModelProperty("主负责人")
    private String meetingMasterCharge;

    @ApiModelProperty("报名截止起始时间")
    private LocalDateTime endEnrollStartTime;

    @ApiModelProperty("报名截止结束时间")
    private LocalDateTime endEnrollEndTime;

    @ApiModelProperty("状态，0待发布，1已发布，2已结束，3已取消")
    private String status;
}
