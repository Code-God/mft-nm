package com.meifute.external.aliyun.express.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Classname AliExpressClientTest
 * @Description
 * @Date 2020-07-28 15:14
 * @Created by MR. Xb.Wu
 */
class AliExpressClientTest {

    @Test
    void getAliExpress() {
        AliExpressResult express = AliExpressClient.getAliExpress("06020ea3219e40d4945834809ea20591", "JDVB04831161549");
        Assertions.assertNotNull(express);
    }
}