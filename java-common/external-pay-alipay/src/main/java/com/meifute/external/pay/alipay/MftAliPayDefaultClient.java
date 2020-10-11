package com.meifute.external.pay.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.meifute.external.pay.alipay.notify.AliPayNotifyOrderRequest;
import com.meifute.external.pay.alipay.notify.AliPayNotifyOrderResponse;
import com.meifute.external.pay.alipay.query.AliPayQueryOrderRequest;
import com.meifute.external.pay.alipay.query.AliPayQueryOrderResponse;
import com.meifute.external.pay.alipay.unified.AliPayUnifiedOrderRequest;
import com.meifute.external.pay.alipay.unified.AliPayUnifiedOrderResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Classname MftAliPayDefualtClient
 * @Description
 * @Date 2020-07-30 16:49
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftAliPayDefaultClient implements MftAliPayClient {

    private AlipayClient client;

    private String aliPayPublicKey;

    private static final String gatewayUrl = "https://openapi.alipay.com/gateway.do";


    public MftAliPayDefaultClient(String appId, String privateKey, String aliPayPublicKey) {
        this.aliPayPublicKey = aliPayPublicKey;
        this.client = new DefaultAlipayClient(gatewayUrl, appId, privateKey, AlipayConstants.FORMAT_JSON,
                AlipayConstants.CHARSET_UTF8, aliPayPublicKey, AlipayConstants.SIGN_TYPE_RSA2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AliPayResponse> T execute(AliPayRequest<T> request) {
        if (request instanceof AliPayUnifiedOrderRequest) {
            return (T) unifiedAliPayOrder((AliPayUnifiedOrderRequest) request);
        }
        if (request instanceof AliPayQueryOrderRequest) {
            return (T) queryAliPayOrder((AliPayQueryOrderRequest) request);
        }
        if (request instanceof AliPayNotifyOrderRequest) {
            return (T) notifyAliPayOrder((AliPayNotifyOrderRequest) request);
        }
        return null;
    }

    /**
     * 创建支付宝支付预付单
     *
     * @param request
     * @return
     */
    private AliPayUnifiedOrderResponse unifiedAliPayOrder(AliPayUnifiedOrderRequest request) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(request.getSubject());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(request.getAmt()));
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setEnablePayChannels("balance,moneyFund,bankPay,debitCardExpress");
        model.setPassbackParams(URLEncoder.encode(request.getPassbackParams(), StandardCharsets.UTF_8)); //附加数据

        AlipayTradeAppPayRequest aliPayRequest = new AlipayTradeAppPayRequest();
        aliPayRequest.setNotifyUrl(request.getNotifyUrl());
        aliPayRequest.setBizModel(model);
        try {
            AlipayTradeAppPayResponse response = client.sdkExecute(aliPayRequest);

            return AliPayUnifiedOrderResponse.builder().body(response.getBody()).result(true).returnMsg("调用成功").build();
        } catch (Exception e) {
            log.info("支付宝统一下单接口服务端异常,异常信息---", e);
        }
        return AliPayUnifiedOrderResponse.builder().result(false).returnMsg("创建支付宝支付预付单失败").build();
    }

    /**
     * 查询支付宝支付结果
     *
     * @param request
     * @return
     */
    public AliPayQueryOrderResponse queryAliPayOrder(AliPayQueryOrderRequest request) {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(request.getOutTradeNo());
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        queryRequest.setBizModel(model);

        try {
            AlipayTradeQueryResponse response = client.execute(queryRequest);

            if (response.isSuccess() && ("TRADE_SUCCESS".equals(response.getTradeStatus()) || "TRADE_FINISHED".equals(response.getTradeStatus()))) {
                return AliPayQueryOrderResponse.builder()
                        .result(true)
                        .totalAmount(new BigDecimal(response.getTotalAmount()))
                        .tradeNo(response.getTradeNo())
                        .returnMsg("支付成功")
                        .build();
            }
            return AliPayQueryOrderResponse.builder()
                    .result(false)
                    .returnMsg(response.getSubMsg())
                    .build();
        } catch (Exception e) {
            log.error("支付宝支付查询支付结果异常 queryAliPayOrder :{}", e.getMessage());
        }
        return AliPayQueryOrderResponse.builder()
                .result(false)
                .returnMsg("支付失败")
                .build();
    }

    /**
     * 支付宝支付完成后通知回调
     *
     * @param request
     * @return
     */
    public AliPayNotifyOrderResponse notifyAliPayOrder(AliPayNotifyOrderRequest request) {
        Map<String, String> params = request.getParams();
        String tradeStatus = params.get("trade_status");
        String outTradeNo = params.get("out_trade_no");
        String tradeNo = params.get("trade_no");
        String totalAmount = params.get("total_amount");
        String subMsg = params.get("sub_msg");
        String passbackParams = params.get("passback_params");

        if (!"TRADE_SUCCESS".equals(tradeStatus) && !"TRADE_FINISHED".equals(tradeStatus)) {
            return AliPayNotifyOrderResponse.builder()
                    .result(false)
                    .returnMsg(subMsg)
                    .build();
        }
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
            if (flag) {
                return AliPayNotifyOrderResponse.builder()
                        .result(true)
                        .totalAmount(new BigDecimal(totalAmount))
                        .returnMsg("支付成功")
                        .passbackParams(passbackParams)
                        .outTradeNo(outTradeNo)
                        .tradeNo(tradeNo)
                        .build();
            }
        } catch (Exception e) {
            log.error("支付宝支付回调异常 notifyAliPayOrder :{}", e.getMessage());
        }
        return AliPayNotifyOrderResponse.builder()
                .result(false)
                .returnMsg("验签失败")
                .build();
    }

}
