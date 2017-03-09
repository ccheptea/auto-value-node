package com.ccheptea.auto.value.node.models;

/**
 * Created by constantin.cheptea
 * on 03/03/2017.
 */
public class Specifics {
    String name = "specifics1";
    Keywords keywords = null;

    public $.Node_Specifics node() {
        return new $.Node_Specifics(this);
    }

    @Override
    public String toString() {
        return "Node_Specifics{" +
                "name='" + name + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
