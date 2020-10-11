package com.meifute.external.jiguang;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.utils.StringUtils;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @Classname JSmsClient
 * @Description 极光短信
 * @Date 2020-07-22 10:23
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftJSmsClient {

    private String secret;

    private String appKey;

    private SMSClient smsClient;

    public MftJSmsClient(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
        checkNotNullConfig();
        smsClient = new SMSClient(this.secret, this.appKey);
    }


    /**
     * 发送短信验证码
     *
     * @param phoneNumber 手机号码
     * @param tempId      模版id
     * @return
     */
    public String sendSMSCode(String phoneNumber, int tempId) {
        isPhone(phoneNumber);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phoneNumber)
                .setTempId(tempId)
                .build();
        SendSMSResult res = null;
        try {
            res = smsClient.sendSMSCode(payload);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("发送短信验证码出现异常: {}", e.getMessage());
        }
        return res == null ? null : res.getMessageId();
    }

    /**
     * 验证短信验证码是否有效
     *
     * @param code  短信验证码
     * @param msgId msg_id
     */
    public boolean verifySMSCode(String code, String msgId) {
        ValidSMSResult res = null;
        try {
            res = smsClient.sendValidSMSCode(msgId, code);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("验证短信验证码出现异常: {}", e.getMessage());
        }
        return res == null ? false : res.getIsValid();
    }

    /**
     * 发送文本通知短信
     *
     * @param phoneNumber 手机号码
     * @param tempParaMap 模版参数（map-> key为极光模版对应的参数名，value为具体的值）
     * @param tempId      模版id
     * @return
     */
    public boolean sendTemplateSMS(String phoneNumber, Map<String, String> tempParaMap, int tempId) {
        isPhone(phoneNumber);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phoneNumber)
                .setTempPara(tempParaMap)
                .setTempId(tempId)
                .build();
        SendSMSResult res = null;
        try {
            res = smsClient.sendTemplateSMS(payload);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("发送文本短信出现异常: {}", e.getMessage());
        }
        if (res != null && res.getResponseCode() == 200) {
            log.info("发送文本通知短信成功");
            return true;
        }
        return false;
    }

    private void isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (!phone.matches(regex)) {
            log.error("请输入正确的手机号: {}", phone);
            throw new RuntimeException("请输入正确的手机号");
        }
    }

    private void checkNotNullConfig() {
        if (StringUtils.isEmpty(this.secret) || StringUtils.isEmpty(this.appKey)) {
            log.error("极光配置信息不能为空");
            throw new RuntimeException("极光配置信息不能为空");
        }
    }

}
