package com.ldw.bhttp.param;


import com.ldw.bhttp.httpsend.HttpSend;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/26 19:18
 * @user 威威君
 */
public class Param {
    Request.Builder builder = new Request.Builder();
    private HashMap<String, Object> hashMap = new HashMap<>();
    Method method;

    private static String domain = null;
    //String domain = null;
    String url = null;

    private HttpUrl.Builder httpUrlbuilder;

    public static String getDomain() {
        return domain;
    }

    public static void setDefaultDomain(String s) {
        domain = s;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    private String getFinalUrl() {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            if (domain == null) {
                domain = getDomain();
            }
            return domain + url;
        }
    }

    public void addQuery(String k, String v, boolean isEncode) {
        if (httpUrlbuilder == null) {
            httpUrlbuilder = HttpUrl.parse(getFinalUrl()).newBuilder();
        }
        if (isEncode) {
            httpUrlbuilder.addEncodedQueryParameter(k, v);
        } else {
            httpUrlbuilder.addQueryParameter(k, v);
        }
    }

    public void reload() {
        httpUrlbuilder = HttpUrl.parse(getFinalUrl()).newBuilder();
    }

    public Request getRequest() {
        String finalURL = getFinalUrl();

        builder.url(finalURL);
//        builder.url("http://gdptdad.com:8080/api/user/login/?identifier=1&voucher=6");
        if (method == Method.GET) {
            //httpUrlbuilder.build().newBuilder(finalURL);
            builder.url(httpUrlbuilder.build()).get();
        } else if (method == Method.POST) {
            builder.post(RequestBody.create("", null));
        }
        return builder.build();
    }

    @NotNull
    public Param add(String k, Object v) {
        addParam(k, v);
        return this;
    }

    public <T> HttpSend<T> asObject(Class<T> tClass) {
        return new HttpSend<>(this, tClass);
    }

    public HttpSend<String> asString() {
        return new HttpSend<>(this, String.class);
    }

    public void addParam(String k, Object v) {
        hashMap.put(k, v);
    }
}
