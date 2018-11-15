package com.augustbonds.mapreduce;

import com.augustbonds.foundation.Array;
import com.augustbonds.foundation.Dictionary;

public abstract class MapReduce<Key,Intermediate,Result> {

    private final Array<String> inputs;
    private Dictionary<Key, Array<Intermediate>> intermediates = new Dictionary<>();
    private Dictionary<Key, Result> results = new Dictionary<>();

    public MapReduce(Array<String> inputs){
        this.inputs = inputs;
    }

    /**
     * Generates intermediate tuples and calls emit(Key key, Intermediate intermediate);
     * @param input
     */
    abstract void map(String input);


    /**
     * Generates results by calling emitResult(Key key, Intermediate intermediate);
     * @param key
     * @param intermediates
     */
    abstract void reduce(Key key, Array<Intermediate> intermediates);


    protected final void emit(Key key, Intermediate intermediate){
       if (intermediates.get(key) == null) {
           intermediates.put(key, new Array<>());
       }

       intermediates.get(key).append(intermediate);
    }

    protected final void emitResult(Key key, Result result){
        results.put(key, result);
    }

    public final Dictionary<Key, Result> run(){
        //Mapping stage
        inputs.forEach(this::map);

        //Reducing stage
        intermediates.forEach(this::reduce);

        return results;
    }
}
