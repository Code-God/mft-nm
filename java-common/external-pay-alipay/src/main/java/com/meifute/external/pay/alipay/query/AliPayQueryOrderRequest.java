package com.meifute.external.pay.alipay.query;

import com.meifute.external.pay.alipay.AliPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname AliPayQueryOrderRequest
 * @Description
 * @Date 2020-07-30 17:39
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayQueryOrderRequest implements AliPayRequest<AliPayQueryOrderResponse> {

    private String outTradeNo;

    @Override
    public Class<AliPayQueryOrderResponse> getResponseClass() {
        return AliPayQueryOrderResponse.class;
    }
}
