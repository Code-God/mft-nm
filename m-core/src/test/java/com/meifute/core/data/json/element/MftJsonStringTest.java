package com.meifute.core.data.json.element;

import com.meifute.core.data.json.MftJsonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/19
 * @Description
 */
class MftJsonStringTest {

    @Test
    void testToString() {
        String s = "abc\tc\b\f\n\r\tab";
        MftJsonString jsonString = new MftJsonString(s);
        assertEquals(s,jsonString.toString());
        assertEquals("\"abc\\tc\\b\\f\\n\\r\\tab\"",jsonString.toJsonString());

    }

    @Test
    void parse() throws MftJsonException {
        String s = "\"abc\tc\b\f\n\r\tab\"";
        MftJsonString jsonString = new MftJsonString("");
        jsonString.parse(s);
        assertEquals("\"abc\\tc\\b\\f\\n\\r\\tab\"",jsonString.toJsonString());
    }

    @Test
    void compareTo_equal_hashcode() {
        MftJsonString jsonString1 = new MftJsonString("1");
        MftJsonString jsonString2 = new MftJsonString("1");
        MftJsonString jsonString3 = new MftJsonString("2");
        assertEquals(jsonString1,jsonString2);
        assertTrue(jsonString1.equals(jsonString2));
        assertTrue(jsonString1.hashCode()==jsonString2.hashCode());
        assertTrue(jsonString1.compareTo(jsonString3)<0);
        assertTrue(jsonString3.compareTo(jsonString1)>0);
        assertTrue(jsonString1.compareTo(jsonString2)==0);
    }
}