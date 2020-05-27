package com.ldw.okhttp_bhttp;

import androidx.annotation.NonNull;

import com.ldw.bhttp.ApiService;
import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.parse.MyResponse;

/**
 * @date 2020/5/26 19:08
 * @user 威威君
 */
public class Test {
    public static void main(String[] args) {
        BHttp.create(ApiService.class)
                .login("3")
                .subscribe(new Observer<MyResponse<String>>() {
                    @Override
                    public void onSubscribe() {

                    }

                    @Override
                    public void onNext(@NonNull MyResponse<String> stringMyResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
