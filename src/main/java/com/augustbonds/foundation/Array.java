package com.augustbonds.foundation;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Array class is the default ordered collection.
 * It is backed by an array, and grows to fit it's contents dynamically.
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
     * @return the size of the Array.
     */
    public int size() {
        return size;
    }

    /**
     * Appends an element to the end of the Array.
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
     * @param index The target index.
     * @param element The element to be set.
     */
    public void set(int index, E element) {
        checkIndex(index);
        contents[index] = element;
    }

    /**
     * Removes an element at a specific index and shrinks the Array.
     * The index must be within [0,size()), or else a {@code OutOfBoundsException} will be thrown.
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
     * @param toMap The function to apply to objects stored in the Array.
     * @param <O> The type of the objects stored in the returned Array.
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
     * @param index The index of the requested element.
     * @return The element, if it exists. Null otherwise.
     */
    public E get(int index) {
        checkIndex(index);
        return (E) contents[index];
    }

    /**
     * Filter this Array by a given Predicate.
     * @param predicate The predicate on which to filter this Array.
     * @return An Array consisting of the objects in this Array satisfying the predicate.
     */
    public Array<E> filter(Predicate<E> predicate){
        Array<E> filtered = new Array<>();
        for (int i = 0 ; i < size ; i++){
            if (predicate.test((E)contents[i])){
                filtered.append((E)contents[i]);
            }
        }
        return filtered;
    }

    /**
     * Perform an in-place sorting of the elements in the Array.
     * @param comparator The comparator on which to base the ordering.
     */
    public void sort(Comparator<E> comparator){
        new Wrapper().sort(comparator);
    }

    /**
     * Return an iterator for this Array.
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

    private void insert(E element, int index){
        Object[] newContents = Arrays.copyOf(contents, size + 1);
        newContents[index] = element;
        System.arraycopy(contents, index, newContents, index + 1, size + 1 - index);
        contents = newContents;
        size++;
        preallocatedSize = size;
    }

    private void clear(){
        size = 0;
        contents = new Object[10];
        preallocatedSize = 10;
    }

    private class ArrayIterator implements ListIterator<E> {

        final int startIndex;
        int index;
        int size;

        private ArrayIterator(){
            startIndex = 0;
            index = 0;
            size = size();
        }

        private ArrayIterator(int index){
            startIndex = 0;
            this.index = index;
            size = size();
        }

        private ArrayIterator(int fromIndex, int toIndex){
            startIndex = fromIndex;
            index = startIndex;
            size = toIndex - startIndex;
        }

        @Override
        public boolean hasNext() {
            return index < startIndex + size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new OutOfBoundsException("Called next() on empty iterator.");
            }
            return get(index++);
        }

        @Override
        public boolean hasPrevious() {
            return index > startIndex;
        }

        @Override
        public E previous() {
            if (!hasPrevious()){
                throw new OutOfBoundsException("Called previous() on empty iterator.");
            }
            return get(--index);
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkIndex(index);
            Array.this.remove(index);
            size--;
        }

        @Override
        public void set(E e) {
            checkIndex(index - 1);
            Array.this.set(index - 1, e);
        }

        @Override
        public void add(E e) {
            Array.this.insert(e, startIndex + size);
            size++;
        }

        private void checkIndex(int index) {
            if (index < startIndex || index >= startIndex + size) {
                throw new OutOfBoundsException();
            }
        }
    }

    class Wrapper implements List<E> {

        private Wrapper subListParent;
        private final int startIndex;
        private int size;

        Wrapper() {
            startIndex = 0;
            size = Array.this.size();
        }
        Wrapper(int fromIndex, int toIndex){
            startIndex = fromIndex;
            this.size = toIndex - startIndex;
        }

        private Wrapper(int fromIndex, int toIndex, Wrapper parent){
            this(fromIndex, toIndex);
            subListParent = parent;
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
                for (int i = startIndex ; i < startIndex + size ; i++){
                    if (contents[i] == null){
                        return true;
                    }
                }
            } else {
                for (int i = startIndex; i < startIndex + size; i++) {
                    if (o.equals(contents[i])){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new ArrayIterator(startIndex, startIndex + size);
        }

        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[size];
            System.arraycopy(contents, startIndex, newArray, 0, size);
            return newArray;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return (T[]) Arrays.copyOfRange(contents, startIndex, startIndex + size, a.getClass());
        }

        @Override
        public boolean add(E e) {
            Array.this.insert(e, startIndex + size);
            incrementSize();
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (o == null) {
                for (int i = startIndex ; i < startIndex + size ; i++){
                    if (contents[i] == null){
                        Array.this.remove(i);
                        decrementSize();
                        return true;
                    }
                }
            } else {
                for (int i = startIndex ; i < startIndex + size ; i++){
                    if(o.equals(contents[i])){
                        Array.this.remove(i);
                        decrementSize();
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
            Array.this.clear();
        }

        @Override
        public E get(int index) {
            return Array.this.get(startIndex + index);
        }

        @Override
        public E set(int index, E element) {
            Array.this.set(startIndex + index, element);
            return element;
        }

        @Override
        public void add(int index, E element) {
            Array.this.insert(element, startIndex + index);
            incrementSize();
        }

        @Override
        public E remove(int index) {
            E element = Array.this.get(startIndex + index);
            Array.this.remove(index);
            decrementSize();
            return element;
        }

        @Override
        public int indexOf(Object o) {
            if (o == null){
                for (int i = startIndex; i < startIndex + size ; i++){
                    if (contents[i] == null){
                        return i;
                    }
                }
            } else {
                for (int i = startIndex; i < startIndex + size ; i++){
                    if (o.equals(contents[i])){
                        return i;
                    }
                }
            }

            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            if (o == null){
                for (int i = startIndex + size - 1; i >= startIndex ; i--){
                    if (contents[i] == null){
                        return i;
                    }
                }
            } else {
                for (int i = startIndex + size - 1; i >= startIndex ; i--){
                    if (o.equals(contents[i])){
                        return i;
                    }
                }
            }

            return -1;
        }

        @Override
        public ListIterator<E> listIterator() {
            return new ArrayIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return new ArrayIterator(startIndex + index);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new Wrapper(startIndex + fromIndex, startIndex + toIndex, this);
        }

        private void decrementSize(){
            size--;
            if (subListParent == null) {
                Array.this.size--;
            } else {
                subListParent.decrementSize();
            }
        }

        private void incrementSize(){
            size++;
            if (subListParent == null) {
                Array.this.size++;
            } else {
                subListParent.incrementSize();
            }
        }


    }
}
