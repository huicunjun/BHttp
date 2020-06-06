package com.ldw.bhttp.param;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @date 2020/5/28 19:30
 * @user 威威君
 */
public class PathParam extends IRequest {

    public PathParam(String url, Method method) {
        super(url, method, ParamType.Path);
    }

    @Override
    public Request getRequest() {
        String finalUrl = getFinalUrl();
        Request.Builder requestBuilder = getRequestBuilder();

        HashMap<String, Object> maps = new HashMap<>();
        for (String key : hashMap.keySet()) {
            Object v = Objects.requireNonNull(hashMap.get(key)).v;
            maps.put(key, v);
        }
        finalUrl = finalUrl.replace(" ", "");
        for (String key : maps.keySet()) {
            finalUrl = finalUrl.replace("{" + key + "}", maps.get(key).toString());
        }
        builder.url(finalUrl);
        if (getMethod().isGet()) {
        } else if (getMethod().isPost()) {
            requestBuilder.post(RequestBody.create(new byte[]{}, null));
        } else if (getMethod().isDelete()) {
            requestBuilder.delete();
        } else if (getMethod().isPatch()) {
            requestBuilder.patch(RequestBody.create(new byte[]{}, null));
        } else if (getMethod().isPut()) {
            requestBuilder.put(RequestBody.create(new byte[]{}, null));
        } else if (getMethod().isHead()) {
            requestBuilder.head();
        }

        requestBuilder.url(finalUrl);
        return getRequestBuilder().build();
    }

}
