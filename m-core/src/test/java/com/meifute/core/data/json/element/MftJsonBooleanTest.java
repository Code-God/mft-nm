package com.meifute.core.data.json.element;

import com.meifute.core.data.json.MftJsonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/19
 * @Description
 */
class MftJsonBooleanTest {

    @Test
    void parse() throws MftJsonException {
        MftJsonBoolean b =new MftJsonBoolean(false);
        assertEquals(false, b.value);
        b.parse("true");
        assertEquals(true,b.value);
        b.parse("false");
        assertEquals(false, b.value);
    }
}