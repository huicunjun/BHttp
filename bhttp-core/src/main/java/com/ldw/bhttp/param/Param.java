package com.ldw.bhttp.param;


import android.media.browse.MediaBrowser;

import androidx.lifecycle.Lifecycle;

import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.parse.Parse;

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
    HashMap<String, Object> hashMap = new HashMap<>();
    Method method;

    String domain = null;
    String url = null;

    private HttpUrl.Builder httpUrlbuilder;

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
                domain = BHttp.getDefaultDomain();
            }
            return domain + url;
        }
    }

    public void addQuery(String k, String v, boolean isEncode) {
        if (httpUrlbuilder == null) {
            httpUrlbuilder =  HttpUrl.parse(getFinalUrl()).newBuilder();
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
}
