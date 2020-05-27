package com.ldw.bhttp.callback;

import androidx.annotation.NonNull;

/**
 * @date 2020/5/25 21:31
 */
public interface Observer<T> {

    void onSubscribe();

    void onNext(@NonNull T t);

    void onError(@NonNull Throwable e);

    void onComplete();
}
