package com.ccheptea.auto.value.node;

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

    public Alternative<T> ifNotPresent(Action action) {
        if (value == null) {
            action.execute();
        }

        return new Alternative<>(value);
    }

    public interface Action {
        void execute();
    }

    interface Action1<T> {
        void execute(T value);
    }

    final class AlternativeIfNull<T> {
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

    final class Alternative<T> {
        private final T value;

        public Alternative(T value) {
            this.value = value;
        }

        public void otherwise(Action1<T> action) {
            if (value != null) {
                action.execute(value);
            }
        }
    }
}
