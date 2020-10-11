package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: BaseResponse
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 10:57
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable {

    @ApiModelProperty("响应码，0=成功")
    private String code;

    @ApiModelProperty("响应消息")
    private String message;

}