package com.meifute.core.http;

import com.meifute.core.lang.MftStrings;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

/**
 * 这个Http 客户端依赖于jdk 所提供的URLConnection
 *
 * @author wzeng
 * @date 2020/7/27
 * @Description  依赖jdk11 的http 客户端， 没有太大并发场景下的使用是合适的。
 */
public class MftHttpClient {

    HttpClient.Version version=HttpClient.Version.HTTP_1_1;
    long connection_timeout_milli=6000;
    HttpClient.Redirect redirect = HttpClient.Redirect.ALWAYS;
    HttpMethod method=HttpMethod.POST;
    String contentType="application/x-www-form-urlencoded";
    Map<String,String> parameters=null;

    public static MftHttpClient newClient(){
        MftHttpClient client= new MftHttpClient();
        return client;
    }

    public  MftHttpClient version(HttpClient.Version version){
        this.version=version;
        return this;
    }

    public MftHttpClient contentType(String contentType){
        this.contentType = contentType;
        return this;
    }

    public MftHttpClient redirect(HttpClient.Redirect redirect){
        this.redirect = redirect;
        return this;
    }

    public MftHttpClient connectTimeoutMilli(long milliSeconds){
        this.connection_timeout_milli=milliSeconds;
        return this;
    }

    public MftHttpClient httpMethod(HttpMethod httpMethod){
        this.method=httpMethod;
        return this;
    }

    public MftHttpClient parameters(Map<String,String> params){
        this.parameters=params;
        return this;
    }

    public HttpResponse execute(String url) throws IOException, InterruptedException {

        HttpClient client = buildClient();
        HttpRequest.Builder request= HttpRequest.newBuilder(URI.create(url));
        if(this.method.equals(HttpMethod.GET)){
            request.GET();
        } else if(this.method.equals(HttpMethod.POST)){
            request.header("Content-Type","application/x-www-form-urlencoded");
            request.POST(HttpRequest.BodyPublishers.ofString(parametersToString()));
        }
        HttpResponse<byte[]> response = client.send(request.build(),HttpResponse.BodyHandlers.ofByteArray());
        return response;
    }

    public static byte[] httpGet(String url) throws IOException, InterruptedException {
        HttpResponse<byte[]> client = MftHttpClient.newClient().httpMethod(HttpMethod.GET).execute(url);
        return client.body();
    }

    public static String httpGetString(String url) throws IOException, InterruptedException {
        return MftStrings.toUtf8String(MftHttpClient.httpGet(url));
    }

    public static byte[] httpPost(String url, Map<String,String> parameters) throws IOException, InterruptedException {
        HttpResponse<byte[]> client = MftHttpClient.newClient().httpMethod(HttpMethod.POST).parameters(parameters).execute(url);
        return client.body();
    }

    public static String httpPostString(String url, Map<String,String> parameters) throws IOException, InterruptedException {
        return MftStrings.toUtf8String(MftHttpClient.httpPost(url,parameters));
    }



    private HttpClient buildClient() {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMillis(this.connection_timeout_milli))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        return client;
    }

    private String parametersToString(){
        StringBuilder sb = new StringBuilder();
        if (parameters != null) {
            for (Map.Entry<String, String> params : parameters.entrySet()) {
                if (sb.length() == 0) {
                    //Do nothing.
                } else {
                    sb.append("&");
                }

                sb.append(URLEncoder.encode(params.getKey(), StandardCharsets.UTF_8)).append("=")
                        .append(URLEncoder.encode(params.getValue(), StandardCharsets.UTF_8));
            }
        }
        return sb.toString();
    }
}
