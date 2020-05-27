package com.ldw.bhttp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @date 2020/5/27 10:49
 * @user 威威君
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    /** The query parameter name. */
    String value();

    /**
     * Specifies whether the parameter {@linkplain #value() name} and value are already URL encoded.
     */
    boolean encoded() default false;
}
