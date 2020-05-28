package com.ldw.bhttp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @date 2020/5/28 14:34
 * @user 威威君
 */
public class ParameterizedTypeImpl implements ParameterizedType {

    private Type rawType;//真实类型
    private Type actualType;//泛型类型

    public ParameterizedTypeImpl(Type rawType,Type actualType) {
        this.rawType = rawType;
        this.actualType = actualType;
    }

    public Type[] getActualTypeArguments() {
        return new Type[]{actualType};
    }

    public Type getRawType() {
        return rawType;
    }

    public Type getOwnerType() {
        return null;
    }
}
