package com.augustbonds.mapreduce;

import com.augustbonds.foundation.Array;

public class WordCounter extends MapReduce<String, Integer, Integer> {

    public WordCounter(Array<String> texts) {
        super(texts);
    }

    @Override
    void map(String input) {
        String[] split = input.split("\\s+");
        for (String word : split){
            emit(word, 1);
        }
    }

    @Override
    void reduce(String word, Array<Integer> intermediates) {
       emitResult(word, intermediates.size());
    }
}
