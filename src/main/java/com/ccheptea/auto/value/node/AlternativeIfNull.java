package com.ccheptea.auto.value.node;

/**
 * Created by constantin.cheptea
 * on 13/03/2017.
 */
public final class AlternativeIfNull<T> {
    private T value;

    public AlternativeIfNull(T value) {
        this.value = value;
    }

    public void otherwise(Action action) {
        if (value == null) {
            action.execute();
        }
    }
}
