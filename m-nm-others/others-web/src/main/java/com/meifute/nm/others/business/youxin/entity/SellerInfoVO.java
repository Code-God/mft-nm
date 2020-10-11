package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: SellInfoVO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 19:30
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfoVO implements Serializable {

    @ApiModelProperty("买家昵称")
    private String buyer;

    @ApiModelProperty("下单手机")
    private String buyerPhone;

}