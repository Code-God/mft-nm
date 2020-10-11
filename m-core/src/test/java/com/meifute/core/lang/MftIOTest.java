package com.meifute.core.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/7/28
 * @Description
 */
class MftIOTest {

    @Test
    void isSocketOpened_NotAHost() {
        assertFalse(MftIO.isSocketOpened("abc",10));
    }

    @Test
    void isSocketOpened() throws IOException {
        assertFalse(MftIO.isSocketOpened("127.0.0.1",13333));
        try(ServerSocket a= new ServerSocket(13333,0)){
            assertTrue(MftIO.isSocketOpened("127.0.0.1",13333));
        }
    }

    @Test
    void findAvaliableRadomUserPort(){
        assertFalse(MftIO.isSocketOpened(MftIO.findRandomnAvalibalePort()));
    }
}