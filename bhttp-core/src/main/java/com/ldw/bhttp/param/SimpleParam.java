package com.ldw.bhttp.param;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
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
public class SimpleParam extends IRequest{
    public SimpleParam() {
    }

    public SimpleParam(String url, Method method, ParamType paramType) {
        super(url, method, paramType);
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


    @Override
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


}
