package com.augustbonds.foundation;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Array class is the default ordered collection.
 * It is backed by an array, and grows to fit it's contents dynamically.
 *
 * @param <E> The type of objects stored in this Array
 */
public class Array<E> implements Iterable<E> {
    Object[] contents;
    private int size;
    private int preallocatedSize;

    /**
     * Default constructor.
     */
    public Array() {
        size = 0;
        preallocatedSize = 10;
        contents = new Object[preallocatedSize];
    }

    private Array(Object[] newContents, int size) {
        contents = newContents;
        preallocatedSize = newContents.length;
        this.size = size;
    }

    /**
     * Returns the size of the Array.
     *
     * @return the size of the Array.
     */
    public int size() {
        return size;
    }

    /**
     * Appends an element to the end of the Array.
     *
     * @param element The element to append.
     */
    public void append(E element) {
        if (size > preallocatedSize * 0.8) {
            grow();
        }

        contents[size] = element;
        size++;
    }

    /**
     * Sets the element at a specific index.
     * The index must be within [0,size()), or else a {@code OutOfBoundsException} will be thrown.
     *
     * @param index   The target index.
     * @param element The element to be set.
     */
    public void set(int index, E element) {
        checkIndex(index);
        contents[index] = element;
    }

    /**
     * Removes an element at a specific index and shrinks the Array.
     * The index must be within [0,size()), or else a {@code OutOfBoundsException} will be thrown.
     *
     * @param index The index of the element to remove.
     */
    public void remove(int index) {
        checkIndex(index);
        if (size == 1) {
            contents[0] = null;
        }

        Object[] newContents = Arrays.copyOf(contents, size - 1);
        System.arraycopy(contents, index + 1, newContents, index, size - 1 - index);
        contents = newContents;
        size--;
        preallocatedSize = size;
    }

    /**
     * Apply a function to each element of the Array, and return an Array of the returned elements.
     *
     * @param toMap The function to apply to objects stored in the Array.
     * @param <O>   The type of the objects stored in the returned Array.
     * @return An Array of elements as the result of applying toMap to every element in the Array.
     */
    public <O> Array<O> map(Function<E, O> toMap) {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = toMap.apply(get(i));
        }

        return new Array<>(result, size);
    }

    /**
     * Get the element at a specific index.
     *
     * @param index The index of the requested element.
     * @return The element, if it exists. Null otherwise.
     */
    public E get(int index) {
        checkIndex(index);
        return (E) contents[index];
    }

    /**
     * Filter this Array by a given Predicate.
     *
     * @param predicate The predicate on which to filter this Array.
     * @return An Array consisting of the objects in this Array satisfying the predicate.
     */
    public Array<E> filter(Predicate<E> predicate) {
        Array<E> filtered = new Array<>();
        for (int i = 0; i < size; i++) {
            if (predicate.test((E) contents[i])) {
                filtered.append((E) contents[i]);
            }
        }
        return filtered;
    }

    /**
     * Return an iterator for this Array.
     *
     * @return An iterator for this Array.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    private void grow() {
        int newSize = (size * 2 + 10);
        contents = Arrays.copyOf(contents, newSize);
        preallocatedSize = newSize;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new OutOfBoundsException();
        }
    }

    private class ArrayIterator implements Iterator<E> {

        final int startIndex;
        int index;

        private ArrayIterator() {
            startIndex = 0;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < startIndex + size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Called next() on empty iterator.");
            }
            return get(index++);
        }

        @Override
        public void remove() {
            Array.this.remove(index-1);
        }
    }
}
