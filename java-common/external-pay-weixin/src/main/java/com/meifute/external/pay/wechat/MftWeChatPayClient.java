package com.meifute.external.pay.wechat;

/**
 * @Classname MftWeChatPayClient
 * @Description
 * @Date 2020-07-31 17:07
 * @Created by MR. Xb.Wu
 */
public interface MftWeChatPayClient {

    <T extends WeChatPayResponse> T execute(WeChatPayRequest<T> request);
}
