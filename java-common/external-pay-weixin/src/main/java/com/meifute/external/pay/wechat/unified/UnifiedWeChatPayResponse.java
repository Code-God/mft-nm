package com.meifute.external.pay.wechat.unified;

import com.meifute.external.pay.wechat.WeChatPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UnifiedWeChatResponse
 * @Description
 * @Date 2020-07-29 10:33
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedWeChatPayResponse extends WeChatPayResponse {

    private String appId;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timestamp;
    private String sign;
    private String packAge;
    private boolean result;
    private String returnMsg;
}
