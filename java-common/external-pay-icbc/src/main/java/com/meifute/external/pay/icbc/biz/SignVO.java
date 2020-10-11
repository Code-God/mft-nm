package com.meifute.external.pay.icbc.biz;

import lombok.Data;

/**
 * @Classname SignVO
 * @Description TODO
 * @Date 2020-05-18 16:28
 * @Created by MR. Xb.Wu
 */
@Data
public class SignVO {

    private String privateKey;
    private String biz_content;
    private String clientType;
    private String app_id;
    private String msg_id;
    private String format;
    private String charset;
    private String encrypt_type;
    private String sign_type;
    private String timestamp;
    private String ca;
}
