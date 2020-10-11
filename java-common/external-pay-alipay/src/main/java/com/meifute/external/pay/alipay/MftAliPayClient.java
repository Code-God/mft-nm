package com.meifute.external.pay.alipay;

/**
 * @Classname MftAliPayClient
 * @Description
 * @Date 2020-07-30 16:40
 * @Created by MR. Xb.Wu
 */
public interface MftAliPayClient {

    <T extends AliPayResponse> T execute(AliPayRequest<T> request);

}
