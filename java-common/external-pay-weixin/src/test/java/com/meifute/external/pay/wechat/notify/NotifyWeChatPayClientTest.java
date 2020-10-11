package com.meifute.external.pay.wechat.notify;

import com.meifute.external.pay.wechat.MftWeChatPayClient;
import com.meifute.external.pay.wechat.MftWeChatPayDefaultClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Classname NotifyWeChatPayClientTest
 * @Description
 * @Date 2020-07-29 15:07
 * @Created by MR. Xb.Wu
 */
@Slf4j
class NotifyWeChatPayClientTest {

    @Test
    void notifyWeChatOrder() {
        NotifyWeChatOrderRequest request = NotifyWeChatOrderRequest.builder()
                .bodyXml("<xml><appid><![CDATA[wx11296849b9f015ec]]></appid>" +
                        "<bank_type><![CDATA[OTHERS]]></bank_type>" +
                        "<cash_fee><![CDATA[500]]></cash_fee>" +
                        "<fee_type><![CDATA[CNY]]></fee_type>" +
                        "<is_subscribe><![CDATA[N]]></is_subscribe>" +
                        "<mch_id><![CDATA[1546918881]]></mch_id>" +
                        "<nonce_str><![CDATA[7fbxJg8AiY9qhQo67byiBUwSBoc5BVEo]]></nonce_str>" +
                        "<openid><![CDATA[oJe6stx9fAM7ZxteXC-DiDiVdc2U]]></openid>" +
                        "<out_trade_no><![CDATA[1114346829442617344]]></out_trade_no>" +
                        "<result_code><![CDATA[SUCCESS]]></result_code>" +
                        "<return_code><![CDATA[SUCCESS]]></return_code>" +
                        "<sign><![CDATA[087D160EAE342445FC88B2CC6B8055EC]]></sign>" +
                        "<time_end><![CDATA[20200729155145]]></time_end>" +
                        "<total_fee>500</total_fee>" +
                        "<trade_type><![CDATA[APP]]></trade_type>" +
                        "<transaction_id><![CDATA[4200000712202007291374936653]]></transaction_id></xml>")
                .build();
        MftWeChatPayClient client = new MftWeChatPayDefaultClient("wx11296849b9f015ec", "1546918881", "9NOnZYq3KrdCyqBLOLiWi6O8lSzcZalQ");
        NotifyWeChatOrderResponse response = client.execute(request);
        log.info("" + response);
    }
}