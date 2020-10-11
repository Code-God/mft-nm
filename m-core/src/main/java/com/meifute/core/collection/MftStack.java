package com.meifute.core.collection;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author wzeng
 * @date 2020/8/12
 * @Description
 */
public class MftStack<E>  {
   Deque<E> deQueue = new ArrayDeque<>();

   public void push(E element){
      deQueue.push(element);
   }

   public E pop(){
      if(deQueue.isEmpty()){
         throw new StackOverflowError("Can not pop element from an empty stack");
      } else {
         return deQueue.pollFirst();
      }
   }

   public E peek(){
      if(deQueue.isEmpty()){
         throw new StackOverflowError("Can not peek element from an empty stack");
      } else {
         return deQueue.peekFirst();
      }
   }

   public  boolean isEmpty(){
      return deQueue.isEmpty();
   }

   public void clear(){
      this.deQueue.clear();
   }

   public int size(){
      return this.deQueue.size();
   }
}
