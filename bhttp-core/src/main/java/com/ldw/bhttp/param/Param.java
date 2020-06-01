package com.ldw.bhttp.param;


import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

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
    private static String domain = null;

    private Request.Builder builder = new Request.Builder();
    private HashMap<String, ParameValue> hashMap = new HashMap<>();
    //private HashMap<String, Boolean> hashMapEncode = new HashMap<>();
    private Method method;
    private ParamType paramType = ParamType.Form;
    private String url = null;

    public static String getDomain() {
        return domain;
    }

    public static void setDefaultDomain(String s) {
        domain = s;
    }

    public void setDomain(String domain) {
        Param.domain = domain;
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
            for (String key : hashMap.keySet()) {
                Object v = Objects.requireNonNull(hashMap.get(key)).v;
                if (Objects.requireNonNull(hashMap.get(key)).isEncode)
                    httpUrlbuilder.addEncodedQueryParameter(key, v.toString());
                else
                    httpUrlbuilder.addQueryParameter(key, v.toString());
            }
            builder.url(httpUrlbuilder.build()).get();
        } else if (method == Method.POST) {
            this.builder.post(resolveRequestBody(paramType));
         /*   if (paramType.isForm()) {
                this.builder.post(buildFormBody().build());
            } else if (paramType.isJson()) {
                this.builder.post(buildJsonRequestBody());
            }*/
        } else if (method == Method.PUT) {
            builder.put(resolveRequestBody(paramType));
          /*  if (paramType.isForm()) {
                builder.put(buildFormBody().build());
            } else if (paramType.isJson()) {
                this.builder.put(buildJsonRequestBody());
            }*/
            //todo
        } else if (method == Method.DELETE) {
            builder.delete(resolveRequestBody(paramType));
           /* if (paramType.isForm()) {
                builder.delete(buildFormBody().build());
            }*/
            //todo
        } else if (method == Method.PATCH) {
            builder.patch(resolveRequestBody(paramType));
            //todo
        } else if (method == Method.HEAD) {
            builder.head();
            //todo
        }
        //解析请求参数


        return builder.build();
    }

    @NotNull
    private RequestBody resolveRequestBody(ParamType paramType) {
        if (paramType.isForm()) {
            return buildFormBody().build();
        } else if (paramType.isQuery()) {
            return buildFormBody().build();
        } else if (paramType.isJson()) {
            return buildJsonRequestBody();
        } else if (paramType.isPath()) {
            return buildPathRequestBody();
        }
        return null;
    }

    @NotNull
    private RequestBody buildJsonRequestBody() {
        HashMap<String, Object> jsonMaps = new HashMap<>();
        for (String key : hashMap.keySet()) {
            Object v = Objects.requireNonNull(hashMap.get(key)).v;
            jsonMaps.put(key, v);
        }
        return RequestBody.create(new Gson().toJson(jsonMaps), MediaType.parse("application/json;charset=utf-8"));
    }

    @NotNull
    private RequestBody buildPathRequestBody() {
        HashMap<String, Object> maps = new HashMap<>();

        for (String key : hashMap.keySet()) {
            Object v = Objects.requireNonNull(hashMap.get(key)).v;
            maps.put(key, v);
        }
        String finalUrl = getFinalUrl();
        finalUrl = finalUrl.replace(" ", "");
        for (String key : maps.keySet()) {
            finalUrl = finalUrl.replace("{" + key + "}", maps.get(key).toString());
        }
        builder.url(finalUrl);
        return RequestBody.create(new byte[]{}, null);
    }

    private FormBody.Builder buildFormBody() {
        FormBody.Builder formBodybuilder = new FormBody.Builder();
        for (String key : hashMap.keySet()) {
            Object v = Objects.requireNonNull(hashMap.get(key)).v;
            if (Objects.requireNonNull(hashMap.get(key)).isEncode)
                formBodybuilder.addEncoded(key, v.toString());
            else
                formBodybuilder.add(key, v.toString());
        }
        return formBodybuilder;
    }

    @NotNull
    public Param add(String k, Object v) {
        add(k, v, false);
        return this;
    }

    public void add(String k, Object v, boolean isEncode, ParamType paramType) {
        hashMap.put(k, new ParameValue(k, v, isEncode));
        this.paramType = paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public void add(String k, Object v, boolean isEncode) {
        hashMap.put(k, new ParameValue(k, v, isEncode));
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

    class ParameValue {
        public String key;
        public Object v;
        public boolean isEncode = false;

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
}
