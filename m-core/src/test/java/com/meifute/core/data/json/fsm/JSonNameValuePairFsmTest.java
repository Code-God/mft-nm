package com.meifute.core.data.json.fsm;

import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/16
 * @Description
 */
class JSonNameValuePairFsmTest {

    @Test
    void initial() throws Exception {
        JSonNameValuePairFsm jSonNameValuePairFsm=new JSonNameValuePairFsm(
                new AbstractFsmStringParser.ParserStateObject("\"abc\":\"321\"",0),null);
        jSonNameValuePairFsm.parse();
        assertEquals("\"abc\"",jSonNameValuePairFsm.getFsmObject().getFirst().toJsonString());
        assertEquals("\"321\"",jSonNameValuePairFsm.getFsmObject().getSecond().toJsonString());
    }
}