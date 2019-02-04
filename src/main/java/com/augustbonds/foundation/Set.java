package com.augustbonds.foundation;

import java.util.HashSet;
import java.util.Iterator;

public class Set<E> implements Iterable<E> {

    private final HashSet<E> contents;

    public Set() {
        contents = new HashSet<>();
    }

    public void add(E element) {
        contents.add(element);
    }

    public void remove(E element) {
        contents.remove(element);
    }

    public int size() {
        return contents.size();
    }

    @Override
    public Iterator<E> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<E> {

        final Iterator<E> iterator = contents.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new OutOfBoundsException("Called next() on empty iterator.");
            }
            return iterator.next();
        }
    }
}
