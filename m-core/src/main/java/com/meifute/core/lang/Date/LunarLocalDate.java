package com.meifute.core.lang.Date;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 描述中国农历历法
 */
public class LunarLocalDate {

    public LunarLocalDate(LocalDate localDate){

    }

    public LunarLocalDate of(int year, int month, int day){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public LunarLocalDate of(int lunarYear,int lunarMonth, int lunarDay,boolean leapMonth){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public int getYear(){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public int getMonth(){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public int getDay(){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public boolean isLunarFestive(){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public boolean getLunarFestive(){
        throw  new UnsupportedOperationException("Not implemented");
    }

    public static Map<String, List<LocalDate>> getAllLunarFestives(int year){
        throw  new UnsupportedOperationException("Not implemented");
    }

}
