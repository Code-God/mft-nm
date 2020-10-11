package com.meifute.core.lang;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/4
 * @Description
 */
class MftClassTest {

    @Test
    void createInstance() throws ClassNotFoundException, MftException {
       Date date= MftClass.createInstance(Date.class.getName());
       assertNotNull(date);
    }

    @Test
    void createInstance_exception(){
        assertThrows(MftException.class,()->MftClass.createInstance(TestForClass.class.getName()));
        assertThrows(MftException.class,()->MftClass.createInstance(TestForClass.class.getName(),true,1));
        assertThrows(NoSuchMethodException.class ,()->MftClass.createInstance(MftException.class.getName(),true,123));
        assertThrows(NoSuchMethodException.class ,()->MftClass.createInstance(TestForClass.class.getName(),false,3,5L,6));
        assertThrows(NoSuchMethodException.class ,()->MftClass.createInstance(TestForClass.class.getName()
                ,false,3,5L, LocalDate.now()));
    }

    @Test
    void createInstance_parameters() throws NoSuchMethodException, ClassNotFoundException, MftException {
        MftException exception=MftClass.createInstance(MftException.class.getName(),true,"Exception");
        assertEquals("Exception",exception.getMessage());
        TestForClass tfe= MftClass.createInstance(TestForClass.class.getName(),false,3,5L,new Date());
        assertEquals(5L,tfe.getB());
        assertEquals(3,tfe.getA());
    }

    @Test
    void testField(){
        TestForField testForField = new TestForField();
        var op=MftClass.getFieldByFieldName(TestForField.class,"p",null);
        op.ifPresent(o-> {
            try {
                MftClass.setFieldValue(testForField,o,3);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        assertEquals(3,testForField.getP());
    }

    public static class TestForField extends TestForFieldBase{

    }

    public static class TestForFieldBase{
        private transient int p=1;

        public int getP() {
            return p;
        }
    }



    public static class TestForClass {

        int a;
        long b;
        Date c;

        private TestForClass(){

        }
        public TestForClass(Integer p) throws MftException {
            throw new MftException(("Exception in constructor"));
        }

        public TestForClass(Number a, long b,Date c){
            this.a =a.intValue();
            this.b=b;
            this.c = c;
        }

        public int getA() {
            return a;
        }

        public long getB() {
            return b;
        }

        public Date getC() {
            return c;
        }
    }



}