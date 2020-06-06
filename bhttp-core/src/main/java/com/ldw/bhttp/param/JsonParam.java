package com.ldw.bhttp.param;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/6/6 10:27
 * @user 威威君
 */
public class JsonParam extends IRequest {
    public JsonParam() {
    }

    public JsonParam(String url, Method method) {
        super(url, method, ParamType.Json);
    }

    @Override
    public Request getRequest() {
        String finalUrl = getFinalUrl();
        Request.Builder requestBuilder = getRequestBuilder();
        if (getMethod().isGet()) {
            requestBuilder.get();
        } else if (getMethod().isPost()) {
            requestBuilder.post(buildJsonRequestBody());
        } else if (getMethod().isDelete()) {
            requestBuilder.delete(buildJsonRequestBody());
        }else if (getMethod().isPatch()) {
            requestBuilder.patch(buildJsonRequestBody());
        }else if (getMethod().isPut()) {
            requestBuilder.put(buildJsonRequestBody());
        }else if (getMethod().isHead()) {
            requestBuilder.head();
        }

        requestBuilder.url(finalUrl);
        return getRequestBuilder().build();
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
}
