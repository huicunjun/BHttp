package com.ldw.test;

import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.annotation.GET;
import com.ldw.bhttp.annotation.POST;
import com.ldw.bhttp.annotation.Query;

/**
 * @date 2020/5/26 19:28
 * @user 威威君
 */
public interface ApiService {
    // @GET("http://192.168.1.3:8022//test/")
    @GET("test/")
    BHttp<Response<String>> test(@Query("id") String id);

    @GET("http://gdptdad.com/download/app-release.apk")
    BHttp<Response<String>> download(@Query("id") String id);

    @GET("http://192.168.1.3:8022//test/")
    BHttp<Response<Bean>> login(String id);

    @POST("SSdgdsgS")
    BHttp<Response<String>> order(String id);
}
