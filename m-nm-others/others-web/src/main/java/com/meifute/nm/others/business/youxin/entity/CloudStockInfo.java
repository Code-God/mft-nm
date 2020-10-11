package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: CloudStockInfo
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 16:54
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CloudStockInfo implements Serializable {

    @ApiModelProperty("商品编码")
    private String itemCode;

    @ApiModelProperty("代理身份标识")
    private String childIdentify;

    @ApiModelProperty("仓库编码")
    private String wareCode;

    @ApiModelProperty("可用库存数量")
    private String availableCnt;

    @ApiModelProperty("总库存数量")
    private String totalCnt;

}