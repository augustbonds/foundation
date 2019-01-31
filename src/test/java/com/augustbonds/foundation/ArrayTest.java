package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayTest {

    @Test
    public void testAppendSize() {
        Array<Object> array = new Array<>();
        assertEquals(0, array.size());
        for (int i = 0; i < 10; i++) {
            array.append(new Object());
        }
        assertEquals(10, array.size());
    }

    @Test
    public void testAddAndRemove() {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        for (int i = 0; i < 3; i++) {
            array.remove(5);
        }

        assertEquals(0, (int) array.get(0));
        assertEquals(7, array.size());
        assertEquals(8, (int) array.get(5));
    }

    @Test
    public void testSet() {
        Array<Integer> array = new Array<>();

        Util.expect(() -> array.set(0, 10), new OutOfBoundsException());

        array.append(1);
        assertEquals(1, (int) array.get(0));

        array.set(0, 2);
        assertEquals(2, (int) array.get(0));
    }

    @Test
    public void testMap() {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        array = array.map(integer -> integer * 2);

        assertEquals(10, array.size());
        assertEquals(2, (int) array.get(1));
    }

    @Test
    public void testGet() {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 10; i++) {
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
        Util.expect(() -> array.remove(0), new OutOfBoundsException());
    }

    @Test
    public void testForEach() {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        final int[] count = new int[1];

        array.forEach(integer -> count[0]++);
        assertEquals(10, count[0]);
    }

    @Test
    public void testIterator() {

        final Random random = new Random(123);
        final int maxIndex = 1000;

        Array<Boolean> flags = new Array<>();
        for (int i = 0; i < maxIndex; i++) {
            flags.append(false);
        }

        Array<Integer> indexes = new Array<>();
        for (int i = 0; i < maxIndex; i++) {
            indexes.append(random.nextInt(maxIndex));
            flags.set(indexes.get(i), true);
        }

        //Verify that at least one flag is set to true
        boolean foundOne = false;
        for (int i = 0; i < maxIndex; i++) {
            if (flags.get(i)) {
                foundOne = true;
                break;
            }
        }

        assertTrue(foundOne);

        int elementCounter = 0;
        for (Integer index : indexes) {
            flags.set(index, false);
            elementCounter++;
        }

        assertEquals(maxIndex, elementCounter);

        //Verify that all true flags have been reset to false
        boolean anyTrue = false;
        for (int i = 0; i < maxIndex; i++) {
            if (flags.get(i)) {
                anyTrue = true;
                break;
            }
        }

        assertFalse(anyTrue);
    }

    @Test
    public void testIteratorException() {
        Iterator<Integer> empty = new Array<Integer>().iterator();
        assertThrows(OutOfBoundsException.class, empty::next);

        Array<Integer> arrayOf2 = new Array<>();
        arrayOf2.append(1);
        arrayOf2.append(2);
        Iterator<Integer> iteratorOf2 = arrayOf2.iterator();
        iteratorOf2.next();
        iteratorOf2.next();
        assertThrows(OutOfBoundsException.class, iteratorOf2::next);

        Array<Integer> arrayOf1000 = new Array<>();
        for (int i = 0; i < 1000; i++) {
            arrayOf1000.append(i);
        }
        Iterator<Integer> iteratorOf1000 = arrayOf1000.iterator();
        for (int i = 0; i < 1000; i++) {
            iteratorOf1000.next();
        }
        assertThrows(OutOfBoundsException.class, iteratorOf1000::next);

    }
}
