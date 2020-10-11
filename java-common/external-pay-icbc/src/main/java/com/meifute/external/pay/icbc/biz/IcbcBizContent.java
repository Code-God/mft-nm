package com.meifute.external.pay.icbc.biz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname IcbcBizContent
 * @Description TODO
 * @Date 2020-05-09 15:58
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IcbcBizContent {

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
