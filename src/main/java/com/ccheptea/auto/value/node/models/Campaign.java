package com.ccheptea.auto.value.node.models;

import com.ccheptea.auto.value.node.Nodeable;

/**
 * Created by constantin.cheptea
 * on 03/03/2017.
 */

@Nodeable
public class Campaign {
    public Specifics specifics = new Specifics();

//    public abstract Nodeable_Campaign node();

    public $.Nodeable_Campaign node() {
        return new $.Nodeable_Campaign(this);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "specifics=" + specifics +
                '}';
    }
}
