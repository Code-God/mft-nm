package com.meifute.core.http;

import com.meifute.core.lang.MftIO;
import com.meifute.core.lang.MftStrings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/7/27
 * @Description
 */
class MftHttpServerTest {

     int port = 18001;
    MftHttpServer httpServer=null;



    @BeforeEach
    void setUp() throws IOException {
        port = MftIO.findRandomnAvalibalePort();
         httpServer = MftHttpServer.newHttpServer(port)
                .addHander("/echo", HttpMethod.GET, new HttpHandler() {
                    @Override
                    public void handle(HttpExchange exchange) throws IOException {
                        exchange.sendResponseHeaders(200,0);
                        String query= Optional.ofNullable(exchange.getRequestURI().getQuery()).orElse("");
                        exchange.getResponseBody().write(("echo fromGet:"
                                +query).getBytes(StandardCharsets.UTF_8));
                       }
                })
                .addHander("/echo", HttpMethod.POST, new HttpHandler() {
                    @Override
                    public void handle(HttpExchange exchange) throws IOException {
                        exchange.sendResponseHeaders(200,0);
                        exchange.getResponseBody().write(("echo fromPost:"
                                +Optional.of(MftStrings.readAllInputStreamToUtf8(exchange.getRequestBody(),true))
                                .orElse("")).getBytes(StandardCharsets.UTF_8));
                    }
                }).addHander("/echo", null, new HttpHandler() {
                     @Override
                     public void handle(HttpExchange exchange) throws IOException {
                         exchange.sendResponseHeaders(200,0);
                         exchange.getResponseBody().write(("echo from MftServer:"+exchange.getRequestURI().getQuery()
                                 +Optional.of(MftStrings.readAllInputStreamToUtf8(exchange.getRequestBody(),true))
                                 .orElse("")).getBytes(StandardCharsets.UTF_8));
                     }
                 }).addHander("/exception", null, new HttpHandler() {
                     @Override
                     public void handle(HttpExchange exchange) throws IOException {
                         throw new IOException("Error for Testing");
                     }
                 });

        httpServer.start();
    }

    @AfterEach
    void tearDown() {
        if(httpServer!=null){
            httpServer.stop(1);
        }
    }

    @Test
    public void http_noParameters() throws IOException, InterruptedException {

        HttpResponse<byte[]> response = MftHttpClient.newClient().contentType("application/x-www-form-urlencoded")
                .redirect(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_2)
                .connectTimeoutMilli(3000)
                .httpMethod(HttpMethod.GET)
                .execute("http://localhost:"+port+"/echo");
        Assertions.assertEquals("echo fromGet:",MftStrings.toUtf8String(response.body()));

        HttpResponse<byte[]> responsePost = MftHttpClient.newClient().httpMethod(HttpMethod.POST).execute("http://localhost:"+port+"/echo/abc/edf?ad=ad");
        Assertions.assertEquals("echo fromPost:",MftStrings.toUtf8String(responsePost.body()));

        HttpResponse<byte[]> responseNotExist = MftHttpClient.newClient().httpMethod(HttpMethod.GET).execute("http://localhost:"+port+"/echo2?ad=ad");
        Assertions.assertEquals("HELLO FROM MEIFUTE",MftStrings.toUtf8String(responseNotExist.body()));

        HttpResponse<byte[]> responseNotExistPost = MftHttpClient.newClient().httpMethod(HttpMethod.POST).execute("http://localhost:"+port+"/echo?ad=ad");
        Assertions.assertEquals("echo fromPost:",MftStrings.toUtf8String(responseNotExistPost.body()));


        String s= MftHttpClient.httpGetString("http://localhost:"+port+"/echo");
        assertEquals("echo fromGet:",s);


    }

    @Test
    public void test_withParameters() throws IOException, InterruptedException {
        HttpResponse<byte[]> response = MftHttpClient.newClient().httpMethod(HttpMethod.GET).execute("http://localhost:"+port+"/echo?ad=ad");
        Assertions.assertEquals("echo fromGet:ad=ad",MftStrings.toUtf8String(response.body()));

        var params=new TreeMap<String,String>();
        params.put("key1","value1");
        params.put("key2","value2");
        HttpResponse<byte[]> responsePost = MftHttpClient.newClient().httpMethod(HttpMethod.POST).parameters(params).execute("http://localhost:"+port+"/echo?ad=ad");
        Assertions.assertEquals("echo fromPost:key1=value1&key2=value2",MftStrings.toUtf8String(responsePost.body()));
    }

    @Test
    public void test_exception() throws IOException, InterruptedException {
            String s= MftHttpClient.httpGetString("http://localhost:" + port + "/exception?ad=ad");
            assertTrue(s.contains("Error for Testing"));

        String p= MftHttpClient.httpPostString("http://localhost:" + port + "/exception?ad=ad",null);
        assertTrue(p.contains("Error for Testing"));
    }
}