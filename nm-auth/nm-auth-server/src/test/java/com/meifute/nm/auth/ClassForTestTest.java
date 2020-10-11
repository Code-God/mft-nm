package com.meifute.nm.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/17
 * @Description
 */
class ClassForTestTest {

    @Test
    void getP() {
        ClassForTest classForTest =new ClassForTest();
        classForTest.setP(2);
        assertEquals(2,classForTest.getP());
    }
}