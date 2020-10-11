package com.meifute.external.pay.icbc.biz;

import com.icbc.agrat.pay.McClientTools;
import com.icbc.api.IcbcApiException;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.utils.IcbcSignature;
import com.icbc.api.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Classname PackFormData
 * @Description TODO
 * @Date 2020-05-09 17:22
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class PackFormDataICBC {

    public static FormDataICBC createFormData_ICBC_WAPB_THIRD_10(OrderEntity_ICBC_WAPB_THIRD_MFT orderEntity, McClientTools.ClientType clientType) {
        String tranData_json = JSON.toJSONString(orderEntity.getBizContent());

        FormDataICBC formData = new FormDataICBC();
        formData.setAppId(orderEntity.getAppId());
        formData.setMsgId(orderEntity.getMsgId());
        formData.setFormat(orderEntity.getFormat());
        formData.setCharset(orderEntity.getCharset());
        formData.setEncryptType(orderEntity.getEncryptType());
        formData.setSignType(orderEntity.getSignType());
        formData.setTimestamp(orderEntity.getTimestamp());
        formData.setCa(orderEntity.getCa());
        formData.setTranData(tranData_json);

        Map<String, String> signMsgMap = null;
        try {
            signMsgMap = sign_ICBC_WAPB_THIRD_10(orderEntity, formData.getTranData(), McClientTools.getClientString(clientType));
        } catch (Exception e) {
            log.info("报错了------------:{0}", e);
        }
        if (null != signMsgMap && !signMsgMap.isEmpty()) {
            formData.setMerSignMsg(signMsgMap.get("sign"));
        } else {
            formData = null;
        }
        return formData;
    }

    public static Map<String, String> sign_ICBC_WAPB_THIRD_10(OrderEntity_ICBC_WAPB_THIRD_MFT orderEntity, String tranData_json, String clientType) {
        try {
            SignVO signVO = new SignVO();
            signVO.setApp_id(orderEntity.getAppId());
            signVO.setMsg_id(orderEntity.getMsgId());
            signVO.setFormat(orderEntity.getFormat());
            signVO.setCharset(orderEntity.getCharset());
            signVO.setEncrypt_type(orderEntity.getEncryptType());
            signVO.setClientType(clientType);
            signVO.setBiz_content(tranData_json);
            signVO.setCa(orderEntity.getCa());
            signVO.setPrivateKey(orderEntity.getUserKey());
            signVO.setTimestamp(orderEntity.getTimestamp());
            signVO.setSign_type(orderEntity.getSignType());
            Map<String, String> map = icbcSign(tranData_json, signVO);
            log.info("******************************************************************");
            log.info("* 返回商户签名成功信息**************：" + map.get("sign"));
            return map;
        } catch (Exception e) {
            log.info("* 订单数据签名失败：e=" + e.getMessage());
        }
        return null;
    }

    private static Map<String, String> icbcSign(String bizContentStr, SignVO signVO) {
        String path;
        if (!"23".equals(signVO.getClientType()) && !"24".equals(signVO.getClientType())) {
            path = "/api/cardbusiness/epay/consumptionepayforsdk/V1";
        } else {
            path = "/api/cardbusiness/aggregatepay/b2c/online/consumptionapforsdk/V1";
        }
        Map<String, String> params = new HashMap<>();

        params.put("app_id", signVO.getApp_id());
        params.put("sign_type", signVO.getSign_type());
        params.put("charset", signVO.getCharset());
        params.put("format", signVO.getFormat());
        params.put("ca", signVO.getCa());
        params.put("app_auth_token", "");
        params.put("msg_id", signVO.getMsg_id());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        params.put("timestamp", df.format(Long.parseLong(signVO.getTimestamp())));
        params.put("biz_content", bizContentStr);

        String strToSign = WebUtils.buildOrderedSignStr(path, params);
        String signedStr = null;
        try {
            signedStr = IcbcSignature.sign(strToSign, signVO.getSign_type(), signVO.getPrivateKey(), signVO.getCharset(), null);
            log.info("******************************************************************");
            log.info("* 返回商户签名成功信息：" + signedStr);
        } catch (IcbcApiException e) {
            log.info("* 订单数据签名失败：e=" + e.getMessage());
        }
        params.put("sign", signedStr);
        return params;
    }
}
