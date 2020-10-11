package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 云库存详情 商品库存信息
 * @Author ChenXiang
 * @Date 2019-04-15 20:25:04
 * @ModifyBy
 * @ModifyDate
 **/
@Data
@ApiModel(value = "用户云库存商品详细信息")
public class MallCloudStockDTO implements Serializable {

    @ApiModelProperty(value = "云库存ID")
    private String id;
    @ApiModelProperty(value = "用户ID")
    private String mallUserId;
    @ApiModelProperty(value = "商品ID")
    private String itemId;
    @ApiModelProperty(value = "商品sku码")
    private String skuCode;
    @ApiModelProperty(value = "库存数量")
    private BigDecimal stock;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品编号")
    private String transportGoodsNo;

}
