package com.ldw.bhttp.parse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ldw.bhttp.callback.Consumer;
import com.ldw.bhttp.utils.LogUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @date 2020/5/26 19:18
 * @user 威威君
 */
public class Parse<T> {


    public T convert(Type type,String response) throws JsonSyntaxException {
        LogUtils.logd("type.toString()");
        LogUtils.logd(type.toString());
        LogUtils.logd(response);
        T t = (T) new Gson().fromJson(response, type);
        return t;
    }
    public T convert(Method method,String response) throws JsonSyntaxException {
        Type returnType = method.getGenericReturnType();
        ParameterizedType parameterizedType = (ParameterizedType) returnType;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type rawType = parameterizedType.getRawType();
        for (int i = 0; i < actualTypeArguments.length; i++) {
            Type paramType = actualTypeArguments[i];
            System.out.println("paramType");
            System.out.println(paramType);
            T t = (T) new Gson().fromJson(response, paramType);
            return t;
        }
        return null;
    }


}
