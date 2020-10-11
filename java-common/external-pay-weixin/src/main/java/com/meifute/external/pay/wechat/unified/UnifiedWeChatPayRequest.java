package com.meifute.external.pay.wechat.unified;

import com.meifute.external.pay.wechat.WeChatPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname WeChatPayRequest
 * @Description
 * @Date 2020-07-29 10:21
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedWeChatPayRequest extends WeChatPayRequest<UnifiedWeChatPayResponse> {


    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private BigDecimal totalFee;

    /**
     * 描述
     */
    private String body;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 附加字段
     */
    private String attach;

}
