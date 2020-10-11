package com.meifute.nm.othersserver.domain.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: wuxb
 * @Date: 2019-03-21 11:24
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@ApiModel(value = "移动支付入参")
public class MobileUnifiedOrderParam implements Serializable {

    @ApiModelProperty(value = "订单号", required = true)
    private String orderId;

    @ApiModelProperty(value = "支付类型，2微信，3支付宝",required = true)
    private String payType;

    @ApiModelProperty(value = "金额",required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "描叙",required = true)
    private String describe;

    @ApiModelProperty(value = "订单类型，0产品订单，1新品试用，2通用活动，3会务活动，4厦门活动", required = true)
    private Integer orderType;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("回调地址")
    private String notifyUrl;
}