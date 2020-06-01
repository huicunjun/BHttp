package com.ldw.test;
//import com.bhttp.wrapper.generator.*;

import com.bhttp.wrapper.generator.BHttp;
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
    BHttp<IResponse<String>> test(@Query("id") String id);

    @GET("http://gdptdad.com/download/app-release.apk")
    BHttp<IResponse<String>> download(@Query("id") String id);

    @GET("http://192.168.1.3:8022//test/")
    BHttp<IResponse<Bean>> login(String id);

    @POST("SSdgdsgS")
    BHttp<IResponse<String>> order(String id);
}
