package com.augustbonds.foundation;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * The Dictionary class acts as a lookup table for values of type V with keys of type K.
 * The type K must implement both hashCode() and a corresponding equals() method.
 * @param <K> The type of key in the Dictionary
 * @param <V> The type of the values stored in the Dictionary
 */
public class Dictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {

    private final Map<K, V> contents;

    /**
     * Default constructor.
     */
    public Dictionary() {
        contents = new HashMap<>();
    }

    /**
     * Get the value corresponding the given key.
     * @param key The key
     * @return A value, or null.
     */
    public V get(K key) {
        return contents.get(key);
    }

    /**
     * Store a value and a corresponding key.
     * @param key A key
     * @param value A value
     */
    public void put(K key, V value) {
        contents.put(key, value);
    }

    /**
     * Perform an action on each key-value pair stored in the dictionary.
     * @param consumer The action to perform on each key-value pair in the dictionary.
     */
    public void forEach(BiConsumer<K, V> consumer) {
        contents.forEach(consumer);
    }

    /**
     * The number key-value pairs stored in the dictionary
     * @return The number of key-value pairs in the dictionary
     */
    public int size() {
        return contents.size();
    }

    /**
     * Iterate over the key-value pairs stored in the dictionary.
     * @return An Iterator over this dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator();
    }

    /**
     * The interface of key-value pairs accessed through the Dictionary Iterator.
     * @param <K> The type of key in the pair
     * @param <V> The type of value in the pair
     */
    public interface Entry<K, V> {
        K getKey();
        V getValue();
    }

    private class DictionaryIterator implements Iterator<Entry<K, V>> {
        private final Iterator<Map.Entry<K, V>> iterator = contents.entrySet().iterator();

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
