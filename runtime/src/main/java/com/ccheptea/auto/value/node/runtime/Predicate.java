package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 18/03/2017.
 */
public interface Predicate<T> {

    /**
     * Test if the value satisfies a condition
     *
     * @param value the value checked in the condition
     * @return true if the condition is satisfied; false otherwise
     */
    boolean test(T value);
}
