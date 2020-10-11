package com.meifute.external.pay.wechat.notify;

import com.meifute.external.pay.wechat.WeChatPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Classname NotifyWeChatOrderRequest
 * @Description
 * @Date 2020-07-29 14:38
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyWeChatOrderRequest extends WeChatPayRequest<NotifyWeChatOrderResponse> {

    /**
     * xml字符串
     */
    private String bodyXml;
}
