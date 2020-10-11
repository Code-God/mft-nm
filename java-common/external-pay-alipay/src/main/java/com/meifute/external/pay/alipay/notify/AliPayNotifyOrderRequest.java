package com.meifute.external.pay.alipay.notify;

import com.meifute.external.pay.alipay.AliPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Classname AliPayNotifyOrderRequest
 * @Description
 * @Date 2020-07-30 18:11
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayNotifyOrderRequest implements AliPayRequest<AliPayNotifyOrderResponse> {

    Map<String, String> params;


    @Override
    public Class<AliPayNotifyOrderResponse> getResponseClass() {
        return AliPayNotifyOrderResponse.class;
    }
}
