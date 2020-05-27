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
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Header {
    String value();
}
