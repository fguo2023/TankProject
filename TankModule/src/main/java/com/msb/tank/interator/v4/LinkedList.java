package com.msb.tank.interator.v4;

import java.util.NoSuchElementException;

class LinkedList implements Collection_ {
    int size = 0;
    Node head = null;
    Node tail = null;

    @Override
    public void add(Object o) {
        Node n = new Node(o);
        n.next = null;
        if (head == null) {
            head = n;
            tail = n;
        }

        tail.next = n;
        tail = n;
        size++;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator_ iterator() {
       return new LinkedListIterator();
    }

    private class Node {
        Object obj;
        Node next;

        public Node(Object obj) {
            this.obj = obj;
        }

        @Override
        public String toString() {
            return obj.toString();
        }
    }

    private class LinkedListIterator implements Iterator_ {
        Node current;

        public LinkedListIterator() {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null && current.next != null;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Object value = current.obj;
            current = current.next;
            return value;
        }
    }
}



