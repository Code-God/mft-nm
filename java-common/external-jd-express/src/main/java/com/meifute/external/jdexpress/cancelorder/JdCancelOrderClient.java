package com.meifute.external.jdexpress.cancelorder;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.response.cancelOrder.CancelResult;
import com.jd.open.api.sdk.request.ECLP.EclpOrderCancelOrderRequest;
import com.jd.open.api.sdk.response.ECLP.EclpOrderCancelOrderResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Classname JdCancelOrderClient
 * @Description
 * @Date 2020-07-23 18:01
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class JdCancelOrderClient {

    private static final int CAN_CANCEL_STATUS = 1;

    private JdClient client;
    private JdCancelOrderRequest jdCancelOrderRequest;


    public JdCancelOrderClient(JdClient client, JdCancelOrderRequest jdCancelOrderRequest) {
        this.client = client;
        this.jdCancelOrderRequest = jdCancelOrderRequest;
    }

    /**
     * 关闭出库单
     *
     * @return
     */
    public JdCancelOrderResponse execute() {
        EclpOrderCancelOrderRequest request = new EclpOrderCancelOrderRequest();
        request.setEclpSoNo(jdCancelOrderRequest.getEclpSoNo());
        JdCancelOrderResponse jdCancelOrderResponse = new JdCancelOrderResponse();
        try {
            EclpOrderCancelOrderResponse response = client.execute(request);
            if (Objects.isNull(response) || Objects.isNull(response.getCancelorderResult())) {
                return jdCancelOrderResponse;
            }
            CancelResult cancelResult = response.getCancelorderResult();
            jdCancelOrderResponse.setResult(CAN_CANCEL_STATUS == cancelResult.getCode());
        } catch (Exception e) {
            log.error("cancelOrder error:{}", e.getMessage());
        }
        return jdCancelOrderResponse;
    }
}
