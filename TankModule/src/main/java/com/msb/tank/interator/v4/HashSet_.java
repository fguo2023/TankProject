package com.msb.tank.interator.v4;

public class HashSet_ implements Collection_{

    Object[] objects = new Object[10];
    private int index = 0;
    @Override
    public void add(Object o) {
        if (index == objects.length) {
            Object[] newObjects = new Object[objects.length * 2];
            System.arraycopy(objects, 0, newObjects, 0, objects.length);
            objects = newObjects;
        }

        for (int i = 0; i <= index; i++) {
                if(o.hashCode() == objects[i].hashCode()) return;
        }
        objects[index] = o;
        index++;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public Iterator_ iterator() {
        return new HashSetIterator();
    }

    private class HashSetIterator implements Iterator_ {
        int currentIndex = 0;
        @Override
        public boolean hasNext() {
            return currentIndex >= index;
        }

        @Override
        public Object next() {
            Object o = objects[currentIndex];
            currentIndex++;
            return o;
        }
    }
}
