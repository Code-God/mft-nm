package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: OrderCreateVO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 19:23
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateVO implements Serializable {

    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("请求唯一标识")
    private String seqid;
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("仓库编码")
    private String wareCode;
    @ApiModelProperty("订单状态，1 代表已支付")
    private Integer orderState;
    @ApiModelProperty("支付方式，1 代表在线支付，2 代表货 到付款")
    private Integer payType;
    @ApiModelProperty("下单时间")
    private String orderTime;
    @ApiModelProperty("付款时间")
    private String payTime;
    @ApiModelProperty("订单总价")
    private String totalAmt;
    @ApiModelProperty("运费")
    private String forwareAmt;
    @ApiModelProperty("优惠金额")
    private String discountAmt;
    @ApiModelProperty("实付金额")
    private String payAmt;
    @ApiModelProperty("买家备注")
    private String buyermemo;
    @ApiModelProperty("卖家信息")
    private SellerInfoVO sellInfo;
    @ApiModelProperty("买家信息")
    private BuyerInfoVO buyerInfo;
    @ApiModelProperty("收货信息")
    private AddressItemVO addressItem;
    @ApiModelProperty("发票信息")
    private InvoiceVO invoice;
    @ApiModelProperty("商品详情")
    private List<OrderItemVO> orderItem;


}