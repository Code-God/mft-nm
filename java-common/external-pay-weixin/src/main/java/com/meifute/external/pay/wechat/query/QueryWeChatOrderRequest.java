package com.meifute.external.pay.wechat.query;

import com.meifute.external.pay.wechat.WeChatPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Classname QueryWechatOrderRequest
 * @Description
 * @Date 2020-07-29 11:44
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryWeChatOrderRequest extends WeChatPayRequest<QueryWeChatOrderResponse> {


    /**
     * 订单号
     */
    private String outTradeNo;
}
