package com.meifute.external.jdexpress.push;

import com.alibaba.fastjson.JSON;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.StringUtil;
import com.jd.open.api.sdk.request.ECLP.EclpOrderAddOrderRequest;
import com.jd.open.api.sdk.response.ECLP.EclpOrderAddOrderResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname JDExpressPushClient
 * @Description
 * @Date 2020-07-23 16:42
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class JdExpressPushClient {

    private JdExpressPushRequest jdExpressPushRequest;
    private JdClient client;

    public JdExpressPushClient(JdClient client, JdExpressPushRequest jdExpressPushRequest) {
        this.jdExpressPushRequest = jdExpressPushRequest;
        this.client = client;
    }

    private void checkConfig(String appKey, String appSecret, String accessToken, String serverUrl) {
        if (StringUtil.isEmpty(appKey) || StringUtil.isEmpty(appSecret) || StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(serverUrl)) {
            throw new RuntimeException("物流初始配置信息不能为空");
        }
    }


    /**
     * 京东推单
     *
     * @return
     */
    public JdExpressPushResponse execute() {
        log.info("进入京东推单 orderId: {},transportGoodsNo: {},goodsAmount: {}",
                jdExpressPushRequest.getOrderId(), jdExpressPushRequest.getTransportGoodsNo(), jdExpressPushRequest.getGoodsAmount());
        EclpOrderAddOrderRequest request = new EclpOrderAddOrderRequest();
        request.setIsvUUID(jdExpressPushRequest.getOrderId());
        request.setOrderMark(jdExpressPushRequest.getOrderMark());
        request.setSalePlatformSource(jdExpressPushRequest.getSalePlatformSource());
        request.setIsvSource(jdExpressPushRequest.getIsvSource());
        request.setShopNo(jdExpressPushRequest.getShopNo());
        request.setDepartmentNo(jdExpressPushRequest.getDepartmentNo());
        request.setConsigneeName(jdExpressPushRequest.getRecipientName());
        request.setConsigneeMobile(jdExpressPushRequest.getRecipientPhone());
        request.setConsigneeAddress(jdExpressPushRequest.getRecipientAddress());
        request.setQuantity(jdExpressPushRequest.getGoodsAmount());
        request.setGoodsNo(jdExpressPushRequest.getTransportGoodsNo());
        request.setWarehouseNo(jdExpressPushRequest.getWarehouseNo());
        EclpOrderAddOrderResponse response;
        JdExpressPushResponse jdExpressPushResponse = new JdExpressPushResponse();
        try {
            response = client.execute(request);
            if (response != null) {
                jdExpressPushResponse.setEclpSoNo(response.getEclpSoNo());
                jdExpressPushResponse.setMsg(response.getZhDesc());
            }
            log.info("jdExpressPush Response: {}", JSON.toJSONString(response));
        } catch (Exception e) {
            log.error("jdExpressPush error:{}", e.getMessage());
            return null;
        }
        return jdExpressPushResponse;
    }
}
