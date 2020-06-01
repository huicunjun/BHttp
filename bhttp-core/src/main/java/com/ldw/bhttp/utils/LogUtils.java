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
                .append("\n")
                .append( " request Url")
                .append("\n")
                .append(request.url() + " ")
                .append("\n")
                .append( " request body")
                .append("\n")
              //  .append(request.body().contentType().toString() + "  ")
              //  .append("\n")
                .append(" request end Method=")
                .append("\n")
                .append(request.method())
                .append("\n")
                .append(" Code=").append(response.code())
                .append("\n")
                .append(" ------>");
        System.out.println(builder.toString());
    }

    public static void setDebug(boolean b) {
        isDebug = b;
    }
}
