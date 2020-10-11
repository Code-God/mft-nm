package com.meifute.core.lang;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/9
 * @Description
 */
class MftNumberTest {

    @ParameterizedTest
    @ValueSource(chars = {'a','A','1','0','2','9','f','F'})
    void isHexChar(char c) {
        assertTrue(MftNumber.isHexChar(c));
    }

    @ParameterizedTest
    @ValueSource(chars = {'\t','g','='})
    void isHexChar_false(char c) {
        assertFalse(MftNumber.isHexChar(c));
    }
}