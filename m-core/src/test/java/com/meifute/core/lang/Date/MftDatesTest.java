package com.meifute.core.lang.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/7/13
 * @Description
 */
class MftDatesTest {

    @Test
    void toLocalDatetime() {
        Date date = new Date();
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        LocalDateTime localDateTime = MftDates.toLocalDatetime(date);
        assertEquals(calendar.get(Calendar.YEAR),localDateTime.getYear());
        assertEquals(calendar.get(Calendar.MONTH),localDateTime.getMonth().getValue()-1);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH),localDateTime.getDayOfMonth());
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY),localDateTime.getHour());
        assertEquals(calendar.get(Calendar.MINUTE),localDateTime.getMinute());
        assertEquals(calendar.get(Calendar.SECOND),localDateTime.getSecond());
        assertEquals(calendar.get(Calendar.MILLISECOND),localDateTime.getNano()/1000000);

    }

    @Test
    void toDate() {
        LocalDateTime localDateTime =  LocalDateTime.now();
        Date date= MftDates.toDate(localDateTime);
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(calendar.get(Calendar.YEAR),localDateTime.getYear());
        assertEquals(calendar.get(Calendar.MONTH),localDateTime.getMonth().getValue()-1);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH),localDateTime.getDayOfMonth());
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY),localDateTime.getHour());
        assertEquals(calendar.get(Calendar.MINUTE),localDateTime.getMinute());
        assertEquals(calendar.get(Calendar.SECOND),localDateTime.getSecond());
        assertEquals(calendar.get(Calendar.MILLISECOND),localDateTime.getNano()/1000000);

    }
}