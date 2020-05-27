package com.ldw.bhttp.param;

import androidx.annotation.NonNull;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/22 17:27
 */
public abstract class Param111<P extends Param111> {
    private String url;
    private String doman;
    private Method method;
    private Headers.Builder headersBuilder;
    private Request.Builder requestBuilder = new Request.Builder();

    public Param111(@NonNull String url, Method method) {
        this.url = url;
        this.method = method;
    }


    public P setUrl(String url) {
        this.url = url;
        return (P) this;
    }

    public String getUrl() {
        return url;
    }

    public HttpUrl getHttpUrl() {
        return HttpUrl.get(url);
    }

    //不带参数的url
    public final String getSimpleUrl() {
        return url;
    }

    public final Headers.Builder getHeadersBuilder() {
        if (headersBuilder == null)
            headersBuilder = new Headers.Builder();
        return headersBuilder;
    }

    //##################################请求头相关方法#####################################################
    public final P addHeader(String key, String value) {
        getHeadersBuilder().add(key, value);
        return (P) this;
    }

    public final P addHeader(String line) {
        getHeadersBuilder().add(line);
        return (P) this;
    }

    public P addAllHeader(Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                addHeader(entry.getKey(), entry.getValue());
            }
        }
        return (P) this;
    }

    public P addAllHeader(Headers headers) {
        getHeadersBuilder().addAll(headers);
        return (P) this;
    }

    public final P setHeader(String key, String value) {
        getHeadersBuilder().set(key, value);
        return (P) this;
    }

    public final String getHeader(String key) {
        return getHeadersBuilder().get(key);
    }

    public final P removeAllHeader(String key) {
        getHeadersBuilder().removeAll(key);
        return (P) this;
    }

    //############################请求体##############################################
    public abstract RequestBody getRequestBody();

    public Method getMethod() {
        return method;
    }


    //######################################################################################

/*    public static JsonParam postJson(String url) {
        return new JsonParam(url, Method.POST);
    }

    public static JsonParam postJson(String url) {
        return new JsonParam(url, Method.POST);
    }*/

    public abstract void add(String key, Object value);

    public abstract Request getRequest();

}
