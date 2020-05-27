package com.ldw.test;

import com.google.gson.reflect.TypeToken;
import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.httpsend.HttpSend;

import org.jetbrains.annotations.NotNull;

/**
 * @date 2020/5/27 20:22
 * @user 威威君
 */
public class AHttp extends BHttp<Response> {

    @NotNull
    public <D> HttpSend<Response<D>> asResponse(Class<D> tClass) {
        return new HttpSend<Response<D>>(getParam(),new TypeToken<Response<D>>(){}.getType());
    }

}
