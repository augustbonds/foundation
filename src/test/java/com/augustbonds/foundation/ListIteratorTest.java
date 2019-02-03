package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

public class ListIteratorTest {

    @Test
    public void testJavaListIterator(){
        ArrayList<Integer> array = new ArrayList<>();
        array.add(0);
        array.add(1);
        array.add(2);

        ListIterator<Integer> listIterator = array.listIterator(1);
        assertEquals(1, (int)listIterator.next());
        assertEquals(2, (int)listIterator.next());
        assertFalse(listIterator.hasNext());
        assertEquals(2, (int)listIterator.previous());
        assertEquals(1, (int)listIterator.previous());
        assertEquals(0, (int)listIterator.previous());
        assertFalse(listIterator.hasPrevious());


        ListIterator<Integer> listIterator2 = array.listIterator(1);
        assertEquals(0, (int) listIterator2.previous());

        assertEquals( 0, listIterator2.nextIndex());
        listIterator2.remove();
        assertThrows(IllegalStateException.class, listIterator2::remove);
        assertEquals(1, (int) listIterator2.next());
        assertEquals(2, (int) listIterator2.next());
        listIterator2.remove();
        listIterator2.previous();
        listIterator2.remove();

        assertFalse(listIterator2.hasPrevious() || listIterator2.hasNext());

        assertThrows(IllegalStateException.class, () -> listIterator2.set(-1));
    }

}
