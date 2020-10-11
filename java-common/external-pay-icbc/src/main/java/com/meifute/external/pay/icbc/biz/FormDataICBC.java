package com.meifute.external.pay.icbc.biz;


import lombok.Data;

/**
 * @Classname FormData
 * @Description TODO
 * @Date 2020-05-09 17:26
 * @Created by MR. Xb.Wu
 */
@Data
public class FormDataICBC {

    private String tranData;
    private String merSignMsg;
    private String merSingData;
    private String appId;
    private String msgId;
    private String format;
    private String charset;
    private String encryptType;
    private String signType;
    private String timestamp;
    private String ca;
    private OrderEntity_ICBC_WAPB_THIRD_MFT orderEntity;
}
