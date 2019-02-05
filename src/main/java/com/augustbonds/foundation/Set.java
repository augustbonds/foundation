package com.augustbonds.foundation;

import java.util.HashSet;
import java.util.Iterator;

/**
 * The Set class is used to hold a number of unique items of type E.
 * The type E must implement hashCode() and a corresponding equals().
 *
 * @param <E> The type of elements this Set holds
 */
public class Set<E> implements Iterable<E> {

    private final HashSet<E> contents;

    /**
     * The default constructor.
     */
    public Set() {
        contents = new HashSet<>();
    }

    /**
     * Add an element to the Set.
     * @param element The element to add.
     */
    public void add(E element) {
        contents.add(element);
    }

    /**
     * Remove an element from the Set.
     * @param element The element to remove.
     */
    public void remove(E element) {
        contents.remove(element);
    }

    /**
     * Check whether the specified element exists in the Set.
     * @param element
     * @return
     */
    public boolean contains(E element){
        return contents.contains(element);
    }

    /**
     * The number of elements currently in the Set.
     * @return The number of elements.
     */
    public int size() {
        return contents.size();
    }

    /**
     * An iterator for the elements in the Set.
     */
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
