package com.meifute.core.data.json;

import com.meifute.core.data.json.element.MftJsonArray;
import com.meifute.core.data.json.element.MftJsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
class MftJsonTest {

    @Test
    void parse() throws Exception {
        String jsonString="{\"a\":123,\"b\":false,\"c\":[null,\"a\tb\",3.2e4,\"中国人\"]}";
        MftJsonObject object= MftJson.parse(jsonString);
        assertEquals(3,object.size());
        assertEquals("123",object.get("a").toString());
        assertEquals("false",object.get("b").toString());
        MftJsonArray array= (MftJsonArray) object.get("c");
        assertEquals(4,array.size());
        assertEquals("null",array.get(0).toString());
        assertEquals("a\tb",array.get(1).toString());
        assertEquals("32000.0",array.get(2).toString());
        assertEquals("中国人",array.get(3).toString());
    }



    @Test
    void parse_exception(){
        String jsonString="'abc'";
        assertThrows(MftJsonException.class,()->{
            MftJson.parse(jsonString);
        });
    }

}