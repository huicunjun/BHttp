package com.ldw.test;

import androidx.annotation.NonNull;

import com.ldw.bhttp.BHttp;
import com.ldw.bhttp.callback.Observer;

/**
 * @date 2020/5/26 19:08
 * @user 威威君
 */
public class Test {
    public static void main(String[] args) {
        BHttp.create(ApiService.class)
                .test("3")
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe() {

                    }

                    @Override
                    public void onNext(@NonNull Response<String> stringMyResponse) {

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
