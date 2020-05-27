package com.ldw.bhttp.utils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @date 2020/5/27 7:31
 * @user 威威君
 */
public class LogUtils {
    private static final String TAG = "BHttp Debug";
    private static Boolean isDebug = false;

    public static void logd(String s) {
        if (!isDebug) return;
        System.out.println(TAG + "\n" + s);
    }

    public static void logd(Response response) {
        if (!isDebug) return;
        Request request = response.request();
        StringBuilder builder = new StringBuilder()
                .append("<------ ")
                .append(request.body() + " ")
                .append(" request end Method=")
                .append(request.method())
                .append(" Code=").append(response.code())
                .append(" ------>");
        System.out.println(builder.toString());
    }

    public static void setDebug(boolean b) {
        isDebug = b;
    }
}
