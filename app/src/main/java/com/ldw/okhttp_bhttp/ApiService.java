package com.ldw.okhttp_bhttp;

import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.annotation.GET;
import com.ldw.bhttp.annotation.POST;
import com.ldw.bhttp.annotation.Query;
import com.ldw.bhttp.parse.MyResponse;

/**
 * @date 2020/5/26 19:28
 * @user 威威君
 */
public interface ApiService {
   // @GET("http://192.168.1.3:8022//test/")
    @GET("test/")
    BHttp<MyResponse<String>> test(@Query("id") String id);

    @GET("http://192.168.1.3:8022//test/")
    BHttp<MyResponse<Bean>> login(String id);

    @POST("SSdgdsgS")
    BHttp<MyResponse<String>> order(String id);
}
