package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 18/03/2017.
 */
public interface Mapper<T, Z> {
    Z map(T valueToConvert);
}
