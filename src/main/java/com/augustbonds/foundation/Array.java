package com.augustbonds.foundation;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Array<E> implements Iterable<E> {
    Object[] contents;
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
        preallocatedSize = size;
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

    public Array<E> filter(Predicate<E> predicate){
        Array<E> filtered = new Array<>();
        for (int i = 0 ; i < size ; i++){
            if (predicate.test((E)contents[i])){
                filtered.append((E)contents[i]);
            }
        }
        return filtered;
    }

    public void sort(Comparator<E> comparator){
        new Wrapper(contents).sort(comparator);
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

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
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

    class Wrapper implements List<E> {

        private Object[] contents;
        Wrapper(Object[] contents){
            this.contents = contents;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            if (o == null){
                for (int i = 0 ; i < size ; i++){
                    if (contents[i] == null){
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (o.equals(contents[i])){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return iterator();
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(contents, size);
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return (T[]) Arrays.copyOf(contents, size, a.getClass());
        }

        @Override
        public boolean add(E e) {
            append(e);
            contents = Array.this.contents;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (o == null) {
                for (int i = 0 ; i < size ; i++){
                    if (contents[i] == null){
                        Array.this.remove(i);
                        contents = Array.this.contents;
                        return true;
                    }
                }
            } else {
                for (int i = 0 ; i < size ; i++){
                    if(o.equals(contents[i])){
                        Array.this.remove(i);
                        contents = Array.this.contents;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public E get(int index) {
            return null;
        }

        @Override
        public E set(int index, E element) {
            return null;
        }

        @Override
        public void add(int index, E element) {

        }

        @Override
        public E remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return null;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return null;
        }
    }
}
