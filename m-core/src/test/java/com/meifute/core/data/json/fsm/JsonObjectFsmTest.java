package com.meifute.core.data.json.fsm;

import com.meifute.core.data.json.element.MftJsonNumber;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/16
 * @Description
 */
class JsonObjectFsmTest {

    @Test
    void object_single() throws Exception {
        JsonObjectFsm jof=new JsonObjectFsm(new AbstractFsmStringParser.ParserStateObject(
                "{\"abc\":123}",0),null);
        jof.parse();
        assertEquals(1,jof.getFsmObject().size());
        assertTrue(jof.getFsmObject().containsKey("abc"));
        assertEquals(new MftJsonNumber(123),jof.getFsmObject().get("abc"));

    }

    @Test
    void object_threeElement() throws Exception {
        JsonObjectFsm jof=new JsonObjectFsm(new AbstractFsmStringParser.ParserStateObject(
                "{\"abc\":123,\"a\":true,\"ffg232\":null}",0),null);
        jof.parse();
        assertEquals(3,jof.getFsmObject().size());
        assertTrue(jof.getFsmObject().containsKey("ffg232"));
        assertFalse(jof.getFsmObject().containsKey("iii"));
        assertEquals(new MftJsonNumber(123),jof.getFsmObject().get("abc"));
        assertEquals("true",jof.getFsmObject().get("a").toString());
        assertEquals("null",jof.getFsmObject().get("ffg232").toString());

    }

    @Test
    void object_array() throws Exception {
        JsonObjectFsm jof=new JsonObjectFsm(new AbstractFsmStringParser.ParserStateObject(
                "{\"abc\":123,\"a\":[true,123,\"ab\"],\"ffg232\":null}",0),null);
        jof.parse();
        assertEquals(3,jof.getFsmObject().size());
        assertTrue(jof.getFsmObject().containsKey("ffg232"));
        assertFalse(jof.getFsmObject().containsKey("iii"));
        assertEquals(new MftJsonNumber(123),jof.getFsmObject().get("abc"));
        assertEquals("[true,123,\"ab\"]",jof.getFsmObject().get("a").toString());
        assertEquals("null",jof.getFsmObject().get("ffg232").toString());

    }
}