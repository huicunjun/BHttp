package com.ldw.bhttp;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ldw.bhttp.ParameterizedTypeImpl;
import com.ldw.bhttp.annotation.Form;
import com.ldw.bhttp.annotation.GET;
import com.ldw.bhttp.annotation.Json;
import com.ldw.bhttp.annotation.POST;
import com.ldw.bhttp.annotation.PUT;
import com.ldw.bhttp.annotation.Path;
import com.ldw.bhttp.annotation.Query;
import com.ldw.bhttp.callback.Consumer;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.entry.MyResponse;
import com.ldw.bhttp.param.Param;
import com.ldw.bhttp.param.ParamType;
import com.ldw.bhttp.parse.Parse;
import com.ldw.bhttp.ssl.SSLSocketFactoryImpl;
import com.ldw.bhttp.ssl.X509TrustManagerImpl;
import com.ldw.bhttp.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @date 2020/5/26 19:10
 * @user 威威君
 */
public class BaseBHttp<T> {
    // private static LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
    private static final Map<Method, BaseBHttp<?>> serviceMethodCache = new ConcurrentHashMap<>();
    private static OkHttpClient okHttpClient = null;
    private Param param = new Param();
    private Parse<?> parse;
    private Call call;
    private Type returnType;
    private int state = 0;
    private static final int state_OK = 0;
    private static final int state_cancel = 1;
    private static final int MSG_ON_DESTROY = 666;
    private Class<?> tClass;

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getDefaultOkHttpClient();
        }
        return okHttpClient;
    }

    private static OkHttpClient getDefaultOkHttpClient() {
        X509TrustManager trustAllCert = new X509TrustManagerImpl();
        SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }) //忽略host验证
                .build();
    }

    public static void init(OkHttpClient okHttpClient) {
        if (BaseBHttp.okHttpClient != null) {
            throw new RuntimeException("OkHttpClient Can only be initialized once");
        }
        BaseBHttp.okHttpClient = okHttpClient;
    }

    public static void setDebug(boolean b) {
        LogUtils.setDebug(b);
    }

    public static void setDefaultDomain(String s) {
        Param.setDefaultDomain(s);
    }

    public Param getParam() {
        return param;
    }

    public BaseBHttp(Method method, Object[] args, boolean isRetrofit) {
        if (isRetrofit)
            loadService(method, args);
        setDefaultDomain(null);
    }

    public BaseBHttp() {
        setDefaultDomain(null);
    }

    //###########################################请求方法相关#################################################################
    @NotNull
    public static BaseBHttp<?> postFrom(@NotNull String s) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(s);
        client.param.setMethod(com.ldw.bhttp.param.Method.POST);
        client.param.setParamType(ParamType.Form);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> postJson(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.POST);
        client.param.setParamType(ParamType.Json);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> postPath(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.POST);
        client.param.setParamType(ParamType.Path);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> putFrom(@NotNull String s) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(s);
        client.param.setMethod(com.ldw.bhttp.param.Method.PUT);
        client.param.setParamType(ParamType.Form);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> putJson(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.PUT);
        client.param.setParamType(ParamType.Json);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> putPath(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.PUT);
        client.param.setParamType(ParamType.Path);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> deleteFrom(@NotNull String s) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(s);
        client.param.setMethod(com.ldw.bhttp.param.Method.DELETE);
        client.param.setParamType(ParamType.Form);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> deleteJson(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.DELETE);
        client.param.setParamType(ParamType.Json);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> deletePath(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.DELETE);
        client.param.setParamType(ParamType.Path);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> patchFrom(@NotNull String s) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(s);
        client.param.setMethod(com.ldw.bhttp.param.Method.PATCH);
        client.param.setParamType(ParamType.Form);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> patchJson(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.PATCH);
        client.param.setParamType(ParamType.Json);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> patchPath(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.PATCH);
        client.param.setParamType(ParamType.Path);
        return client;
    }

    @NotNull
    public static BaseBHttp<?> get(String url) {
        BaseBHttp<?> client = new BaseBHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.GET);
        return client;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public BaseBHttp<?> add(String k, Object v) {
        param.add(k, v,false);
        return this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public BaseBHttp<?> addEncode(String k, Object v) {
        param.add(k, v, true);
        return this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <D> BaseBHttp<D> asObject(Class<D> tClass) {
        // returnType = new TypeToken<D>() {}.getType();
        this.tClass = tClass;
        return (BaseBHttp<D>) this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <D> BaseBHttp<MyResponse<D>> asResponse(Class<D> tClass) {
        this.tClass = tClass;
        returnType = new TypeToken<MyResponse<D>>() {
        }.getType();
        return (BaseBHttp<MyResponse<D>>) this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public BaseBHttp<String> asString() {
        this.tClass = String.class;
        return (BaseBHttp<String>) this;
    }

    //#############################################################################################################################


    //###########################################参数解析相关方法#################################################################
    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                (proxy, method, args) -> {
                    Type returnType = method.getGenericReturnType();
                    BaseBHttp<?> result = serviceMethodCache.get(method);
                    if (result != null) {
                        LogUtils.logd("缓存读取");
                        result.reLoadParam(method, args);
                        return result;
                    }
                    synchronized (serviceMethodCache) {
                        result = serviceMethodCache.get(method);
                        if (result == null) {
                            result = new BaseBHttp(method, args, true);
                            serviceMethodCache.put(method, result);
                        }
                    }
                    return result;

                });
    }

    private static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadService(Method method, Object[] args) {
        returnType = method.getGenericReturnType();
        ParameterizedType parameterizedType = (ParameterizedType) returnType;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type rawType = parameterizedType.getRawType();
        returnType = actualTypeArguments[0];
        Annotation[] annotations = method.getAnnotations();
        param = new Param();
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i] instanceof GET) {
                parseHttpMethodAndPath(((GET) annotations[i]).value(), com.ldw.bhttp.param.Method.GET);
            } else if (annotations[i] instanceof POST) {
                parseHttpMethodAndPath(((POST) annotations[i]).value(), com.ldw.bhttp.param.Method.POST);
            } else if (annotations[i] instanceof PUT) {
                parseHttpMethodAndPath(((PUT) annotations[i]).value(), com.ldw.bhttp.param.Method.PUT);
            }
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (int j = 0; j < parameterAnnotation.length; j++) {
                Annotation annotation = parameterAnnotation[j];
                parseHttParam(annotation, args[i]);
            }
        }
    }

    private void parseHttParam(Annotation annotation, Object value) {
        if (annotation instanceof Query) {
            param.add(((Query) annotation).value(), value.toString(), ((Query) annotation).encoded(), ParamType.Query);
        } else if (annotation instanceof Form) {
            param.add(((Form) annotation).value(), value.toString(), ((Form) annotation).encoded(), ParamType.Form);
        } else if (annotation instanceof Json) {
            param.add(((Json) annotation).value(), value.toString(), ((Json) annotation).encoded(), ParamType.Json);
        } else if (annotation instanceof Path) {
            param.add(((Path) annotation).value(), value.toString(), ((Path) annotation).encoded(), ParamType.Path);
        }
    }

    void parseHttpMethodAndPath(String value, com.ldw.bhttp.param.Method method) {
        param.setMethod(method);
        param.setUrl(value);
    }

    private void reLoadParam(Method method, Object[] args) {
        param.reload();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (int j = 0; j < parameterAnnotation.length; j++) {
                Annotation annotation = parameterAnnotation[j];
                parseHttParam(annotation, args[i]);
            }
        }
    }
    //#############################################################################################################################


    //###############################################生命周期相关方法#################################################################
    private void addLifeLis(Lifecycle lifecycle) {
        lifecycle.addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                state = state_cancel;
                if (call != null) {
                    if (!call.isCanceled()) {
                        call.cancel();
                        LogUtils.logd("停止请求");
                        System.gc();
                    }
                }
            } else {
                state = state_OK;
            }
        });
    }

    public BaseBHttp<T> to(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            return to(appCompatActivity.getLifecycle());
        } else if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            return to(fragmentActivity.getLifecycle());
        } else {
            throw new RuntimeException("当前 Activity 或者 Fragment不支持 getLifecycle");
        }

    }

    public BaseBHttp<T> to(Lifecycle lifecycle) {
        if (lifecycle != null) {
            addLifeLis(lifecycle);
        }
        return this;
    }

    public BaseBHttp<T> to(Fragment fragment) {
        to(fragment.getLifecycle());
        return this;
    }
    //############################################################################################################################

    @SuppressWarnings("unchecked")
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
                    try {
                        if (state != state_OK)
                            return;
                        parse = new Parse<>();
                        String s = response.body().string();
                        T convert = null;
                        convert = convert(s, returnType, tClass);
                        LogUtils.logd(response);
                        T finalConvert = convert;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                observer.onNext(finalConvert);
                                observer.onComplete();
                            }
                        });
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
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
                    try {
                        if (state != state_OK)
                            return;
                        parse = new Parse<>();
                        String s = response.body().string();
                        T convert = null;
                        convert = convert(s, returnType, tClass);
                        LogUtils.logd(response);
                        T finalConvert = convert;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onNext.accept(finalConvert);
                            }
                        });
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onError.accept(e);
                            }
                        });
                    }
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    private T convert(String s, Type returnType, Class<?> tClass) throws JsonSyntaxException {
        LogUtils.logd("请求结果：");
        LogUtils.logd(s);
        T convert = null;
        if (returnType != null && tClass != null) {
            convert = new Gson().fromJson(s, new ParameterizedTypeImpl(MyResponse.class, tClass));
        } else if (returnType != null) {
            convert = new Gson().fromJson(s, returnType);
        } else if (tClass != null) {
            if (tClass == String.class) {
                convert = (T) s;
            } else
                convert = (T) new Gson().fromJson(s, tClass);
        }
        if (convert != null) {
            return convert;
        }
        throw new JsonSyntaxException("Json解析数据异常");
        //   return convert;
    }

    private Handler handler = new Handler(Looper.getMainLooper(), msg -> {
        if (msg.what == MSG_ON_DESTROY) {

        }
        return false;
    });

}
