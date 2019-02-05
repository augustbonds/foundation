package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class SetTest {

    @Test
    public void testSetAdd() {
        Set<Integer> set = new Set<>();
        assertEquals(0, set.size());

        for (int i = 0; i < 10; i++) {
            set.add(i);
        }

        assertEquals(10, set.size());
    }

    @Test
    public void testForEach() {
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 5; i++) {
            set.add(i);
        }

        final int[] count = new int[1];
        set.forEach((i) -> count[0]++);

        assertEquals(5, count[0]);
    }

    @Test
    public void testRemove() {
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }

        assertEquals(10, set.size());

        set.remove(0);
        assertEquals(9, set.size());
    }

    @Test
    public void testNoDuplicates() {
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 10; i++) {
            set.add(0);
        }

        assertEquals(1, set.size());
    }

    @Test
    public void testIterator() {
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 1000; i++) {
            set.add(i);
        }

        Array<Boolean> flags = new Array<>();
        for (int i = 0; i < 1000; i++) {
            flags.append(true);
        }

        for (Integer integer : set) {
            flags.set(integer, false);
        }

        boolean allFalse = true;
        for (int i = 0; i < 1000; i++) {
            if (flags.get(i)) {
                allFalse = false;
            }
        }

        assertTrue(allFalse);
    }

    @Test
    public void testIteratorException() {
        assertThrows(OutOfBoundsException.class, () -> new Set<Integer>().iterator().next());

        Set<Integer> set = new Set<>();
        for (int i = 0; i < 1000; i++) {
            set.add(i);
        }

        Iterator<Integer> iterator = set.iterator();
        for (int i = 0; i < 1000; i++) {
            iterator.next();
        }

        assertThrows(OutOfBoundsException.class, iterator::next);
    }

    @Test
    public void testContains(){
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 1000; i++){
            set.add(i);
        }

        boolean containedAll = true;
        for (int i = 999; i >= 0; i--){
            if (!set.contains(i)){
                containedAll = false;
                break;
            }
        }
        assertTrue(containedAll);
        assertFalse(set.contains(-1));
    }
}
