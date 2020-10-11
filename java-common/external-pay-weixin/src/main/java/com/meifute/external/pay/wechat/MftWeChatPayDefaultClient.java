package com.meifute.external.pay.wechat;

import com.meifute.external.pay.wechat.notify.NotifyWeChatOrderRequest;
import com.meifute.external.pay.wechat.notify.NotifyWeChatOrderResponse;
import com.meifute.external.pay.wechat.query.QueryWeChatOrderRequest;
import com.meifute.external.pay.wechat.query.QueryWeChatOrderResponse;
import com.meifute.external.pay.wechat.unified.UnifiedWeChatPayRequest;
import com.meifute.external.pay.wechat.unified.UnifiedWeChatPayResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Classname MftWeChatPayDefaultClient
 * @Description
 * @Date 2020-07-31 17:09
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftWeChatPayDefaultClient implements MftWeChatPayClient {

    private String appId;

    private String mchId;

    private String key;

    public MftWeChatPayDefaultClient(String appId, String mchId, String key) {
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends WeChatPayResponse> T execute(WeChatPayRequest<T> request) {
        if (request instanceof UnifiedWeChatPayRequest) {
            return (T) weChatUnifiedOrder((UnifiedWeChatPayRequest) request);
        }
        if (request instanceof QueryWeChatOrderRequest) {
            return (T) queryWeChatOrder((QueryWeChatOrderRequest) request);
        }
        if (request instanceof NotifyWeChatOrderRequest) {
            return (T) notifyWeChatOrder((NotifyWeChatOrderRequest) request);
        }
        return null;
    }




    /**
     * 创建微信支付预付单
     *
     * @return
     */
    private UnifiedWeChatPayResponse weChatUnifiedOrder(UnifiedWeChatPayRequest unifiedWeChatPayRequest) {
        String paySendParam = getPaySendParam(unifiedWeChatPayRequest);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CommonUtil.GATEWAY_URL))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(paySendParam)).build();
        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            SortedMap<String, String> resultMap = CommonUtil.dom2Map(response.body());
            log.info("获取微信支付预付单 weChatUnifiedOrder resultMap :{}", resultMap);

            if (CommonUtil.checkPayResult(resultMap.get("return_code"), resultMap.get("result_code"))) {

                var nonceStr = CommonUtil.getRandomString(CommonUtil.RANDOM_STR_LENGTH);
                var timestamp = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                var prepayId = resultMap.get("prepay_id");
                var signParam = pottingMap(prepayId, nonceStr, timestamp);
                //二次签名
                var signAgain = CommonUtil.createSign(key, signParam);
                return UnifiedWeChatPayResponse.builder()
                        .appId(appId)
                        .partnerId(mchId)
                        .nonceStr(nonceStr)
                        .packAge("Sign=WXPay")
                        .timestamp(timestamp)
                        .prepayId(prepayId)
                        .sign(signAgain)
                        .returnMsg(resultMap.get("return_msg"))
                        .result(true)
                        .build();
            } else {
                return UnifiedWeChatPayResponse.builder()
                        .result(false)
                        .returnMsg(resultMap.get("err_code_des"))
                        .build();
            }
        } catch (IOException | InterruptedException e) {
            log.error("创建微信支付预付单异常:{}", e.getMessage());
        }
        return UnifiedWeChatPayResponse.builder()
                .result(false)
                .returnMsg("创建微信支付预付单失败")
                .build();
    }

    /**
     * 查询微信支付结果
     *
     * @return
     */
    public QueryWeChatOrderResponse queryWeChatOrder(QueryWeChatOrderRequest queryWeChatOrderRequest) {
        String paySendParam = getSendParam(queryWeChatOrderRequest);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CommonUtil.ORDER_QUERY))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(paySendParam)).build();

        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            SortedMap<String, String> resultMap = CommonUtil.dom2Map(response.body());

            log.info("查询微信支付结果 queryWeChatOrder resultMap :{}", resultMap);

            if (CommonUtil.checkPayResult(resultMap)) {
                return QueryWeChatOrderResponse.builder()
                        .totalFee(Integer.parseInt(resultMap.get("total_fee")))
                        .transactionId(resultMap.get("transaction_id"))
                        .result(true)
                        .returnMsg(resultMap.get("trade_state_desc"))
                        .build();
            } else {
                return QueryWeChatOrderResponse.builder()
                        .result(false)
                        .returnMsg(resultMap.get("trade_state_desc"))
                        .build();
            }
        } catch (IOException | InterruptedException e) {
            log.error("查询微信支付结果异常:{}", e.getMessage());
        }
        return QueryWeChatOrderResponse.builder()
                .result(false)
                .returnMsg("查询微信支付结果失败")
                .build();
    }


    /**
     * 回调通知验签
     *
     * @return
     */
    public NotifyWeChatOrderResponse notifyWeChatOrder(NotifyWeChatOrderRequest notifyWeChatOrderRequest) {
        SortedMap<String, String> notifyMap = CommonUtil.dom2Map(notifyWeChatOrderRequest.getBodyXml());
        log.info("微信支付回调 notifyWeChatOrder notify map :{}", notifyMap);
        if (CommonUtil.checkPayResult(notifyMap.get("result_code"), notifyMap.get("result_code"))) {
            if (CommonUtil.verifySign(key, notifyMap)) {
                return NotifyWeChatOrderResponse.builder()
                        .result(true)
                        .transactionId(notifyMap.get("transaction_id"))
                        .totalFee(Integer.parseInt(notifyMap.get("total_fee")))
                        .attach(notifyMap.get("notifyMap"))
                        .outTradeNo(notifyMap.get("out_trade_no"))
                        .returnMsg("支付成功")
                        .build();
            } else {
                NotifyWeChatOrderResponse.builder()
                        .result(false)
                        .returnMsg("签名错误")
                        .build();
            }
        }
        return NotifyWeChatOrderResponse.builder()
                .result(false)
                .returnMsg("正在支付中")
                .build();
    }



    public String getPaySendParam(UnifiedWeChatPayRequest weChatPayRequest) {
        TreeMap<String, Object> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", CommonUtil.getRandomString(32));
        parameters.put("body", weChatPayRequest.getBody());//描叙
        parameters.put("notify_url", weChatPayRequest.getNotifyUrl());
        parameters.put("out_trade_no", weChatPayRequest.getOutTradeNo());//商户订单号
        parameters.put("total_fee", weChatPayRequest.getTotalFee().multiply(BigDecimal.valueOf(100)).intValue()); //单位（分）
        parameters.put("spbill_create_ip", CommonUtil.getLocalIP());
        parameters.put("trade_type", "APP");
        parameters.put("limit_pay", "no_credit");
        parameters.put("attach", weChatPayRequest.getAttach());
        parameters.put("sign", CommonUtil.createSign(key, parameters));
        return CommonUtil.getRequestXml(parameters);
    }

    public SortedMap<String, Object> pottingMap(String prepayId, String nonceStr, String timestamp) {
        SortedMap<String, Object> signParam = new TreeMap<>();
        signParam.put("appid", appId);
        signParam.put("partnerid", mchId);
        signParam.put("prepayid", prepayId);
        signParam.put("package", "Sign=WXPay");
        signParam.put("noncestr", nonceStr);
        signParam.put("timestamp", timestamp);
        return signParam;
    }

    public String getSendParam(QueryWeChatOrderRequest weChatOrderRequest) {
        TreeMap<String, Object> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("out_trade_no", weChatOrderRequest.getOutTradeNo());//商户订单号
        parameters.put("nonce_str", CommonUtil.getRandomString(32));
        parameters.put("sign", CommonUtil.createSign(key, parameters));
        return CommonUtil.getRequestXml(parameters);
    }
}
