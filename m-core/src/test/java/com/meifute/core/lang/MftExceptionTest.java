package com.meifute.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/7/30
 * @Description
 */
class MftExceptionTest {

    @Test
    void getErrorCode() {
        MftException mftException= new MftException("test",2);
        assertEquals("test",mftException.getMessage());
        assertEquals(2,mftException.getErrorCode());
    }
}