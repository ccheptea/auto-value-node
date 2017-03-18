package com.ccheptea.auto.value.node.runtime;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.lang.reflect.Proxy;

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

    public AlternativeIfNull<T> ifPresent(Action1<T> action1) {
        if (value != null) {
            action1.execute(value);
        }

        return new AlternativeIfNull<>(value);
    }

    public AlternativeIfNotNull<T> ifNotPresent(Action action) {
        if (value == null) {
            action.execute();
        }

        return new AlternativeIfNotNull<>(value);
    }

    public void anyValue(Action1<T> action1) {
        action1.execute(value);
    }

    public Observable<T> react() {
        return Observable.just(value);
    }

    public <Z> Z map(Mapper<T, Z> mapper) {
        return mapper.map(value);
    }
    
}
