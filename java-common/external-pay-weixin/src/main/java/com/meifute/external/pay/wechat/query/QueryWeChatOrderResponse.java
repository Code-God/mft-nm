package com.meifute.external.pay.wechat.query;

import com.meifute.external.pay.wechat.WeChatPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Classname QueryWeChatOrderResponse
 * @Description
 * @Date 2020-07-29 11:45
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryWeChatOrderResponse extends WeChatPayResponse {

    private int totalFee;

    private String transactionId;

    private String returnMsg;

    private boolean result;
}
