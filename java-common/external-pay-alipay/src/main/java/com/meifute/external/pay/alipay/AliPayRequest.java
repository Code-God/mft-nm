package com.meifute.external.pay.alipay;

/**
 * @Classname AlipayRequest
 * @Description
 * @Date 2020-07-30 16:39
 * @Created by MR. Xb.Wu
 */
public interface AliPayRequest<T extends AliPayResponse> {

    Class<T> getResponseClass();
}
