package com.example.task01;

import java.util.function.BiConsumer;

public class Pair <T, S>{
    private T fValue;
    private S sValue;

    private Pair(T t, S s) {
        fValue = t;
        sValue = s;
    }

    public static <T, S> Pair<T, S> of(T t, S s){
        return new Pair<>(t, s);
    }

    public T getFirst() { return fValue; }
    public S getSecond() { return sValue; }

    public void ifPresent(BiConsumer<? super T, ? super S> consumer) {
        if(fValue != null & sValue != null)
            consumer.accept(fValue, sValue);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Pair){
            Pair<T, S> t = (Pair<T, S>) o;
            return this.fValue.equals(t.fValue) & this.sValue.equals(t.sValue);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return fValue.hashCode() + sValue.hashCode();
    }


    // TODO напишите реализацию
}
