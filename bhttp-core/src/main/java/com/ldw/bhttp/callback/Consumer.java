package com.ldw.bhttp.callback;

import androidx.annotation.NonNull;

/**
 * @date 2020/5/22 20:37
 */
public interface Consumer< T> {
    /**
     * Consume the given value.
     * @param t the value
     * @throws Throwable if the implementation wishes to throw any type of exception
     */

    void accept(T t);


}
