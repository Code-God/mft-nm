package com.meifute.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MftStringsTest {

    @ParameterizedTest
    @ValueSource(strings={""})
    void isEmpty_True(String string) {
        assertTrue(MftStrings.isEmpty(string));
        assertTrue(MftStrings.isEmpty(null));
    }


    @ParameterizedTest
    @ValueSource(strings={"a","apple"," ","\t\n"})
    void isEmpty_False(String string) {
        assertFalse(MftStrings.isEmpty(string));
    }

    @ParameterizedTest
    @ValueSource(strings={""})
    void isNotEmpty_True(String string) {
        Assertions.assertFalse(MftStrings.isNotEmpty(string));
        Assertions.assertFalse(MftStrings.isNotEmpty(null));
    }

    @ParameterizedTest
    @ValueSource(strings={"a","apple"," ","\t\n"})
    void isNotEmpty_False(String string) {
        assertTrue(MftStrings.isNotEmpty(string));
    }

    @ParameterizedTest
    @ValueSource(strings={"","\t\n "," "})
    void isBlank_True(String string) {
        assertTrue(MftStrings.isBlank(string));
        assertTrue(MftStrings.isBlank(null));
    }

    @ParameterizedTest
    @ValueSource(strings={"a","apple",})
    void isNotBlank_False(String string) {
        assertTrue(MftStrings.isNotBlank(string));
    }

    @Test
    void abbreviate(){
        Assertions.assertEquals("",MftStrings.abbreviate("","",0));
        Assertions.assertEquals("",MftStrings.abbreviate("","",1));
        Assertions.assertEquals("",MftStrings.abbreviate("a","",0));
        Assertions.assertEquals("ab",MftStrings.abbreviate("abc","",2));
        Assertions.assertEquals("abc",MftStrings.abbreviate("abc",".",3));
        Assertions.assertEquals(".",MftStrings.abbreviate("abcdefgh",null,1));
        Assertions.assertEquals("ab...",MftStrings.abbreviate("abcdefgh",null,5));
        Assertions.assertEquals("这是...",MftStrings.abbreviate("这是一段比较长的中文",null,5));
        Assertions.assertEquals("**较**",MftStrings.abbreviate("这是一段比较长的中文","**",5,5));
        Assertions.assertEquals("**的中文",MftStrings.abbreviate("这是一段比较长的中文","**",9,5));
        Assertions.assertEquals("***",MftStrings.abbreviate("这是一段比较长的中文","***",9,3));
        Assertions.assertEquals("****",MftStrings.abbreviate("这是一段比较长的中文","***",9,4));
        Assertions.assertEquals("******",MftStrings.abbreviate("这是一段比较长的中文","***",9,6));
        Assertions.assertEquals("***长的中文",MftStrings.abbreviate("这是一段比较长的中文","***",9,7));
        Assertions.assertEquals("***长的中文",MftStrings.abbreviate("这是一段比较长的中文","***",11,7));
    }


    @Test
    public void toReadableString(){
        List<Object> objs=new ArrayList<>();
        objs.add(1);
        objs.add(LocalDateTime.of(2001,01,01,12,01,01));
        int[] t=new int[]{1,3,9};
        objs.add(t);
        Map<String,Integer> map =new TreeMap<>();
        map.put("m1",12);
        map.put("m2",34);
        objs.add(map);
        String s=MftStrings.toReadableString(objs);
        Assertions.assertEquals("[1, 2001-01-01 12:01:01, [1, 3, 9, ]\n, {m1 : 12, m2 : 34, }\n, ]\n",s);

    }

    @Test
    public void toUtf8(){
        String s="abc问题";
        assertEquals(null, MftStrings.toUtf8String(null));
        assertEquals(s,MftStrings.toUtf8String(MftStrings.toUtf8Bytes(s)));
    }

    @Test
    void findNearestChar(){
        String s = "abcdefghi,,,{}";
        assertTrue(MftStrings.findNearest(s,0,null).isEmpty());
        assertTrue(MftStrings.findNearest(s,0,'z').isEmpty());
        assertEquals(0,MftStrings.findNearest(s,0,'a','b').get().getSecond());
        assertEquals(1,MftStrings.findNearest(s,0,'z','b').get().getSecond());


    }



}