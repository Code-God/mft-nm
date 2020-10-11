package com.meifute.external.pay.alipay.unified;

import com.meifute.external.pay.alipay.AliPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname AliUnifiedOrderRequest
 * @Description
 * @Date 2020-07-30 11:44
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayUnifiedOrderRequest implements AliPayRequest<AliPayUnifiedOrderResponse> {

    /**
     * 标题
     */
    private String subject;

    /**
     * 单号
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 附加数据
     */
    private String passbackParams;

    @Override
    public Class<AliPayUnifiedOrderResponse> getResponseClass() {
        return AliPayUnifiedOrderResponse.class;
    }
}
