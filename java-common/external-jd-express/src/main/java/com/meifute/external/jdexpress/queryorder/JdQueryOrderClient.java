package com.meifute.external.jdexpress.queryorder;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.ECLP.EclpOrderQueryOrderRequest;
import com.jd.open.api.sdk.response.ECLP.EclpOrderQueryOrderResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Classname JdQueryEayBillClient
 * @Description
 * @Date 2020-07-23 16:56
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class JdQueryOrderClient {

    private static final String SIGN_FOR_SUCCESS = "10034";
    private static final List<String> CAN_CANCEL_CODE = Arrays.asList("10009", "10010", "100130", "100131", "100132", "10014", "10015", "10016", "10028");


    private JdClient client;
    private JdQueryOrderRequest jdQueryWayBillRequest;

    public JdQueryOrderClient(JdClient client, JdQueryOrderRequest jdQueryWayBillRequest) {
        this.client = client;
        this.jdQueryWayBillRequest = jdQueryWayBillRequest;
    }


    /**
     * 查询订单信息
     * @return
     */
    public JdQueryOrderResponse execute() {
        EclpOrderQueryOrderRequest request = new EclpOrderQueryOrderRequest();
        request.setEclpSoNo(jdQueryWayBillRequest.getEclpSoNo());
        JdQueryOrderResponse jdQueryWayBillResponse = new JdQueryOrderResponse();
        try {
            EclpOrderQueryOrderResponse response = client.execute(request);
            if (Objects.isNull(response) || Objects.isNull(response.getQueryorderResult())) {
                return jdQueryWayBillResponse;
            }
            if (SIGN_FOR_SUCCESS.equals(response.getQueryorderResult().getCurrentStatus())) {
                jdQueryWayBillResponse.setSignFor(true);
            }
            if (CAN_CANCEL_CODE.contains(response.getQueryorderResult().getCurrentStatus())) {
                jdQueryWayBillResponse.setCanCancel(true);
            }
            jdQueryWayBillResponse.setWayBill(response.getQueryorderResult().getWayBill());
        } catch (Exception e) {
            log.error("queryOrderResult error:{0}", e);
        }
        return jdQueryWayBillResponse;
    }
}
