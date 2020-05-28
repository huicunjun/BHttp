package com.ldw.test;

import androidx.annotation.NonNull;

import com.ldw.bhttp.OkHttp;
import com.ldw.bhttp.callback.Consumer;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.entry.MyResponse;

/**
 * @date 2020/5/26 19:08
 * @user 威威君
 */


public class Test {
    public static void main(String[] args) {

      /*  AHttp.postJson("")
                .add("id","sss")
                .asResponse(String.class)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe() {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        BHttp.postFrom("")
                .add("id","sss")
                .asObject(Response.class)
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe() {

                    }

                    @Override
                    public void onNext(@NonNull Response response) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        BHttp.create(ApiService.class)
                .login("")
                .subscribe(new Consumer<Response<Bean>>() {
                    @Override
                    public void accept(Response<Bean> beanResponse) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });*/
        OkHttp.postJson("")
                .add("id","")
                .asResponse(String.class)
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

        OkHttp.postJson("http://192.168.1.3:8022//test/")
                .add("id","666")
                .asObject(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
        OkHttp.postJson("")
                .add("id","")
                .asResponse(String.class)
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
