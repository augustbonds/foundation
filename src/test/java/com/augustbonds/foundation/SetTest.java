package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTest {

    @Test
    public void testSetAdd(){
        Set<Integer> set = new Set<>();
        assertEquals(0, set.size());

        for (int i = 0 ; i < 10 ; i++){
            set.add(i);
        }

        assertEquals(10, set.size());
    }

    @Test
    public void testForEach(){
        Set<Integer> set = new Set<>();
        for (int i = 0 ; i < 5 ; i++){
            set.add(i);
        }

        final int[] count = new int[1];
        set.forEach((i) -> count[0]++);

        assertEquals(5, count[0]);
    }

    @Test
    public void testRemove(){
        Set<Integer> set = new Set<>();
        for (int i = 0 ; i < 10 ; i++){
            set.add(i);
        }

        assertEquals(10, set.size());

        set.remove(0);
        assertEquals(9, set.size());
    }

    @Test
    public void testNoDuplicates() {
        Set<Integer> set = new Set<>();
        for (int i = 0 ; i < 10 ; i++){
            set.add(0);
        }

        assertEquals(1, set.size());
    }
}
