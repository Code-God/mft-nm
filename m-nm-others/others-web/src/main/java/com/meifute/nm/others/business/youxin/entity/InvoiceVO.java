package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: InvoiceVO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 19:33
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceVO implements Serializable {

    @ApiModelProperty("发票类型")
    private String invoiceType;

    @ApiModelProperty("发票抬头")
    private String invoiceHead;

    @ApiModelProperty("发票内容")
    private String invoiceCont;

}