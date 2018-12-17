package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {

    @Test
    public void testAppendSize(){
        Array<Object> array = new Array<>();
        assertEquals(0, array.size());
        for (int i = 0 ; i < 10 ; i++){
            array.append(new Object());
        }
        assertEquals(10, array.size());
    }

    @Test
    public void testAddAndRemove(){
        Array<Integer> array = new Array<>();
        for (int i = 0 ; i < 10 ; i++){
            array.append(i);
        }

        for (int i = 0 ; i < 3 ; i++){
            array.remove(5);
        }

        assertEquals(0, (int)array.get(0));
        assertEquals(7, array.size());
        assertEquals(8, (int)array.get(5));
    }

    @Test
    public void testSet(){
        Array<Integer> array = new Array<>();

        Util.expect(() -> {
            array.set(0, 10);
        }, new OutOfBoundsException());

        array.append(1);
        assertEquals(1, (int)array.get(0));

        array.set(0,2);
        assertEquals(2, (int)array.get(0));
    }

    @Test
    public void testMap(){
        Array<Integer> array = new Array<>();
        for (int i = 0 ; i < 10 ; i++){
            array.append(i);
        }

        array = array.map(integer -> integer*2);

        assertEquals(10, array.size());
        assertEquals(2, (int)array.get(1));
    }

    @Test
    public void testGet(){
        Array<Integer> array = new Array<>();
        for (int i = 0 ; i < 10 ; i++){
            array.append(i);
        }

        assertEquals(3, (int) array.get(3));
    }

    @Test
    public void testOutOfBounds() {
        final Array<Integer> array = new Array<>();
        Util.expect(() -> array.get(0), new OutOfBoundsException());
    }

    @Test
    public void testRemoveOutOfBounds() {
        final Array<Integer> array = new Array<>();
        Util.expect(() -> array.get(0), new OutOfBoundsException());
    }

    @Test
    public void testForEach() {
        Array<Integer> array = new Array<>();
        for ( int i = 0 ; i < 10 ; i++){
            array.append(i);
        }

        final int[] count = new int[1];

        array.forEach(integer -> count[0]++);
        assertEquals(10, count[0]);
    }
}
