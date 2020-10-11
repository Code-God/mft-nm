package com.meifute.external.pay.wechat.query;

import com.meifute.external.pay.wechat.MftWeChatPayClient;
import com.meifute.external.pay.wechat.MftWeChatPayDefaultClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Classname QueryWeChatOrderClientTest
 * @Description
 * @Date 2020-07-29 16:24
 * @Created by MR. Xb.Wu
 */
@Slf4j
class QueryWeChatOrderClientTest {

    @Test
    void queryWeChatOrder() {
        QueryWeChatOrderRequest request = QueryWeChatOrderRequest.builder()
                .outTradeNo("1114346829442617344")
                .build();
        MftWeChatPayClient client = new MftWeChatPayDefaultClient("wx11296849b9f015ec", "1546918881", "9NOnZYq3KrdCyqBLOLiWi6O8lSzcZalQ");
        QueryWeChatOrderResponse response = client.execute(request);
        log.info("" + response);
    }
}