package com.ldw.bhttp.parse;

import okhttp3.Response;

/**
 * @date 2020/5/23 11:16
 */
public interface IParse<T> {
    T onParse(Response response);
}
