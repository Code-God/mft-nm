package com.meifute.external.pay.icbc.notify;

import com.meifute.external.pay.icbc.ICBCPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ICBCPayNotifyResponse
 * @Description
 * @Date 2020-07-31 16:29
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ICBCPayNotifyResponse extends ICBCPayResponse {

    private boolean result;

    private String returnMsg;

    private String msgId;

    private String outTradeNo;

    private String tradeNo;

    private int amount;
}
