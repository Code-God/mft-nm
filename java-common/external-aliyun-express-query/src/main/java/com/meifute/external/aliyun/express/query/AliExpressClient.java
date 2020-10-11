package com.meifute.external.aliyun.express.query;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AliExpress
 * @Description
 * @Date 2020-07-28 13:23
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class AliExpressClient {

    private static final String HOST = "http://aliapi.kuaidi.com";
    private static final String PATH = "/kuaidiinfo";


    public static AliExpressResult getAliExpress(String appCode, String expressCode) {
        String com = "";
        if (expressCode.contains("JD")) {
            com = "jd";
        }
        AliExpressResult result = new AliExpressResult();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("nu", expressCode);
        queryMap.put("com", com);
        queryMap.put("muti", "0");
        queryMap.put("order", "desc");
        queryMap.put("id", appCode);
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(buildUrl(HOST, PATH, queryMap)))
                    .header("Authorization", "APPCODE " + appCode)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            if (body != null && !body.isEmpty()) {
                result = JSONObject.parseObject(body, AliExpressResult.class);
                log.info("result:{}", result);
                if (result.getReason().contains("单号不存在") || result.getReason().contains("过期")) {
                    result.setReason("暂无物流信息");
                }
            }
        } catch (Exception e) {
            log.error("getAliExpress Error :{}", e.getMessage());
            result.setReason("暂无物流信息");
        }
        return result;
    }

    private static String buildUrl(String host, String path, Map<String, String> queryMap) {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (path != null && !path.isEmpty()) {
            sbUrl.append(path);
        }
        StringBuilder sbQuery = new StringBuilder();
        if (null != queryMap) {
            queryMap.forEach((k, v) -> {
                if (sbQuery.length() > 0) {
                    sbQuery.append("&");
                }
                sbQuery.append(k).append("=").append(URLEncoder.encode(v, StandardCharsets.UTF_8));
            });
            if (sbQuery.length() > 0) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }
}
