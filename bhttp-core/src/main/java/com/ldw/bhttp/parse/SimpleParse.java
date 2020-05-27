package com.ldw.bhttp.parse;


import com.google.gson.Gson;

/**
 * @date 2020/5/23 11:35
 */
public class SimpleParse extends AbstractParse {


    @Override
    public Object onParse(String response, Class tClass, Class responseClass) {
        return null;
    }

    @Override
    public Object onParse(String response, Class tClass) {
        Object o = new Gson().fromJson(response, tClass);
        return o;
    }
}
