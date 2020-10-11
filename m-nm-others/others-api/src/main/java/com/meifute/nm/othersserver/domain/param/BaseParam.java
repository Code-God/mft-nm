package com.meifute.nm.othersserver.domain.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseParam implements Serializable {

    @ApiModelProperty(value = "分页当前页数（默认从1开始）",example = "1")
    protected Integer pageCurrent;

    @ApiModelProperty(value = "分页每页个数（默认20条）",example = "20")
    protected Integer pageSize;

    @ApiModelProperty("时间范围查询参数--开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime beginTime;

    @ApiModelProperty("时间范围查询参数--结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime endTime;

    @ApiModelProperty("排序字段")
    protected String orderField;

    @ApiModelProperty("排序規則")
    protected String orderRule;


}
