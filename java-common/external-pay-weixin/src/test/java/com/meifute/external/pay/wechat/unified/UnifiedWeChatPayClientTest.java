package com.meifute.external.pay.wechat.unified;

import com.meifute.external.pay.wechat.MftWeChatPayClient;
import com.meifute.external.pay.wechat.MftWeChatPayDefaultClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Classname UnifiedWeChatPayClientTest
 * @Description
 * @Date 2020-07-29 16:41
 * @Created by MR. Xb.Wu
 */
@Slf4j
class UnifiedWeChatPayClientTest {

    @Test
    void weChatUnifiedOrder() {
        UnifiedWeChatPayRequest request = UnifiedWeChatPayRequest.builder()
                .body("订单")
                .notifyUrl("aaaa")
                .totalFee(BigDecimal.ONE)
                .outTradeNo("123455")
                .build();
        MftWeChatPayClient client = new MftWeChatPayDefaultClient("wx11296849b9f015ec", "1546918881", "9NOnZYq3KrdCyqBLOLiWi6O8lSzcZalQ");
        UnifiedWeChatPayResponse response = client.execute(request);
        log.info("" + response);
    }
}