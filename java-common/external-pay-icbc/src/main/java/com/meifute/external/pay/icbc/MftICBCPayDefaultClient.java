package com.meifute.external.pay.icbc;

import com.alibaba.fastjson.JSONObject;
import com.icbc.agrat.pay.McClientTools;
import com.icbc.api.IcbcConstants;
import com.icbc.api.utils.IcbcSignature;
import com.icbc.api.utils.WebUtils;
import com.meifute.external.pay.icbc.biz.*;
import com.meifute.external.pay.icbc.notify.ICBCPayNotifyRequest;
import com.meifute.external.pay.icbc.notify.ICBCPayNotifyResponse;
import com.meifute.external.pay.icbc.notify.IcbcResultBizContent;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Classname ICBCPayClient
 * @Description
 * @Date 2020-07-31 12:02
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftICBCPayDefaultClient implements MftICBCPayClient {

    private String appId;
    private String merId;
    private String merAcct;
    private String merPrtclNo;

    public MftICBCPayDefaultClient(String appId, String merId, String merAcct, String merPrtclNo) {
        this.appId = appId;
        this.merId = merId;
        this.merAcct = merAcct;
        this.merPrtclNo = merPrtclNo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ICBCPayResponse> T execute(ICBCPayRequest<T> request) {
        if (request instanceof ICBCPayBizContentSignRequest) {
            return (T) bizContentSign((ICBCPayBizContentSignRequest) request);
        }
        if(request instanceof ICBCPayNotifyRequest) {
            return (T) notifyVerifyEPay((ICBCPayNotifyRequest) request);

        }
        return null;
    }


    /**
     * 工行支付封装 bizContent
     * @param request
     * @return
     */
    private ICBCPayBizContentSignResponse bizContentSign(ICBCPayBizContentSignRequest request) {
        OrderEntity_ICBC_WAPB_THIRD_MFT orderEntity = new OrderEntity_ICBC_WAPB_THIRD_MFT();
        //1. 商户签名数据准备，这里的送值必须与客户端的保持一致
        orderEntity.setUserKey(request.getCertPrivateKey());
        orderEntity.setAppId(appId);
        orderEntity.setFormat(IcbcConstants.FORMAT_JSON);
        orderEntity.setCharset(IcbcConstants.CHARSET_UTF8);
        orderEntity.setSignType(IcbcConstants.SIGN_TYPE_RSA2);
        //消息通讯唯一编号，保持每笔交易的唯一性
        orderEntity.setMsgId(UUID.randomUUID().toString());
        orderEntity.setTimestamp(String.valueOf(System.currentTimeMillis()));

        //2. biz_content数据封装
        Map<String, String> bizContent = putBizContentMap(request);
        orderEntity.setBizContent(bizContent);
        try {
            McClientTools.ClientType clientType = McClientTools.ClientType.Null;
            switch (request.getClientType()) {
                case "1":
                    clientType = McClientTools.ClientType.iPhoneClient;
                    break;
                case "2":
                    clientType = McClientTools.ClientType.AndroidClient;
                    break;
                case "23":
                    clientType = McClientTools.ClientType.WeChatApp;
                    break;
                case "24":
                    clientType = McClientTools.ClientType.AliApp;
                    break;
            }
            //3. 商户签名流程处理
            FormDataICBC formData = PackFormDataICBC.createFormData_ICBC_WAPB_THIRD_10(orderEntity, clientType);
            //4. 返回结果
            return ICBCPayBizContentSignResponse.builder()
                    .appid(formData.getAppId())
                    .msgid(formData.getMsgId())
                    .format(formData.getFormat())
                    .charset(formData.getCharset())
                    .signType(formData.getSignType())
                    .timestampBack(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(formData.getTimestamp()))))
                    .tranData(formData.getTranData())
                    .merSignMsg(formData.getMerSignMsg())
                    .wxAppId(bizContent.get(IcbcOrder.SHOPAPP_ID))
                    .build();
        } catch (Exception e) {
            log.error("工行支付封装 bizContent 异常: {}", e.getMessage());
        }
        return null;
    }

    private Map<String, String> putBizContentMap(ICBCPayBizContentSignRequest request) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime time = LocalDateTime.now();
        Map<String, String> bizContent = new LinkedHashMap<>();
        bizContent.put("interfaceName", "ConsumptionApForSdk");
        bizContent.put("interfaceVersion", "V1");
        bizContent.put("orderDate", dtf.format(time));
        bizContent.put("orderid", request.getOrderId());
        bizContent.put("amount", String.valueOf(request.getAmount().multiply(BigDecimal.valueOf(100)).intValue()));
        bizContent.put("installmentTimes", "1");
        bizContent.put("curType", "001");
        bizContent.put("merID", merId);
        bizContent.put("merAcct", merAcct);
        bizContent.put("shopAppID", request.getShopAppId()); //微信appId
        bizContent.put("expireTime", "85000");
        bizContent.put("appName", request.getAppName());
        bizContent.put("icbcappid", appId);
        bizContent.put("merURL", request.getNotifyUrl());
        bizContent.put("notifyType", "HS");
        bizContent.put("resultType", "0");
        bizContent.put("limitPay", "no_balance");
        bizContent.put("isSupportDISCOUFlag", "1");
        bizContent.put("thirdPartyFlag", "1");
        bizContent.put("mer_prtcl_no", merPrtclNo);
        bizContent.put("returnUrl", request.getReturnUrl());
        bizContent.put("clientType", request.getClientType());
        return bizContent;
    }

    /**
     * 工行回调验签
     * @param request
     * @return
     */
    private ICBCPayNotifyResponse notifyVerifyEPay(ICBCPayNotifyRequest request) {
        Map<String, String> map = request.getMap();
        IcbcResultBizContent bizContent = JSONObject.parseObject(map.get("biz_content"), IcbcResultBizContent.class);
        String returnCode = bizContent.getReturn_code();
        String msgId = bizContent.getMsg_id();

        if (!"0".equals(returnCode)) {
            return ICBCPayNotifyResponse.builder()
                    .result(false)
                    .msgId(msgId)
                    .returnMsg("工行支付业务未处理成功")
                    .build();
        }

        try {
            String outTradeNo = bizContent.getOut_trade_no();
            String tradeNo = bizContent.getOrder_id();
            String amount = bizContent.getPayment_amt();

            //path与@RequestMapping中的value一致
            String signStr = WebUtils.buildOrderedSignStr(request.getRequestMappingPath(), map);

            //验证工行上行网关RSA签名
            boolean flag = IcbcSignature.verify(signStr, map.get("sign_type"), request.getPublicKey(), map.get("charset"), map.get("sign"));

            if (flag) {
                return ICBCPayNotifyResponse.builder()
                        .result(true)
                        .msgId(msgId)
                        .outTradeNo(outTradeNo)
                        .tradeNo(tradeNo)
                        .returnMsg("验签成功")
                        .amount(Integer.parseInt(amount))
                        .build();
            }
        } catch (Exception e) {
            log.info("工行回调验签异常 notifyVerifyEPay error : {0}", e);
        }
        return ICBCPayNotifyResponse.builder()
                .result(false)
                .msgId(msgId)
                .returnMsg("验签失败")
                .build();
    }

}
