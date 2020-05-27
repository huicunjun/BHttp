package com.ldw.bhttp.httpsend;

import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.param.Param;

import org.jetbrains.annotations.NotNull;

/**
 * @date 2020/5/27 19:48
 * @user 威威君
 */
public class HttpSend<T> {
    Param param = new Param();
    Class<T> tClass;
    public HttpSend(Param param, Class<T> tClass) {
        this.param = param;
        this.tClass = tClass;
    }

    public void subscribe(final Observer<T> observer) {


    }
}
