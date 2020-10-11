package com.meifute.nm.othersserver.domain.vo.amoy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname EnrollVo
 * @Description
 * @Date 2020-08-19 18:21
 * @Created by MR. Xb.Wu
 */
@Data
public class EnrollVo implements Serializable {

    @ApiModelProperty("支付方式")
    private List<Integer> payTypes;

    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    @ApiModelProperty("订单id")
    private String orderId;

    private BigDecimal balance;

    private Boolean enrollResult;
}
