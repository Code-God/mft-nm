package com.meifute.core.data.json;

import com.meifute.core.collection.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
class JsonUtilTest {

    @Test
    void isWhiteSpace() {
        assertTrue(JsonUtil.isWhiteSpace(' '));
        assertTrue(JsonUtil.isWhiteSpace('\r'));
        assertTrue(JsonUtil.isWhiteSpace('\n'));
        assertTrue(JsonUtil.isWhiteSpace('\t'));
    }

    @Test
    void delimiter() {
        assertTrue(JsonUtil.isObjectBegin('{'));
        assertTrue(JsonUtil.isObjectEnd('}'));
        assertTrue(JsonUtil.isArrayBegin('['));
        assertTrue(JsonUtil.isArrayEnd(']'));
    }


    @ParameterizedTest
    @ValueSource(strings = {"3","-3","3.3","-3.3","0.9","3.2e22"})
    void isJsonNumber(String s){
        assertTrue(JsonUtil.isJsonNumber(s));
        System.out.println(Double.parseDouble(s));
    }

    @ParameterizedTest
    @ValueSource(strings={"a","3.a","x8",".9"})
    void isJsonNumber_false(String s){
        assertFalse(JsonUtil.isJsonNumber(s));
    }
}