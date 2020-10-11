package com.meifute.core.data.json.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/19
 * @Description
 */
class MftJsonObjectTest {

    @Test
    void toString_includingToJsonString() {
        MftJsonObject object =new MftJsonObject();
        object.put(new MftJsonString("abc"), new MftJsonNumber(123));
        object.put(new MftJsonString("ab"),new MftJsonNull());
        object.put("ccd",new MftJsonString("cde"));
        assertEquals("{\"ab\":null,\"abc\":123,\"ccd\":\"cde\"}",object.toString());
        assertTrue(object.containsKey("abc"));
    }
}