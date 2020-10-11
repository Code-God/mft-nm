package com.meifute.external.jdexpress;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.meifute.external.jdexpress.cancelorder.JdCancelOrderClient;
import com.meifute.external.jdexpress.cancelorder.JdCancelOrderRequest;
import com.meifute.external.jdexpress.cancelorder.JdCancelOrderResponse;
import com.meifute.external.jdexpress.push.JdExpressPushClient;
import com.meifute.external.jdexpress.push.JdExpressPushRequest;
import com.meifute.external.jdexpress.push.JdExpressPushResponse;
import com.meifute.external.jdexpress.queryorder.JdQueryOrderClient;
import com.meifute.external.jdexpress.queryorder.JdQueryOrderRequest;
import com.meifute.external.jdexpress.queryorder.JdQueryOrderResponse;
import com.meifute.external.jdexpress.serinalnumber.JdSerialNumberClient;
import com.meifute.external.jdexpress.serinalnumber.JdSerialNumberRequest;
import com.meifute.external.jdexpress.serinalnumber.JdSerialNumberResponse;
import com.meifute.external.jdexpress.stock.JdQueryStockClient;
import com.meifute.external.jdexpress.stock.JdQueryStockRequest;
import com.meifute.external.jdexpress.stock.JdQueryStockResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * @Classname MftJdClient
 * @Description
 * @Date 2020-07-23 16:57
 * @Created by MR. Xb.Wu
 */
public class MftDefaultJdClient implements MftJdClient {

    private String serverUrl;
    private String appKey;
    private String appSecret;
    private String accessToken;

    private JdClient client;


    public MftDefaultJdClient(String serverUrl, String accessToken, String appKey, String appSecret) {
        this.serverUrl = serverUrl;
        this.accessToken = accessToken;
        this.appKey = appKey;
        this.appSecret = appSecret;
        client = new DefaultJdClient(serverUrl, accessToken, appKey, appSecret);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractResponse> T execute(JdRequest<T> request) {
        if (request instanceof JdExpressPushRequest) {
            JdExpressPushClient jdClient = new JdExpressPushClient(client, (JdExpressPushRequest) request);
            JdExpressPushResponse response = jdClient.execute();
            return (T) response;
        }
        if (request instanceof JdQueryOrderRequest) {
            JdQueryOrderClient jdClient = new JdQueryOrderClient(client, (JdQueryOrderRequest) request);
            JdQueryOrderResponse response = jdClient.execute();
            return (T) response;
        }
        if (request instanceof JdCancelOrderRequest) {
            JdCancelOrderClient jdClient = new JdCancelOrderClient(client, (JdCancelOrderRequest) request);
            JdCancelOrderResponse response = jdClient.execute();
            return (T) response;
        }
        if (request instanceof JdQueryStockRequest) {
            JdQueryStockClient jdClient = new JdQueryStockClient(client, (JdQueryStockRequest) request);
            JdQueryStockResponse response = jdClient.execute();
            return (T) response;
        }
        if (request instanceof JdSerialNumberRequest) {
            JdSerialNumberClient jdClient = new JdSerialNumberClient(client, (JdSerialNumberRequest) request);
            JdSerialNumberResponse response = jdClient.execute();
            return (T) response;
        }
        return null;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static void main(String[] args) throws ParseException {
//        LocalDateTime dateTime = LocalDateTime.parse("2020年01月", DateTimeFormatter.ofPattern("yyyy年MM月"));
//        System.out.println(dateTime);

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
//        LocalDate localDate = LocalDate.parse("2020-01", dateTimeFormatter);
//        LocalDateTime localDateTime = localDate.atStartOfDay();
//        System.out.println(localDateTime);

        SimpleDateFormat str = new SimpleDateFormat("yyyy年MM日");//时间格bai式du
        Date parse = str.parse("2020年1");

        LocalDateTime localDateTime = LocalDateTime.ofInstant(parse.toInstant(), ZoneId.systemDefault());
        System.out.println(localDateTime);
    }
}
