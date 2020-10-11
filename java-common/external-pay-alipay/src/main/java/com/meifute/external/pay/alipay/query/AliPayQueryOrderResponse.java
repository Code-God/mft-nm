package com.meifute.external.pay.alipay.query;

import com.meifute.external.pay.alipay.AliPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname AliPayQueryOrderResponse
 * @Description
 * @Date 2020-07-30 17:39
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayQueryOrderResponse extends AliPayResponse {

    private String tradeNo;

    private BigDecimal totalAmount;

    private boolean result;

    private String returnMsg;
}
