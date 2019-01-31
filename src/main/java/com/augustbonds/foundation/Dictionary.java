package com.augustbonds.foundation;

import java.util.*;
import java.util.function.BiConsumer;

public class Dictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {

    private final Map<K, V> contents;

    public Dictionary() {
        contents = new HashMap<>();
    }

    public V get(K key) {
        return contents.get(key);
    }

    public void put(K key, V value) {
        contents.put(key, value);
    }

    public void forEach(BiConsumer<K, V> consumer) {
        contents.forEach(consumer);
    }

    public int size() {
        return contents.size();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator();
    }

    public interface Entry<K, V> {
        K getKey();

        V getValue();
    }

    private class DictionaryIterator implements Iterator<Entry<K, V>> {
        private Iterator<Map.Entry<K, V>> iterator = contents.entrySet().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            try {
                Map.Entry<K, V> next = iterator.next();
                return new DictionaryEntry(next);
            } catch (NoSuchElementException e) {
                throw new OutOfBoundsException("Called next() on empty iterator.");
            }
        }
    }

    private class DictionaryEntry implements Entry<K, V> {

        private final Map.Entry<K, V> entry;

        private DictionaryEntry(Map.Entry<K, V> entry) {
            this.entry = entry;
        }

        @Override
        public K getKey() {
            return entry.getKey();
        }

        @Override
        public V getValue() {
            return entry.getValue();
        }
    }


}
