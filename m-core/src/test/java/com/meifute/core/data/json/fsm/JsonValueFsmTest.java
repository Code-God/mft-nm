package com.meifute.core.data.json.fsm;

import com.meifute.core.lang.MftStrings;
import com.meifute.core.tool.fsm.AbstractFsmStringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/13
 * @Description
 */
class JsonValueFsmTest {

    @Test
    void initial() throws Exception {
        JsonValueFsm valueFsm =new JsonValueFsm(new AbstractFsmStringParser.ParserStateObject("\"abc\"",0),null);
        valueFsm.parse();
        assertEquals("\"abc\"",valueFsm.getFsmObject().toJsonString());
        System.out.println(MftStrings.toReadableString(valueFsm.getAllStateTransferPath()));
    }
}