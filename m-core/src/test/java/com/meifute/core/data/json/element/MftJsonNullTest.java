package com.meifute.core.data.json.element;

import com.meifute.core.data.json.MftJsonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/19
 * @Description
 */
class MftJsonNullTest {

    @Test
    void parse() throws MftJsonException {
        MftJsonNull nul= new MftJsonNull();
        nul.parse("null");
        assertThrows(MftJsonException.class,()->{
           nul.parse("abc");
        });

        assertEquals("null",nul.toString());
    }
}