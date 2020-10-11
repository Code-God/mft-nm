package com.meifute.core.lang;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author wzeng
 * @date 2020/7/28
 * @Description
 */
public class MftIO {

    public static  boolean isSocketOpened(String ip,int port)  {
        try(Socket client = new Socket(ip,port)){
           return true;
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }


    public static int findRandomnAvalibalePort(){
        Random r= new Random(System.currentTimeMillis());
        int port = getRandomUserPort(r);
        while(true){
            if(!isSocketOpened("127.0.0.1",port)){
                return port;
            } else{
                port = getRandomUserPort(r);
            }
        }
    }

    private static int getRandomUserPort(Random r) {
        int port;
        port = r.nextInt(64000);
        port+=1023;
        return port;
    }

    public static boolean isSocketOpened(int port) {
        return isSocketOpened("127.0.0.1",port);
    }
}
