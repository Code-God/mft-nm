package com.meifute.core.lang.Date;

import com.meifute.core.collection.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wzeng
 * @date 2020/8/8
 * @Description
 */
public class MftDatetimeFormatter {
    /**
     * Such as yyyy-MM-dd HH:mm:ss
     */


    public final static Pair<Pattern, DateTimeFormatter> COMMON_DATETIME_PATTERN=Pair.of(
            Pattern.compile("(\\d{1,4})-(\\d{1,2})-(\\d{1,2}).+?(\\d{1,2}):(\\d{1,2}):(\\d{1,2})")
            ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public final static LocalDateTime tryParse(String datetimeString){
        Matcher matcher = COMMON_DATETIME_PATTERN.getFirst().matcher(datetimeString);
        if(matcher.find()){
            int year=Integer.parseUnsignedInt(matcher.group(1));
            int month=Integer.parseUnsignedInt(matcher.group(2));
            int dayInMonth=Integer.parseUnsignedInt(matcher.group(3));
            int hourInDay=Integer.parseUnsignedInt(matcher.group(4));
            int minutesInHour = Integer.parseUnsignedInt(matcher.group(5));
            int secondsInMinutes=Integer.parseUnsignedInt(matcher.group(6));
            return LocalDateTime.of(year,month,dayInMonth,hourInDay,minutesInHour,secondsInMinutes);
        }

        return null;
    }

    public final static String formatCommonDatetime(LocalDateTime localDateTime){
        return COMMON_DATETIME_PATTERN.getSecond().format(localDateTime);
    }
}
