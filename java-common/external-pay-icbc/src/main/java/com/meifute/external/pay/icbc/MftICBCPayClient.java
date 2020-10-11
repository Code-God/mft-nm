package com.meifute.external.pay.icbc;

/**
 * @Classname ICBCPayClient
 * @Description
 * @Date 2020-07-31 16:01
 * @Created by MR. Xb.Wu
 */
public interface MftICBCPayClient {

    <T extends ICBCPayResponse> T execute(ICBCPayRequest<T> request);
}
