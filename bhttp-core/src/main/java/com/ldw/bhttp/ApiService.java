package com.ldw.bhttp;

import com.ldw.bhttp.annotation.POST;
import com.ldw.bhttp.parse.MyResponse;

/**
 * @date 2020/5/26 19:28
 * @user 威威君
 */
public interface ApiService {

    @POST("SSS")
    BHttp<MyResponse<String>> login(String id);

    @POST("SSdgdsgS")
    BHttp<MyResponse<String>> order(String id);
}
