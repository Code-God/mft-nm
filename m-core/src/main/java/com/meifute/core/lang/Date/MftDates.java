package com.meifute.core.lang.Date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MftDates {


    /**
     * 是否是节日（包括， 官方， 公历 和 农历所有节日）
     * @param localDate
     * @return
     */
    public final static boolean isFestive(LocalDate localDate){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 获取一年中所有的节日， 返回的key是节日明， value 是该节日包含的所有日期
     * @return
     */
    public final static Map<String, List<LocalDate>> getAllFestive(int year){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 是否是官方假日（包括， 官方， 公历 和 农历所有假日）
     * @param localDate
     * @return
     */
    public final static boolean isOfficialHoliday(LocalDate localDate){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 获取一年中所有的官方放假日， 返回的key是假日名称， value 是该假日包含的所有日期
     * @param year
     * @return
     */
    public final static Map<String, List<LocalDate>> getAllOfficialHolidays(int year){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public final static LocalDateTime toLocalDatetime(Date date){
        Instant  instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    public final static Date toDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt =localDateTime.atZone(zoneId);
        Date  date= Date.from(zdt.toInstant());
        return date;
    }

    public LunarLocalDate toLunarLocalDate(LocalDate localDate){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public LocalDate toLocalDate(LunarLocalDate lunarLocalDate){
        throw  new UnsupportedOperationException("Not implemented");
    }

    String commonDate="yyyy-MM-dd HH:mm:ss";
    String commonDateToMilli="yyyy-MM-dd HH:mm:ss.SSS";


    /**
     * 在保证正确性的情况下， 基于多种格式尽可能的将日期字符串转化为日期格式。
     * @param dateString
     * @return
     */
    public LocalDateTime tryParse(String dateString){
        Pattern commonPattern=Pattern.compile("\\d{1,4}?\\d{1,2}?[0123][0-9]");
        throw  new UnsupportedOperationException("Not implemented");
    }
}
