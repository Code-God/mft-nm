package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: OrderItemVO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 19:35
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVO implements Serializable {

    @ApiModelProperty("子订单号")
    private String subOrderCode;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("规格名称")
    private String skuName;

    @ApiModelProperty("商品商家编码")
    private String itemCode;

    @ApiModelProperty("商品数量")
    private Integer num;

    @ApiModelProperty("单价")
    private String price;

    @ApiModelProperty("优惠金额")
    private String discountAmt;

    @ApiModelProperty("支付金额")
    private String payAmt;

}