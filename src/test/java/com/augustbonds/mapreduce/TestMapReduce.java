package com.augustbonds.mapreduce;

import com.augustbonds.foundation.Array;
import com.augustbonds.foundation.Dictionary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestMapReduce {

    @Test
    public void testWordCount(){
        String text = "Here's a story about a little boy named Sue. He had a six foot dad and and a two-toed shoe.";

        Array<String> texts = new Array<>();
        texts.append(text);

        WordCounter wc = new WordCounter(texts);
        Dictionary<String, Integer> result = wc.run();

        assertEquals(4, (int)result.get("a"));
        assertEquals(1, (int)result.get("Here's"));
        assertEquals(1, (int)result.get("two-toed"));
        assertNull(result.get("definitely not there"));
    }
}
