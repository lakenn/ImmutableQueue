package com.paypay.immutable;

import java.util.NoSuchElementException;

public class ImmutableQueue<T> implements Queue<T> {

    private static class Stack<T> {
        private final T head;
        private final Stack<T> tail;
        private final int size;

        private Stack() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        private Stack(T head, Stack<T> tail) {
            this.head = head;
            this.tail = tail;
            this.size = tail.size + 1;
        }

        public Stack<T> push(T obj){
            return new Stack<>(obj, this);
        }

        public boolean isEmpty(){
            return size == 0;
        }
    }

    private Stack<T> inStack;
    private Stack<T> outStack;

    public ImmutableQueue() {
        inStack = new Stack<T>() ;
        outStack = new Stack<T>();
    }

    public ImmutableQueue(Stack<T> inStack, Stack<T> outStack) {
        this.inStack = inStack;
        this.outStack = outStack;
    }

    private Stack<T> reverseStack(Stack<T> stack){
        Stack<T> reversed = new Stack<>();
        Stack<T> cursor = stack;

        while(!cursor.isEmpty()){
            reversed = reversed.push(cursor.head);
            cursor = cursor.tail;
        }

        return reversed;
    }

    public Queue<T> enQueue(T obj) {
        if (obj != null)
            return new ImmutableQueue<>(this.inStack.push(obj), this.outStack);

        throw new IllegalArgumentException();
    }

    public Queue<T> deQueue() {

        if (this.isEmpty()){
            throw new NoSuchElementException();
        }

        if (!this.outStack.isEmpty()){
            // return a new Queue with one item "removed" from outStack
            return new ImmutableQueue<>(this.inStack, this.outStack.tail);
        }
        else{
            // return a new Queue with empty inStack and pop the "reversed" inStack as the new outStack
            return new ImmutableQueue<>(new Stack<>(), reverseStack(this.inStack).tail);
        }
    }

    public T head() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        if (this.outStack.isEmpty()) {
            // "Reverse" item in inStack to outStack
            this.outStack = reverseStack(this.inStack);
            this.inStack = new Stack<T>();
        }

        return this.outStack.head;
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
}
