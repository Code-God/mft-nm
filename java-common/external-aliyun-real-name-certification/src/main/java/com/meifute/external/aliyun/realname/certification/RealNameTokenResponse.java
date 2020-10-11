package com.meifute.external.aliyun.realname.certification;

import java.io.Serializable;

/**
 * @Classname RealNameAuthResponse
 * @Description
 * @Date 2020-07-23 16:21
 * @Created by MR. Xb.Wu
 */
public class RealNameTokenResponse implements Serializable {

    /**
     * 认证token, 表达一次认证会话
     */
    private String token;

    /**
     * 认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
     */
    private String ticketId;

    /**
     * authBiz,规定参数
     */
    private String biz;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }


}
