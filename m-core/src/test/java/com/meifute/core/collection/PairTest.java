package com.meifute.core.collection;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/4
 * @Description
 */
class PairTest {

    @Test
    void pairTest() {
        Pair<String, LocalDate> pair =new Pair<>("abc",LocalDate.of(1999,7,8));
        assertEquals(1999,pair.getSecond().getYear());
        assertEquals("abc",pair.getFirst());
        assertEquals(Month.JULY,pair.getSecond().getMonth());
        assertEquals(8,pair.getSecond().getDayOfMonth());


    }

    @Test
    void testPairOf(){
       var pair=Pair.of("abc",798L);
       assertEquals("abc",pair.getFirst());
       assertEquals(798L,pair.getSecond());
    }


    @Test
    void testToString(){
        var pair = Pair.of("abc", 23);
        assertEquals("<abc:23>",pair.toString());

        pair= Pair.of(null,23);
        assertEquals("<null:23>",pair.toString());
    }
}