package com.meifute.external.jdexpress;

import com.jd.open.api.sdk.internal.JSON.JSON;
import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.meifute.external.jdexpress.queryorder.JdQueryOrderRequest;
import com.meifute.external.jdexpress.queryorder.JdQueryOrderResponse;
import com.meifute.external.jdexpress.serinalnumber.JdSerialNumberRequest;
import com.meifute.external.jdexpress.serinalnumber.JdSerialNumberResponse;
import com.meifute.external.jdexpress.stock.JdQueryStockRequest;
import com.meifute.external.jdexpress.stock.JdQueryStockResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @Classname MftDefaultJdClientTest
 * @Description
 * @Date 2020-07-23 17:43
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftDefaultJdClientTest {

    MftDefaultJdClient client;

    @BeforeEach
    void init() {
        client = new MftDefaultJdClient("https://api.jd.com/routerjson","0d5cc377-fbe1-497f-84c6-437c3a4cfe99","0C2AC54D67CD3E5ACD7EC00EFE395BAC","4cb1120cda6f47b2ad46527e226f62ed");
    }

    @Test
    public void queryExpress() {
        JdQueryOrderRequest request = JdQueryOrderRequest.newBuilder().eclpSoNo("ESL4419171982795").build();
        JdQueryOrderResponse response = client.execute(request);
        Assertions.assertNotNull(response);
    }

    @Test
    public void stock() {
        JdQueryStockRequest request = JdQueryStockRequest.newBuilder()
                .goodsNo("EMG4418057952708")
                .currentPage(0)
                .pageSize(20)
                .departmentNo("EBU4418046546421")
                .warehouseNo("110008555")
                .build();
        JdQueryStockResponse response = client.execute(request);
        Assertions.assertNotNull(response);
    }

    @Test
    public void getSeirnal() throws IOException {
        JdSerialNumberRequest request = JdSerialNumberRequest.newBuilder().eclpSoNo("ESL4419331443414").currentPage(1).pageSize(10).build();
        JdSerialNumberResponse execute = client.execute(request);
        log.info("---->:{}", JsonUtil.toJson(execute));
    }
}
