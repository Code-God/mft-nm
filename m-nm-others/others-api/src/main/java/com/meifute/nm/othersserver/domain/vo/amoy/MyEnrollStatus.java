package com.meifute.nm.othersserver.domain.vo.amoy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MyEnrollStatus
 * @Description
 * @Date 2020-08-20 11:25
 * @Created by MR. Xb.Wu
 */
@Data
public class MyEnrollStatus implements Serializable {

    @ApiModelProperty("0未报名，1已报名，2待付款")
    private Integer status;

    @ApiModelProperty("报名id")
    private String enrollId;
}
