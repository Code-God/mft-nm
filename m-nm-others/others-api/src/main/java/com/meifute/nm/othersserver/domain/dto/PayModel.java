package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname PayModel
 * @Description
 * @Date 2020-08-20 13:27
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayModel implements Serializable {

    @ApiModelProperty(value = "金额",required = true)
    private BigDecimal amount;
    @ApiModelProperty(value = "订单号",required = true)
    private String orderId;
    @ApiModelProperty(value = "订单类型，0产品订单，1新品试用，2通用活动，3会务活动，4厦门活动",required = true)
    private Integer type;
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;
    @ApiModelProperty(value = "支付类型，1余额, 2微信，3支付宝",required = true)
    private Integer payType;
    @ApiModelProperty(value = "描叙，例1：厦门活动订单支付, 例2：厦门活动订单退款",required = true)
    private String describe;
    @ApiModelProperty(value = "外部订单号")
    private String outTradeNo;
    @ApiModelProperty(value = "交易类型，0支付，1退款",required = true)
    private Integer tradeType;

}
