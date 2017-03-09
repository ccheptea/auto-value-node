package com.ccheptea.auto.value.node;

import com.google.auto.value.AutoValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by constantin.cheptea
 * on 06/03/2017.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Nodeable {
}
