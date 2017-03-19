package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 13/03/2017.
 * A simple action to execute with one parameter
 */
public interface Action1<T> {
    void execute(T value);
}
