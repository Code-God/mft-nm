package com.meifute.external.pay.wechat.notify;

import com.meifute.external.pay.wechat.WeChatPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Classname NotifyWeChatOrderResponse
 * @Description
 * @Date 2020-07-29 14:39
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyWeChatOrderResponse extends WeChatPayResponse {

    private int totalFee;

    private String transactionId;

    private String returnMsg;

    private String outTradeNo;

    private boolean result;

    /**
     * 附加字段
     */
    private String attach;
}
