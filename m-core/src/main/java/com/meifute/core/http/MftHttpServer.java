package com.meifute.core.http;

import com.meifute.core.lang.MftStrings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @author wzeng
 * @date 2020/7/27
 * @Description
 */
public class MftHttpServer {

    private HttpServer httpServer = null;

    public static MftHttpServer newHttpServer(int port) throws IOException {
        MftHttpServer mftServer = new MftHttpServer();
        mftServer.httpServer = HttpServer.create(new InetSocketAddress(port), 5);

        return mftServer;
    }

    Map<String,HttpHandler> pathMethods2HandlerMap=new HashMap<>();

    private HttpHandler getHandler(String pathString, HttpMethod hmethod){
        Path path= Path.of(pathString);
        do{
            String pathMethod=generatePathMethod(path.toString(),hmethod);
            if(pathMethods2HandlerMap.containsKey(pathMethod)){
                return pathMethods2HandlerMap.get(pathMethod);
            } else{
                path=path.getParent();
            }
        }while(!path.equals(path.getRoot()));
        return null;
    }

    public void start() {

        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String method=exchange.getRequestMethod();
                String path=exchange.getRequestURI().getPath();
                HttpHandler handler = getHandler(path,HttpMethod.valueOf(method));
                if(handler==null){
                    handler = getHandler(path,null);
                }
               try {
                   if (handler != null) {
                       handler.handle(exchange);
                   } else {
                       String response = "HELLO FROM MEIFUTE";
                       exchange.sendResponseHeaders(200, 0);
                       OutputStream os = exchange.getResponseBody();
                       os.write(response.getBytes(StandardCharsets.UTF_8));
                   }
               }catch(Exception e){
                   exchange.sendResponseHeaders(500,0);
                   OutputStream os = exchange.getResponseBody();
                   os.write(e.getMessage().getBytes(StandardCharsets.UTF_8));
                   os.write(MftStrings.toReadableString(e.getStackTrace()).getBytes(StandardCharsets.UTF_8));
                   os.close();
               }
               finally {
                   exchange.close();
               }
            }
        });
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.start();
    }

    public MftHttpServer addHander(String path, HttpMethod method, HttpHandler handler) {
        String pathMethod = generatePathMethod(path, method);
        this.pathMethods2HandlerMap.put(pathMethod,handler);
        return this;
    }

    private String generatePathMethod(String path, HttpMethod method) {
        if(method !=null){
            return path + "_" + method.name();
        } else{
            return path;
        }
    }

    public void stop(int seconds) {
        if (this.httpServer != null) {
            if(seconds>=0) {
                this.httpServer.stop(seconds);
            } else{
                this.httpServer.stop(1);
            }
        }
    }
}
