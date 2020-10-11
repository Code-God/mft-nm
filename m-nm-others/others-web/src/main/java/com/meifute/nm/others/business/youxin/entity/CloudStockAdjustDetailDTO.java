package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: CloudStockDTO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 16:53
 * @Version: 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudStockAdjustDetailDTO implements Serializable {

    @ApiModelProperty("sku code")
    private String skuCode;

    @ApiModelProperty("子订单号")
    private String subOrderCode;

    @ApiModelProperty("数量")
    private Integer quantity;

}