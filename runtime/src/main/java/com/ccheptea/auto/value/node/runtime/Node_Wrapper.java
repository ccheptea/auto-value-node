package com.ccheptea.auto.value.node.runtime;

/**
 * Created by constantin.cheptea
 * on 07/03/2017.
 * <p>
 * This class is destined to wrap the fields that are of primitive types
 * of classes that are not handled by auto-value-node
 */
public class Node_Wrapper<T> extends Node<T> {
    public Node_Wrapper(T value) {
        super(value);
    }
}
