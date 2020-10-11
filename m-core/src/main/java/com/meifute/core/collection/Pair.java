package com.meifute.core.collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wzeng
 * @date 2020/8/4
 * @Description
 */
public  class Pair<F, S> implements Serializable {

    private F first;
    private S second;


    public Pair(){

    }

    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }

    public static <F,S>  Pair<F,S> of(F first, S second){
        Pair<F, S> pair=new Pair<>();
        pair.setFirst(first);
        pair.setSecond(second);
        return pair;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "<" + first + ":" + second + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
