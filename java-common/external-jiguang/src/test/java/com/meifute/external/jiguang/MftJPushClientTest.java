package com.meifute.external.jiguang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Classname MftJPushClientTest
 * @Description
 * @Date 2020-07-22 13:13
 * @Created by MR. Xb.Wu
 */
class MftJPushClientTest {

    private MftJPushClient client;

    @BeforeEach
    void init() {
        client = new MftJPushClient("4f8295029c0db46dbbc2d37b", "426c91c16d807a02ed54ff42");
    }

    @Test
    void sendPush() {
        List<String> tagsList = new ArrayList<>();
        tagsList.add("367544397757153281");
        Map<String, String> extras = new HashMap<>();
        extras.put("username", "扭送");
        boolean b = client.sendPush("美浮特祝您生活愉快哦哦哦哦!", tagsList, extras, false);
        Assertions.assertTrue(b);
    }

    @Test
    void sendPushAllAudience() {
        boolean b = client.sendPushAllAudience("美浮特祝您生活愉快!", null, false);
        Assertions.assertTrue(b);
    }
}