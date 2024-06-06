package com.msb.tank.interator.v3;

class LinkedList implements Collection_{
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

    private class Node {
        Object obj;
        Node next;

        public Node(Object obj) {
            this.obj = obj;
        }
    }
}



