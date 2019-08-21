package com.paypay.immutable;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ImmutableQueueTest {

    private final List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);

    @org.junit.Test
    public void enQueueReturnNewInstance() {

        Queue<Integer> queue = new ImmutableQueue<>();

        for (Integer num : data){
            Queue<Integer> newQueue = queue.enQueue(num);
            assertTrue(newQueue != queue);
        }

        assertTrue(queue.isEmpty());

    }


    @org.junit.Test
    public void testEnqueueWithHeadElementInNewInstance() {

        Queue<Integer> queue = new ImmutableQueue<>();

        for (Integer num : data){
            queue = queue.enQueue(num);
        }

        assertTrue(queue.head() == data.get(0));

    }

    @Test
    public void testEmptyQueue() {
        Queue<Integer> queue = new ImmutableQueue<>();

        assertTrue(queue.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueEmptyQueue() {
        Queue<Integer> queue = new ImmutableQueue<>();
        queue.deQueue();
    }

    @Test
    public void testDeQueueAll() {
        Queue<Integer> queue = new ImmutableQueue<>();

        for (Integer num : data){
            queue = queue.enQueue(num);
        }

        for(Integer num : data){
            assertEquals(queue.head(), num);
            queue = queue.deQueue();
        }

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testImmutableQueueEnqueueOperation() {
        Queue<Integer> queue = new ImmutableQueue<>();
        Integer element = -1;

        queue = queue.enQueue(element);

        for (Integer num : data){
            queue.enQueue(num);
        }

        assertEquals(queue.head(), element);

        for(int i = 0; i < data.size(); i++){
            queue.deQueue();
        }

        queue.deQueue();

        assertEquals(queue.head(), element);
    }

    @Test
    public void testInterleavedEnqueueDequeueOperations() {
        Queue<Integer> queue = new ImmutableQueue<>();

        queue = queue.enQueue(1);
        queue = queue.enQueue(2);
        queue = queue.enQueue(3);
        queue = queue.deQueue();     // pop 1
        queue = queue.enQueue(4);
        queue = queue.enQueue(5);

        assertEquals(queue.head(), Integer.valueOf(2));

        queue = queue.deQueue();    // pop 2
        assertEquals(queue.head(), Integer.valueOf(3));

        queue = queue.deQueue();    // pop 3
        assertEquals(queue.head(), Integer.valueOf(4));

        queue = queue.deQueue();    // pop 4
        assertEquals(queue.head(), Integer.valueOf(5));

        queue = queue.deQueue();
        assertTrue(queue.isEmpty());
    }

}