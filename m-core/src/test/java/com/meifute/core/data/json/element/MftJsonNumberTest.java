package com.meifute.core.data.json.element;

import com.meifute.core.data.json.MftJsonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/19
 * @Description
 */
class MftJsonNumberTest {

    @Test
    void parse_exception() {
        MftJsonNumber mftJsonNumber =new MftJsonNumber(1);
        assertThrows(MftJsonException.class,()->{
           mftJsonNumber.parse("abc");
        });
    }

    @Test
    void compareTo() {
        MftJsonNumber mftJsonNumber1=new MftJsonNumber(1);
        MftJsonNumber mftJsonNumber2 =new MftJsonNumber(1L);
        MftJsonNumber mftJsonNumber3=new MftJsonNumber(2);
        assertTrue(mftJsonNumber1.compareTo(mftJsonNumber2)==0);
        assertTrue(mftJsonNumber1.compareTo(mftJsonNumber3)<0);
        assertTrue(mftJsonNumber3.compareTo(mftJsonNumber1)>0);
        assertEquals(mftJsonNumber1,mftJsonNumber2);
        assertEquals(mftJsonNumber1.hashCode(),mftJsonNumber2.hashCode());
    }
}