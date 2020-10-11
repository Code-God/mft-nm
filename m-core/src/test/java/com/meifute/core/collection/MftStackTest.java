package com.meifute.core.collection;

import com.meifute.core.lang.MftException;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/12
 * @Description
 */
class MftStackTest {

    @Test
    void push() {
        MftStack<Integer> stack= new MftStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3,stack.pop());
        assertEquals(2,stack.pop());
        assertEquals(1,stack.pop());
    }

    @Test
    void clear() {
        MftStack<Integer> stack =new MftStack<>();
        assertTrue(stack.isEmpty());
        stack.push(1);
        stack.push(2);
        assertFalse(stack.isEmpty());
        assertEquals(2,stack.size());
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0,stack.size());
    }

    @Test
    void pop_exception() {
        assertThrows(StackOverflowError.class,()->new MftStack<Integer>().pop());
    }
}