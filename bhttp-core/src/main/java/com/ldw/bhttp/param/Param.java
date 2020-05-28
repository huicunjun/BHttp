package com.ldw.bhttp.param;


import com.ldw.bhttp.entry.MyResponse;
import com.ldw.bhttp.httpsend.HttpSend;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/26 19:18
 * @user 威威君
 */
public class Param {
    private Request.Builder builder = new Request.Builder();
    private HashMap<String, Object> hashMap = new HashMap<>();
    private HashMap<String, Boolean> hashMapEncode = new HashMap<>();
    private Method method;
    private ParamType paramType;
    private static String domain = null;
    //String domain = null;
    private String url = null;

    // private HttpUrl.Builder httpUrlbuilder;

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


    public void reload() {
        hashMap.clear();
    }

    public Request getRequest() {
        String finalURL = getFinalUrl();//最终请求地址
        builder.url(finalURL);

        //解析请求方法
        if (method == Method.GET) {
            //get请求可以使用HttpUrl特殊处理
            HttpUrl.Builder httpUrlbuilder = HttpUrl.parse(finalURL).newBuilder();
            for (String s : hashMap.keySet()) {
                Object o = hashMap.get(s);
                boolean t = false;
                t = hashMapEncode.get(s);
                if (t)
                    httpUrlbuilder.addEncodedQueryParameter(s, o.toString());
                else
                    httpUrlbuilder.addQueryParameter(s, o.toString());
            }
            builder.url(httpUrlbuilder.build()).get();
        } else if (method == Method.POST) {
            if (paramType.isForm()) {
                FormBody.Builder formBodybuilder = new FormBody.Builder();
                for (String s : hashMap.keySet()) {
                    Object o = hashMap.get(s);
                    boolean t = false;
                    t = hashMapEncode.get(s);
                    if (t)
                        formBodybuilder.addEncoded(s, o.toString());
                    else
                        formBodybuilder.add(s, o.toString());
                }
                this.builder.post(formBodybuilder.build());
            }
        } else if (method == Method.PUT) {
            builder.put(RequestBody.create("new",MediaType.parse("application/x-www-form-urlencoded")));
            //todo
        }
        //解析请求参数


        return builder.build();
    }

    @NotNull
    public Param add(String k, Object v) {
        add(k, v, false);
        return this;
    }

    public void add(String k, Object v, boolean isEncode, ParamType paramType) {
        hashMap.put(k, v);
        hashMapEncode.put(k, isEncode);
        this.paramType = paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public void add(String k, Object v, boolean isEncode) {
        hashMap.put(k, v);
        hashMapEncode.put(k, isEncode);
    }

/*    public <T> HttpSend<MyResponse<T>> asResponse(Class<T> tClass) {
        HttpSend<MyResponse<T>> httpSend = new HttpSend<MyResponse<T>>();
        return httpSend;
    }

    public <T> HttpSend<T> asObject(Class<T> tClass) {
        return new HttpSend<>();
    }

    public HttpSend<String> asString() {
        return new HttpSend<>();
    }*/




  /*  public void addParam(String k, Object v) {
        hashMap.put(k, v);
    }*/
}
