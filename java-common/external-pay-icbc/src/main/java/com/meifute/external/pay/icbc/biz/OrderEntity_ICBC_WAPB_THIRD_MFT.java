package com.meifute.external.pay.icbc.biz;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname OrderEntity_ICBC_WAPB_THIRD
 * @Description TODO
 * @Date 2020-05-09 17:23
 * @Created by MR. Xb.Wu
 */
@Data
public class OrderEntity_ICBC_WAPB_THIRD_MFT {

    private String userCrt;
    private String userKey;
    private String appId;
    private String msgId;
    private String format;
    private String charset;
    private String encryptType;
    private String signType;
    private String timestamp;
    private String ca;
    private Map<String, String> bizContent = new HashMap<>();
}
