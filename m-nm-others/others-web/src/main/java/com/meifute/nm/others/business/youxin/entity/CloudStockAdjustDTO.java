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
public class CloudStockAdjustDTO implements Serializable {

    @ApiModelProperty("userId")
    private String userId;

    @ApiModelProperty("商城订单id")
    private String orderId;

    @ApiModelProperty("0:增加，1:减少")
    private String type;

    @ApiModelProperty("代理库存信息")
    private List<CloudStockAdjustDetailDTO> detailDTOS;
}