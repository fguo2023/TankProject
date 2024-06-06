package com.msb.tank.interator.v2;

public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s" + i));
        }
        System.out.println(list.size());
    }
}

class Node {
    Object obj;
    Node next;

    public Node(Object obj) {
        this.obj = obj;
    }
}

class LinkedList {
    int size = 0;
    Node head = null;
    Node tail = null;

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

    public int size() {
        return size;
    }
}