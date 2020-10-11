package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname PayResult
 * @Description
 * @Date 2020-08-20 12:56
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayResult implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("付费金额")
    private BigDecimal amount;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("交易类型，0支付，1退款")
    private Integer tradeType;

    @ApiModelProperty("支付类型，1余额, 2微信，3支付宝")
    private Integer payType;

    @ApiModelProperty("支付时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("外部订单号")
    private String outTradeNo;

    @ApiModelProperty("订单类型，0产品订单，1新品试用，2通用活动，3会务活动，4厦门活动")
    private Integer type;

    @ApiModelProperty("描叙")
    private String describe;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long version;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;

}
