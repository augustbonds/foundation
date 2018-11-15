package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTest {

    @Test
    public void testPut(){
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

        boolean[] testValues = new boolean[] {true, true, false, false, false, true};

        boolean containsTrue = false;
        for (boolean bool : testValues){
            if (bool){
                containsTrue = true;
            }
        }
        assertTrue(containsTrue);

        Dictionary<Integer, String> dict = new Dictionary<>();

        for (int i = 0; i < testValues.length ; i++){
            if (testValues[i]){
                dict.put(i, "" + i);
            }
        }

        assertTrue(dict.size() > 0);

        dict.forEach((i, s) -> testValues[i] = !testValues[i]); //flip to try to catch duplicates

        for (boolean bool : testValues) {
            assertFalse(bool);
        }
    }
}
