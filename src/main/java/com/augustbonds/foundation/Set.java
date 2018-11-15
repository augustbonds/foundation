package com.augustbonds.foundation;

import java.util.HashSet;
import java.util.function.Consumer;

public class Set<E> {

    private final HashSet<Object> contents;

    public Set() {
        contents = new HashSet<>();
    }

    public void add(E element) {
        contents.add(element);
    }

    public void remove(E element) {
        contents.remove(element);
    }

    public void forEach(Consumer<E> consumer) {
        for (Object o : contents) {
            consumer.accept((E) o);
        }
    }

    public int size() {
        return contents.size();
    }
}
