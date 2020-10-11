package com.meifute.core.lang.Date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
class MftDatetimeFormatterTest {


    @Test
    void formatCommonDatetime(){
        LocalDateTime localDateTime =LocalDateTime.of(1999,9,9,10,11,12);
        assertEquals("1999-09-09 10:11:12",MftDatetimeFormatter.formatCommonDatetime(localDateTime));
    }

    @Test
    void tryParse() {
        String s="1999-09-11T12:22:13";
        assertDatetime(s);

         s="1999-09-11    12:22:13";
        assertDatetime(s);

    }

    private void assertDatetime(String s) {
        LocalDateTime localDateTime = MftDatetimeFormatter.tryParse(s);
        assertEquals(1999,localDateTime.getYear());
        assertEquals(9,localDateTime.getMonth().getValue());
        assertEquals(11,localDateTime.getDayOfMonth());
        assertEquals(12,localDateTime.getHour());
        assertEquals(22,localDateTime.getMinute());
        assertEquals(13,localDateTime.getSecond());
    }
}