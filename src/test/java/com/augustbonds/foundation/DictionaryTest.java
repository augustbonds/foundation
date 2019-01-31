package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTest {

    @Test
    public void testPut() {
        Dictionary<Integer, String> dict = new Dictionary<>();

        dict.put(0, "hej");
        dict.put(1, "hej");

        assertEquals(2, dict.size());

        dict = new Dictionary<>();

        dict.put(0, "hej");
        dict.put(0, "då");

        assertEquals(1, dict.size());
    }


    @Test
    public void testGet() {
        Dictionary<Integer, String> dict = new Dictionary<>();

        dict.put(0, "hej");
        dict.put(4, "da");

        assertEquals("hej", dict.get(0));
        assertEquals("da", dict.get(4));
        assertNull(dict.get(3));

        dict.put(0, "dada");
        assertEquals("dada", dict.get(0));

    }


    @Test
    public void testForEach() {

        boolean[] testValues = new boolean[]{true, true, false, false, false, true};

        boolean containsTrue = false;
        for (boolean bool : testValues) {
            if (bool) {
                containsTrue = true;
            }
        }
        assertTrue(containsTrue);

        Dictionary<Integer, String> dict = new Dictionary<>();

        for (int i = 0; i < testValues.length; i++) {
            if (testValues[i]) {
                dict.put(i, "" + i);
            }
        }

        assertTrue(dict.size() > 0);

        dict.forEach((i, s) -> testValues[i] = !testValues[i]); //flip to try to catch duplicates

        for (boolean bool : testValues) {
            assertFalse(bool);
        }
    }

    @Test
    public void testIterator() {
        Dictionary<String, Integer> dict = new Dictionary<>();
        dict.put("a", 1);
        dict.put("b", 2);
        dict.put("c", -1);

        assertEquals(3, dict.size());

        boolean foundA = false;
        boolean foundB = false;
        boolean foundC = false;
        int entryCounter = 0;

        for (Dictionary.Entry<String, Integer> entry : dict) {
            if ("a".equals(entry.getKey()) && 1 == entry.getValue()) {
                foundA = true;
            }
            if ("b".equals(entry.getKey()) && 2 == entry.getValue()) {
                foundB = true;
            }
            if ("c".equals(entry.getKey()) && -1 == entry.getValue()) {
                foundC = true;
            }

            entryCounter++;
        }

        assertEquals(3, entryCounter);

        assertTrue(foundA && foundB && foundC);
    }

    @Test
    public void testIteratorException() {

        assertThrows(OutOfBoundsException.class, () -> new Dictionary<String, Integer>().iterator().next());


        Dictionary<String, Integer> dict = new Dictionary<>();
        for (int i = 0; i < 1000; i++) {
            dict.put("" + i, i);
        }

        Iterator<Dictionary.Entry<String, Integer>> iterator = dict.iterator();
        for (int i = 0; i < 1000; i++) {
            iterator.next();
        }

        assertThrows(OutOfBoundsException.class, iterator::next);

    }
}
