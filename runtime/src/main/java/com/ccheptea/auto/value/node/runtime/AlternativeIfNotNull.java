package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 13/03/2017.
 */
public final class AlternativeIfNotNull<T> {
    private final T value;

    public AlternativeIfNotNull(T value) {
        this.value = value;
    }

    /**
     * Similar to an if-else block, this method executes an action on
     * the "else" branch
     *
     * @param action the action to be executed with the value
     */
    public void otherwise(Action1<T> action) {
        if (value != null) {
            action.execute(value);
        }
    }
}
