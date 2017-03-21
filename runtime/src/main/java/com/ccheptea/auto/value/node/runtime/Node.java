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
     * Allows returning an alternative value if the value is null
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
    public boolean isPresent() {
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
     * @return a Node containing the value if the match is successful; an empty Node otherwise
     */
    public Node<T> match(Predicate<T> predicate) {
        return predicate.test(value) ? new Node_Wrapper<>(value) : Node_Wrapper.<T>empty();
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
    public AlternativeIfNotNull<T> ifAbsent(Action action) {
        if (value == null) {
            action.execute();
        }

        return new AlternativeIfNotNull<>(value);
    }

    /**
     * Executes an action with the value, regardless if it is present or not
     *
     * @param action1 the action to execute
     */
    public void withValue(Action1<T> action1) {
        action1.execute(value);
    }

    /**
     * Returns a Node with value or alternativeValue
     *
     * @param alternativeValue the value for the new node if the contained value is null
     * @return a Node with the contained value if it is not null; a Node with alternativeValue otherwise
     */
    public Node<T> orAlternative(T alternativeValue) {
        return value != null ? new Node_Wrapper<>(value) : new Node_Wrapper<>(alternativeValue);
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
