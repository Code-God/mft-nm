package com.meifute.nm.othersserver.domain.vo.amoy;

import com.meifute.nm.othersserver.domain.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname QueryEnrollVO
 * @Description
 * @Date 2020-08-20 16:33
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryAgent extends BaseParam implements Serializable {

    @ApiModelProperty("代理姓名")
    private String name;

    @ApiModelProperty("代理手机号")
    private String phone;

    @ApiModelProperty("enrollId")
    private String enrollId;
}
