package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 07/03/2017.
 */
public abstract class Node<T> {
    protected T value;

    public Node(T value) {
        this.value = value;
    }

    /**
     * Returns the value contained by this node
     *
     * @return value contained by this node
     */
    public T value() {
        return value;
    }

    /**
     * Allows setting an alternative value if the value is null
     *
     * @param alternativeValue the value to return if the contained value is null
     * @return value if it is not null, alternativeValue otherwise
     */
    public T value(T alternativeValue) {
        return value != null ? value : alternativeValue;
    }

    /**
     * Used to determine if the value exists.
     *
     * @return true if the value exists, false if the value is null
     */
    public boolean exists() {
        return value != null;
    }

    /**
     * Used to determine if the value is null.
     *
     * @return false if the value exists, true if the value is null
     */
    public boolean isAbsent() {
        return value != null;
    }

    /**
     * Used to check a condition applied to the value
     *
     * @param predicate the predicate defining the condition
     * @return true if the result is true; false otherwise
     */
    public boolean passesTest(Predicate<T> predicate) {
        return predicate.test(value);
    }

    /**
     * Executes an action on the value if it is not null
     *
     * @param action1 the action to execute with the value
     * @return an AlternativeIfNull object to handle the contrary case
     */
    public AlternativeIfNull<T> ifPresent(Action1<T> action1) {
        if (value != null) {
            action1.execute(value);
        }

        return new AlternativeIfNull<>(value);
    }

    /**
     * Executes an action when the value is null
     *
     * @param action the action to execute
     * @return an AlternativeIfNotNull object to handle the contrary case
     */
    public AlternativeIfNotNull<T> ifNotPresent(Action action) {
        if (value == null) {
            action.execute();
        }

        return new AlternativeIfNotNull<>(value);
    }

    /**
     * Executes an action with the value, regardless if it is null or not
     *
     * @param action1 the action to execute
     */
    public void anyValue(Action1<T> action1) {
        action1.execute(value);
    }

    /**
     * Useful method to convert the value to another type or structure
     *
     * @param mapper the mapper responsible to make the conversion
     * @param <Z>    the target type
     * @return the result after converting the value
     */
    public <Z> Z map(Mapper<T, ? extends Z> mapper) {
        return mapper.map(value);
    }

}
