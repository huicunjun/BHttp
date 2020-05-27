package com.ldw.bhttp.parse;

/**
 * @date 2020/5/23 11:20
 */
public class MyResponse<D> {
    public int code;
    public String msg;
    public  D data;

    @Override
    public String toString() {
        return "MyResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
