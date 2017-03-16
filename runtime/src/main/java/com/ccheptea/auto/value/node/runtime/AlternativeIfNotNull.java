package com.ccheptea.auto.value.node.runtime;

import com.annimon.stream.Stream;

import java.util.Collections;


/**
 * Created by constantin.cheptea
 * on 13/03/2017.
 */
public final class AlternativeIfNotNull<T> {
    private final T value;

    public AlternativeIfNotNull(T value) {
        this.value = value;
    }

    public void otherwise(Action1<T> action) {
        if (value != null) {
            action.execute(value);
        }
    }

    public Stream otherwiseStream() {
        if (value != null) {
            if(value instanceof Iterable)
            return Stream.of(value);
        }

        return Stream.empty();
    }
}
