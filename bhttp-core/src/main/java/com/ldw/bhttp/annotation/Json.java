package com.ldw.bhttp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @date 2020/5/26 19:30
 * @user 威威君
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Json {
    /**
     * A relative or absolute path, or full URL of the endpoint. This value is optional if the first
     * <p>
     * this is resolved against a base URL to create the full endpoint URL.
     */
    String value() default "";
    boolean encoded() default false;
}
