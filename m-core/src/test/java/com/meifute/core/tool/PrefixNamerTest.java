package com.meifute.core.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/3
 * @Description
 */
class PrefixNamerTest {

    PrefixNamer namer=new PrefixNamer("test1",DeployEnvironment.TEST,"nm");

    @Test
    void getEntityFullName() {
        assertEquals("test1_TEST_nm_top1",namer.getEntityFullName("top1"));
        assertEquals("test1_TEST_nm_top1",namer.getEntityFullName("test1_TEST_nm_top1"));
        assertNull(namer.getEntityFullName(null));

    }

    @Test
    void getSimpleName() {
        assertEquals("top1",namer.getSimpleName("test1_TEST_nm_top1"));
        assertNull(namer.getSimpleName(null));
    }

    @Test
    void testForProd(){
        PrefixNamer namer=new PrefixNamer("test1",DeployEnvironment.PROD,"nm");
        assertEquals("test1_nm",namer.getPrefix());
    }

}