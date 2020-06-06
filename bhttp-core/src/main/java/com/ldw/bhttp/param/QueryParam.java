package com.ldw.bhttp.param;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @date 2020/6/6 10:59
 * @user 威威君
 */
public class QueryParam extends IRequest {
    public QueryParam(String url, Method method) {
        super(url, method, ParamType.Query);
    }

    public QueryParam() {
    }

    @Override
    public Request getRequest() {
        String finalUrl = getFinalUrl();
        Request.Builder requestBuilder = getRequestBuilder();
        if (getMethod().isGet()) {
            HttpUrl.Builder httpUrlbuilder = HttpUrl.parse(finalUrl).newBuilder();
            for (String key : hashMap.keySet()) {
                Object v = Objects.requireNonNull(hashMap.get(key)).v;
                if (Objects.requireNonNull(hashMap.get(key)).isEncode)
                    httpUrlbuilder.addEncodedQueryParameter(key, v.toString());
                else
                    httpUrlbuilder.addQueryParameter(key, v.toString());
            }
            builder.url(httpUrlbuilder.build()).get();
        } else if (getMethod().isPost()) {
            requestBuilder.post(buildFromRequestBody());
        } else if (getMethod().isDelete()) {
            requestBuilder.delete(buildFromRequestBody());
        } else if (getMethod().isPatch()) {
            requestBuilder.patch(buildFromRequestBody());
        } else if (getMethod().isPut()) {
            requestBuilder.put(buildFromRequestBody());
        } else if (getMethod().isHead()) {
            requestBuilder.head();
        }

        requestBuilder.url(finalUrl);
        return getRequestBuilder().build();
    }

    private FormBody buildFromRequestBody() {
        FormBody.Builder formBodybuilder = new FormBody.Builder();
        for (String key : hashMap.keySet()) {
            Object v = Objects.requireNonNull(hashMap.get(key)).v;
            if (Objects.requireNonNull(hashMap.get(key)).isEncode)
                formBodybuilder.addEncoded(key, v.toString());
            else
                formBodybuilder.add(key, v.toString());
        }
        return formBodybuilder.build();
    }
}
