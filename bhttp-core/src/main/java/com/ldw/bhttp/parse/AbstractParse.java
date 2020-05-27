package com.ldw.bhttp.parse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import okhttp3.Response;


/**
 * @date 2020/5/23 11:15
 */

/**
 * 此类唯一实现解析器的类，自定义解析可以继承此类
 */

public abstract class AbstractParse<T>  {

    public abstract <D> D onParse(String response, Class<D> tClass,Class<?> responseClass);
    public abstract <T,D> T onParse(String response, Class<D> tClass);

    public   Class<T> getTClass(){
        return null;
    }

    public <T> T convert(String json,Class<T> t) throws JsonSyntaxException {
        return new Gson().fromJson(json,t);
    }
}
