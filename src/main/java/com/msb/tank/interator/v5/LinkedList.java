package com.msb.tank.interator.v5;

import java.util.NoSuchElementException;

class LinkedList<E> implements Collection_<E> {
    int size = 0;
    Node head = null;
    Node tail = null;

    @Override
    public void add(E o) {
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
    public Iterator_<E> iterator() {
        return new LinkedListIterator<E>();
    }

    private class Node<E> {
        E obj;
        Node<E> next;

        public Node(E obj) {
            this.obj = obj;
        }

        @Override
        public String toString() {
            return obj.toString();
        }
    }

    private class LinkedListIterator<E> implements Iterator_<E> {
        Node<E> current;

        public LinkedListIterator() {
            this.current = (Node<E>) head;
        }

        @Override
        public boolean hasNext() {
            return current != null && current.next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E value = current.obj;
            current = current.next;
            return value;
        }
    }
}



