package com.ldw.bhttp.param;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * @date 2020/6/6 10:26
 * @user 威威君
 */
public class ParameValue implements Serializable {
    public String key;
    public Object v;
    public boolean isEncode;

    public ParameValue(String key, Object v, boolean isEncode) {
        this.key = key;
        this.v = v;
        this.isEncode = isEncode;
    }

    @NonNull
    @Override
    public String toString() {
        return v.toString();
    }
}
