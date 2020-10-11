package com.meifute.nm.othersserver.domain.vo.amoy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname AmoyActivitySomeoneVO
 * @Description
 * @Date 2020-08-19 18:06
 * @Created by MR. Xb.Wu
 */
@Data
public class AmoyActivitySomeoneVO implements Serializable {

    @ApiModelProperty("同行人姓名")
    private String name;

    @ApiModelProperty("同行人手机号")
    private String phone;

    @ApiModelProperty("同行人身份证号")
    private String idCard;
}
