package com.ldw.test;

/**
 * @date 2020/5/27 14:44
 * @user 威威君
 */
public class Response<D> {
    public int code;
    public String msg;
    public D data;

    @Override
    public String toString() {
        return "MyResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
