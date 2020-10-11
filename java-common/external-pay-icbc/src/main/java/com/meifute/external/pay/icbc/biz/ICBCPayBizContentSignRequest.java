package com.meifute.external.pay.icbc.biz;

import com.meifute.external.pay.icbc.ICBCPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname ICBCPayBizContentSignRequest
 * @Description
 * @Date 2020-07-31 15:38
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ICBCPayBizContentSignRequest extends ICBCPayRequest<ICBCPayBizContentSignResponse> {

    //回调地址
    private String notifyUrl;
    //微信需要的
    private String shopAppId;
    //私钥
    private String certPrivateKey;
    //订单号
    private String orderId;
    //1-工行iPhone客户端版; 2-工行Android客户端版; 23-微信APP; 24-支付宝APP
    private String clientType;
    private String returnUrl;
    //金额
    private BigDecimal amount;

    private String appName;
}
