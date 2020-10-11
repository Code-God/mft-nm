package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: wuxb
 * @Date: 2019-03-21 11:24
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@ApiModel(value = "移动支付入参")
public class PayParam implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "类型，0支付，1充值")
    private String tradeType;

    @ApiModelProperty(value = "支付类型，1余额，2微信，3支付宝")
    private String payType;

    @ApiModelProperty(value = "订单类型，0产品订单，1新品试用，2通用活动，3会务活动，4厦门活动")
    private Integer orderType;
}
