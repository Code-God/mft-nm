package com.meifute.nm.othersserver.domain.vo.moments;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname TodaySourceNum
 * @Description
 * @Date 2020-08-13 12:17
 * @Created by MR. Xb.Wu
 */
@Data
public class TodaySourceNum {

    @ApiModelProperty("当天已发布的素材数量")
    private Integer todayReleasedSourceNum;

    @ApiModelProperty("素材开关")
    private Integer OnOff;
}
