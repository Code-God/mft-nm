package com.meifute.external.pay.icbc.biz;

import com.meifute.external.pay.icbc.ICBCPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ICBCPayBizContentSignResponse
 * @Description
 * @Date 2020-07-31 16:00
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ICBCPayBizContentSignResponse extends ICBCPayResponse {

    private String appid; //appid字段，送回客户端
    private String msgid; //msgid字段，送回客户端
    private String format; //format字段，送回客户端
    private String charset; //charset字段，送回客户端
    private String signType; //signType字段，送回客户端
    private String timestampBack; //timestamp字段，送回客户端
    private String tranData; //交易报文数据，送回客户端
    private String merSignMsg; //签名字段，送回客户端
    private String wxAppId;
    private String attach;
}
