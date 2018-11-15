package com.augustbonds.foundation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Dictionary<K, V> {

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
}
