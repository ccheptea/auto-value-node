package com.ccheptea.auto.value.node.runtime;

import com.annimon.stream.Stream;

import java.util.Collections;

/**
 * Created by constantin.cheptea
 * on 07/03/2017.
 */
public abstract class Node<T> {
    protected T value;

    public Node(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public boolean exists() {
        return value != null;
    }

    public AlternativeIfNull<T> ifPresent(Action1<T> action) {
        if (value != null) {
            action.execute(value);
        }

        return new AlternativeIfNull<>(value);
    }

    public AlternativeIfNotNull<T> ifNotPresent(Action action) {
        if (value == null) {
            action.execute();
        }

        return new AlternativeIfNotNull<T>(value);
    }

    public Stream<T> ifPresentStream() {
        if (value != null) {
            return Stream.of(value);
        }

        return Stream.of(Collections.EMPTY_LIST);
    }
}
