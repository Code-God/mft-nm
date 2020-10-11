package com.meifute.external.jiguang;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @Classname JPushClient
 * @Description 极光推送
 * @Date 2020-07-22 11:46
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class MftJPushClient {

    private String secret;

    private String appKey;

    private JPushClient jPushClient;

    public MftJPushClient(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
        checkNotNullConfig();
        jPushClient = new JPushClient(this.secret, this.appKey);
    }


    /**
     * 给平台的一个或者一组标签发送通知
     *
     * @param content        推送的信息
     * @param tagsList       注册在极光的用户标签id 通常为userId
     * @param extras         扩展字段
     * @param apnsProduction 环境标志位，生产为true，测试为false
     */
    public boolean sendPush(String content, List<String> tagsList, Map<String, String> extras, boolean apnsProduction) {
        PushPayload payload = allPlatformAndTag(content, tagsList, extras, apnsProduction);
        PushResult res = null;
        try {
            res = jPushClient.sendPush(payload);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("极光推送出现异常: {}", e.getMessage());
        }
        if (res != null && res.getResponseCode() == 200) {
            log.info("极光推送通知成功");
            return true;
        }
        return false;
    }

    /**
     * 给所有平台的所有用户发通知
     *
     * @param content
     * @param extras
     * @param apnsProduction
     */
    public boolean sendPushAllAudience(String content, Map<String, String> extras, boolean apnsProduction) {
        PushPayload payload = buildPushObjectAllAliasAlert(content, extras, apnsProduction);
        PushResult res = null;
        try {
            res = jPushClient.sendPush(payload);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("对所有用户推送出现异常: {}", e.getMessage());
        }
        if (res != null && res.getResponseCode() == 200) {
            log.info("对所有用户推送消息成功");
            return true;
        }
        return false;
    }


    private void checkNotNullConfig() {
        if (StringUtils.isEmpty(this.secret) || StringUtils.isEmpty(this.appKey)) {
            log.error("极光配置信息不能为空");
            throw new RuntimeException("极光配置信息不能为空");
        }
    }

    private PushPayload allPlatformAndTag(String alert, List<String> tagsList, Map<String, String> extras, boolean apnsProduction) {
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagsList))
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(apnsProduction).build())
                .build();
    }

    private PushPayload buildPushObjectAllAliasAlert(String message, Map<String, String> extras, boolean apnsProduction) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(message)
                        .addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(apnsProduction).build())
                .build();
    }

}
