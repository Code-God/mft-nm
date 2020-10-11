package com.meifute.external.jiguang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * @Classname MftJSmsClientTest
 * @Description
 * @Date 2020-07-22 13:13
 * @Created by MR. Xb.Wu
 */
class MftJSmsClientTest {

    private MftJSmsClient client;

    @BeforeEach
    void init() {
        client = new MftJSmsClient("93df5747e26f27ce8739eb8e", "76bcbbd1743e042f0fbf38f1");
    }

    @Test
    void sendSMSCode() {
        String code = client.sendSMSCode("17621938801", 176234);
        Assertions.assertNotNull(code);
    }

    @Test
    void sendTemplateSMS() {
        Map<String, String> map = new HashMap<>();
        map.put("admincode", "12");
        map.put("adminname", "美浮特小美");
        map.put("adminphone", "17621938802");
        boolean result = client.sendTemplateSMS("17621938801", map, 176225);
        Assertions.assertTrue(result);
    }
}