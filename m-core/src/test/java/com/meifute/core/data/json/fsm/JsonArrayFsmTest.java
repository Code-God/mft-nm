package com.meifute.core.data.json.fsm;

import com.meifute.core.data.json.element.MftJsonArray;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/14
 * @Description
 */
class JsonArrayFsmTest {

    @Test
    void initial() throws Exception {
        JsonArrayFsm afsm=new JsonArrayFsm(new AbstractFsmStringParser.ParserStateObject("[1,3,2,\"a\"]",0),null);

        afsm.parse();
        MftJsonArray array= afsm.getFsmObject();
        assertEquals(4,array.getArray().size());
        assertEquals("[1,3,2,\"a\"]",array.toJsonString());
    }

    @Test
    void initial_empty() throws Exception {
        JsonArrayFsm afsm2=new JsonArrayFsm(new AbstractFsmStringParser.ParserStateObject("[]",0),null);
        afsm2.parse();
        MftJsonArray array= afsm2.getFsmObject();
        assertEquals(0,array.getArray().size());
    }
}