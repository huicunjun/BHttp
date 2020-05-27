package com.ldw.bhttp.callback;

/**
 * @date 2020/5/22 19:58
 */
public interface CallBack<T> {
    //public void onSubscribe();

    public void onNext(T t) ;

    public void onError() ;

    //public void onComplete();
}
