package com.meifute.core.lang;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/5
 * @Description
 */
class MftTypeTest {

    @Test
    void boxableClassSwitch() throws MftException {
        assertEquals(boolean.class,MftType.boxableClassSwitch(Boolean.class));
        assertEquals(byte.class,MftType.boxableClassSwitch(Byte.class));
        assertEquals(char.class,MftType.boxableClassSwitch(Character.class));
        assertEquals(double.class,MftType.boxableClassSwitch(Double.class));
        assertEquals(float.class,MftType.boxableClassSwitch(Float.class));
        assertEquals(int.class,MftType.boxableClassSwitch(Integer.class));
        assertEquals(long.class,MftType.boxableClassSwitch(Long.class));
        assertEquals(short.class,MftType.boxableClassSwitch(Short.class));
        assertEquals(void.class,MftType.boxableClassSwitch(Void.class));


        assertEquals(Boolean.class,MftType.boxableClassSwitch(boolean.class));
        assertEquals(Byte.class,MftType.boxableClassSwitch(byte.class));
        assertEquals(Character.class,MftType.boxableClassSwitch(char.class));
        assertEquals(Double.class,MftType.boxableClassSwitch(double.class));
        assertEquals(Float.class,MftType.boxableClassSwitch(float.class));
        assertEquals(Integer.class,MftType.boxableClassSwitch(int.class));
        assertEquals(Long.class,MftType.boxableClassSwitch(long.class));
        assertEquals(Short.class,MftType.boxableClassSwitch(short.class));
        assertEquals(Void.class,MftType.boxableClassSwitch(void.class));


    }

    @Test
    void boxableClassSwitch_notBoxAble() throws MftException {
        assertEquals(Date.class,MftType.boxableClassSwitch(Date.class));
    }

    @Test
    void converte_Exception() {
       assertThrows(MftException.class,()->MftType.boxedTypeToPrimitive(int.class));
       assertThrows(MftException.class,()->MftType.primitiveToBoxedType(Date.class));
    }
}