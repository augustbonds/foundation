package com.augustbonds.foundation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class Array<E> implements Iterable<E> {
    private Object[] contents;
    private int size;
    private int preallocatedSize;

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

    public int size() {
        return size;
    }


    public void append(E element) {
        if (size > preallocatedSize * 0.8) {
            grow();
        }

        contents[size] = element;
        size++;
    }

    public void set(int index, E element) {
        checkIndex(index);
        contents[index] = element;
    }

    public void remove(int index) {
        checkIndex(index);
        if (size == 1) {
            contents[0] = null;
        }

        Object[] newContents = Arrays.copyOf(contents, size - 1);
        System.arraycopy(contents, index + 1, newContents, index, size - 1 - index);
        contents = newContents;
        size--;
    }


    public <O> Array<O> map(Function<E, O> toMap) {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = toMap.apply(get(i));
        }

        return new Array<>(result, size);
    }

    public E get(int index) {
        checkIndex(index);
        return (E) contents[index];
    }

    private void grow() {
        int newSize = (int) (size + 10 + size * 0.1);
        contents = Arrays.copyOf(contents, newSize);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new OutOfBoundsException();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; i++) {
            action.accept((E) contents[i]);
        }
    }

    private class ArrayIterator implements Iterator<E> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new OutOfBoundsException("Called next() on empty iterator.");
            }
            return get(index++);
        }
    }
}
