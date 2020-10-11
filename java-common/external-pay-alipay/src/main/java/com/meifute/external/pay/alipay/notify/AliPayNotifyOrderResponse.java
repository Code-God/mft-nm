package com.meifute.external.pay.alipay.notify;

import com.meifute.external.pay.alipay.AliPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname AliPayNotifyOrderResponse
 * @Description
 * @Date 2020-07-30 18:12
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayNotifyOrderResponse extends AliPayResponse {

    private String tradeNo;

    private BigDecimal totalAmount;

    private String outTradeNo;

    private boolean result;

    private String returnMsg;

    private String passbackParams;
}
