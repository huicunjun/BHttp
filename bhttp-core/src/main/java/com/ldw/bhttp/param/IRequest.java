package com.ldw.bhttp.param;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/22 18:51
 */

public abstract class IRequest {
    protected String domain = null;
    protected Request.Builder builder = new Request.Builder();
    protected HashMap<String, ParameValue> hashMap = new HashMap<>();
    protected Method method;
    protected ParamType paramType = ParamType.Form;
    protected String url = null;

    public Request.Builder getRequestBuilder() {
        return builder;
    }

    public Method getMethod() {
        return method;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDefaultDomain(String domain) {
        this.domain = domain;
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

    protected IRequest(String url, Method method, ParamType paramType) {
        this.method = method;
        this.paramType = paramType;
        this.url = url;
    }

    protected IRequest() {
    }

    @NotNull
    public void add(String k, Object v) {
        add(k, v, false);
    }

    public void add(String k, Object v, boolean isEncode) {
        hashMap.put(k, new ParameValue(k, v, isEncode));
    }

    public void add(String k, Object v, boolean isEncode, ParamType paramType) {
        hashMap.put(k, new ParameValue(k, v, isEncode));
        this.paramType = paramType;
    }
    public String getFinalUrl() {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            if (domain == null) {
                domain = getDomain();
            }
            if (!domain.endsWith("/")) {
                domain = domain + "/";
            }
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            return domain + url;
        }
    }
    public abstract Request getRequest();
    public void clear(){
        hashMap.clear();
    }
    /*    *//**
     * @return 不带参数的url
     *//*
    String getSimpleUrl();

    *//**
     * @return HttpUrl
     *//*
    HttpUrl getHttpUrl();

    *//**
     * @return 请求方法，GET、POST等
     *//*
    Method getMethod();


    RequestBody getRequestBody();

    *//**
     * @return 请求头信息
     *//*
    Headers getHeaders();

    *//**
     * @return 根据以上定义的方法构建一个请求
     *//*
    Request buildRequest();*/
}
