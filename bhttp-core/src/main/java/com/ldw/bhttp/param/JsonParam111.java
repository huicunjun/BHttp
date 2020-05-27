package com.ldw.bhttp.param;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/22 17:41
 */
public class JsonParam111 extends Param111<JsonParam111> {
    private Map<String, Object> mParam = new HashMap<>(); //请求参数
    private RequestBody requestBody;
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public JsonParam111(String url, Method method) {
        super(url, method);
    }


    @Override
    public RequestBody getRequestBody() {
        if (mParam == null) {
            return RequestBody.create(new byte[0], null);
        }
        JSONObject jsonObject = new JSONObject(mParam);
        String s = jsonObject.toString();
        requestBody = RequestBody.create(s, MediaType.parse("application/json"));
        return requestBody;
    }

    @Override
    public void add(String key, Object value) {
        mParam.put(key, value);
    }

    @Override
    public Request getRequest() {
        Request.Builder builder = new Request.Builder();
        if (getMethod() == Method.GET) {
            builder.get();
        }
        if (getMethod() == Method.POST) {
            builder.post(getRequestBody());
        }
        Headers.Builder headersBuilder = getHeadersBuilder();
        builder.headers(headersBuilder.build());
        builder.url(getUrl());
        return builder.build();
    }
}
