package com.ldw.bhttp.httpsend;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.ldw.bhttp.callback.Consumer;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.param.Param;
import com.ldw.bhttp.parse.Parse;
import com.ldw.bhttp.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @date 2020/5/27 19:48
 * @user 威威君
 */
public class HttpSend<T> {
    private static OkHttpClient okHttpClient = null;
    Param param = new Param();
    Call call;
    private int state = 0;
    private static final int state_OK = 0;
    private static final int state_cancel = 1;

    private Type returnType;



    public HttpSend() {

    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void subscribe(Consumer<T> onNext, Consumer<? super Throwable> onError) {
        call = getOkHttpClient().newCall(param.getRequest());
        if (state == state_OK) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    if (e.getMessage().contains("close")) {//
                        return;
                    }
                    if (state == state_OK) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onError.accept(e);
                            }
                        });
                    }
                    LogUtils.logd(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (state != state_OK)
                        return;
                    String s = response.body().string();
                    final T convert = (T)  new Parse<>().convert(returnType, s);
                    LogUtils.logd("convert");
                    LogUtils.logd(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onNext.accept(convert);
                        }
                    });
                }
            });
        }

    }

    public void subscribe(final Observer<T> observer) {
        //parse = new Parse<>(method);
        observer.onSubscribe();
        call = getOkHttpClient().newCall(param.getRequest());
        if (state == state_OK) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    if (e.getMessage().contains("Close")) {
                        return;
                    }
                    if (state == state_OK) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                observer.onError(e);
                                observer.onComplete();
                            }
                        });
                    }
                    LogUtils.logd(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (state != state_OK)
                        return;
                    Parse parse = new Parse<>();
                    String s = response.body().string();
                    final T convert = (T) parse.convert(returnType, s);
                    LogUtils.logd("convert");
                    LogUtils.logd(response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            observer.onNext(convert);
                            observer.onComplete();
                        }
                    });
                    // onNext.accept();
                }
            });
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            return false;
        }
    });
}
