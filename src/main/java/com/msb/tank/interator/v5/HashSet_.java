package com.msb.tank.interator.v5;

public class HashSet_<E> implements Collection_<E> {

    E[] objects = (E[]) new Object[10];
    private int index = 0;

    @Override
    public void add(E o) {
        if (index == objects.length) {
            E[] newObjects = (E[]) new Object[objects.length * 2];
            System.arraycopy(objects, 0, newObjects, 0, objects.length);
            objects = newObjects;
        }

        for (int i = 0; i <= index; i++) {
            if (o.hashCode() == objects[i].hashCode()) return;
        }
        objects[index] = o;
        index++;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public Iterator_<E> iterator() {
        return new HashSetIterator<E>();
    }

    private class HashSetIterator<E> implements Iterator_<E> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex >= index;
        }

        @Override
        public E next() {
            E o = (E) objects[currentIndex];
            currentIndex++;
            return o;
        }
    }
}
