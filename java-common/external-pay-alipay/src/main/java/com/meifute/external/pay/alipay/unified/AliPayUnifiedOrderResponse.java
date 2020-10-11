package com.meifute.external.pay.alipay.unified;

import com.meifute.external.pay.alipay.AliPayResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname AliUnifiedOrderResponse
 * @Description
 * @Date 2020-07-30 11:53
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayUnifiedOrderResponse extends AliPayResponse implements Serializable {

    private String body;

    private boolean result;

    private String returnMsg;
}
