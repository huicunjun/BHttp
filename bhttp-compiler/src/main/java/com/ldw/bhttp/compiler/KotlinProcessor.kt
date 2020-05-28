package com.ldw.bhttp.compiler

import com.ldw.bhttp.annotation.DefaultDomain
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * @date 2020/5/28 10:23
 * @user 威威君
 */
class KotlinProcessor : AbstractProcessor() {
    var filer: Filer? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        val map = processingEnv.options
        className = map["bhttp_name"] ?: "BHttp"
        packname = map["package_name"] ?: "com.bhttp.wrapper.generator"
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        try {
            generate()
            createByString(roundEnv!!)
        } catch (e: IOException) {
            e.printStackTrace()
            println(e.message)
        }

        return true
    }

    @Throws(IOException::class)
    private fun generate() {
        val main = MethodSpec.methodBuilder("main")
            .addModifiers(
                Modifier.PUBLIC,
                Modifier.STATIC
            )
            .addParameter(Array<String>::class.java, "args")
            .addStatement("\$T.out.println(\$S)", System::class.java, "Hello World")
            .addStatement("System.out.println(\$S)", "Hello World")
            .build()
        val typeSpec =
            TypeSpec.classBuilder("HelloWorld")
                .addModifiers(
                    Modifier.FINAL,
                    Modifier.PUBLIC
                )
                .addMethod(main)
                .build()
        val javaFile = JavaFile.builder("com.bhttp.wrapper.generator", typeSpec)
            .build()
        javaFile.writeTo(filer)
    }

    private fun createByString(roundEnv: RoundEnvironment) {
        for (element in roundEnv.getElementsAnnotatedWith(
            DefaultDomain::class.java)) {
            val objectType = element.simpleName.toString()
        }
        try {
            val source = processingEnv.filer.createSourceFile("$packname.$className")
            val outputStream = source.openOutputStream()
            val osr = OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            val bufferedWriter = BufferedWriter(osr)
            bufferedWriter.write(replaceClass(ss))
            bufferedWriter.flush()
            bufferedWriter.close()
        } catch (e: IOException) {
        }
    }


    override fun getSupportedAnnotationTypes(): Set<String>? {
        val annotations: MutableSet<String> =
            HashSet()
        annotations.add(DefaultDomain::class.java.canonicalName)
        return annotations
    }

    companion object {
        var className: String? = "BHttp"
        var packname : String? = "com.bhttp.wrapper.generator"
        private fun replaceClass(ss: String): String {
           return ss.replace("BaseHttp","$className").replace("package com.ldw.bhttp;", "package $packname;")
        }

       // var data = SimpleDateFormat().format("yyyy/MM/dd HH:ss")//2020/5/26 19:10
        var ss = """
package com.ldw.bhttp;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ldw.bhttp.annotation.GET;
import com.ldw.bhttp.annotation.POST;
import com.ldw.bhttp.annotation.Query;
import com.ldw.bhttp.callback.Consumer;
import com.ldw.bhttp.callback.Observer;
import com.ldw.bhttp.entry.MyResponse;
import com.ldw.bhttp.httpsend.HttpSend;
import com.ldw.bhttp.ParameterizedTypeImpl;
import com.ldw.bhttp.param.Param;
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
public class OkHttp<T> {
    // private static LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
    private static final Map<Method, OkHttp<?>> serviceMethodCache = new ConcurrentHashMap<>();
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
        if (OkHttp.okHttpClient != null) {
            throw new RuntimeException("只能初始化一次 OkHttpClient");
        }
        OkHttp.okHttpClient = okHttpClient;
    }

    public static void setDebug(boolean b) {
        LogUtils.setDebug(b);
    }

    public static void setDefaultDomain(@NotNull String s) {
        Param.setDefaultDomain(s);
    }

    public Param getParam() {
        return param;
    }

    public OkHttp(Method method, Object[] args, boolean isRetrofit) {
        if (isRetrofit)
            loadService(method, args);
    }

    public OkHttp() {

    }

    //###########################################请求方法相关#################################################################
    @NotNull
    public static Param postFrom(@NotNull String s) {
        return new Param();
    }

  /*  @NotNull
    public static Param postJson(@NotNull String s) {
        Param param = new Param();
        param.setUrl(s);
        return param;
    }*/

    @NotNull
    public static OkHttp<?> postJson(String url) {
        OkHttp<?> client = new OkHttp<>();
        client.param.setUrl(url);
        client.param.setMethod(com.ldw.bhttp.param.Method.POST);
        return client;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public OkHttp<?> add(String k, Object v) {
        param.add(k, v);
        return this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <D> OkHttp<D> asObject(Class<D> tClass) {
        // returnType = new TypeToken<D>() {}.getType();
        this.tClass = tClass;
        return (OkHttp<D>) this;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <D> OkHttp<MyResponse<D>> asResponse(Class<D> tClass) {
        this.tClass = tClass;
        returnType = new TypeToken<MyResponse<D>>() {
        }.getType();
        return (OkHttp<MyResponse<D>>) this;
    }
    //#############################################################################################################################


    //###########################################参数解析相关方法#################################################################
    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        Type returnType = method.getGenericReturnType();
                        OkHttp<?> result = serviceMethodCache.get(method);
                        if (result != null) {
                            LogUtils.logd("缓存读取");
                            result.reLoadParam(method, args);
                            return result;
                        }
                        synchronized (serviceMethodCache) {
                            result = serviceMethodCache.get(method);
                            if (result == null) {
                                result = new OkHttp(method, args, true);
                                serviceMethodCache.put(method, result);
                            }
                        }
                        // System.out.println(new Gson().toJson(args) +"XXXXXXXXXXXXX");
                        return result;

                    }
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
            }
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (int j = 0; j < parameterAnnotation.length; j++) {
                Annotation annotation = parameterAnnotation[i];
                parseHttParam(annotation, args[i]);
            }
        }

    }

    private void parseHttParam(Annotation annotation, Object value) {
        if (annotation instanceof Query) {
            param.addQuery(((Query) annotation).value(), value.toString(), ((Query) annotation).encoded());
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
                Annotation annotation = parameterAnnotation[i];
                parseHttParam(annotation, args[i]);
            }
        }
    }
    //#############################################################################################################################


    //###############################################生命周期相关方法#################################################################
    private void addLifeLis(Lifecycle lifecycle) {
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    state = state_cancel;
                    if (call != null) {
                        if (!call.isCanceled()) {
                            call.cancel();
                            LogUtils.logd("停止请求");
                        }
                    }
                } else {
                    state = state_OK;
                }
            }
        });
    }

    public OkHttp<T> to(Activity activity) {
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

    public OkHttp<T> to(Lifecycle lifecycle) {
        if (lifecycle != null) {
            addLifeLis(lifecycle);
        }
        return this;
    }

    public OkHttp<T> to(Fragment fragment) {
        to(fragment.getLifecycle());
        return this;
    }
    //############################################################################################################################


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
                    parse = new Parse<>();
                    String s = response.body().string();
                    T convert = null;
                    if (returnType != null && tClass != null) {
                        convert = new Gson().fromJson(s, new ParameterizedTypeImpl(MyResponse.class, tClass));
                    } else if (returnType != null) {
                        convert = (T) parse.convert(returnType, s);
                    } else if (tClass != null) {
                        convert = (T) parse.convert(tClass, s);
                    }
                    LogUtils.logd("convert");
                    LogUtils.logd(response);
                    T finalConvert = convert;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            observer.onNext(finalConvert);
                            observer.onComplete();
                        }
                    });
                }
            });
        }
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
                    parse = new Parse<>();
                    String s = response.body().string();
                    T convert = null;
                    if (returnType != null && tClass != null) {
                        convert = new Gson().fromJson(s, new ParameterizedTypeImpl(MyResponse.class, tClass));
                    } else if (returnType != null) {
                        convert = (T) parse.convert(returnType, s);
                    } else if (tClass != null) {
                        convert = (T) parse.convert(tClass, s);
                    }
                    //Response<A> responseA = fromJson(result, new ParameterizedTypeImpl(Response.class, A.class));
                    LogUtils.logd("convert");
                    LogUtils.logd(response);
                    T finalConvert = convert;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onNext.accept(finalConvert);
                        }
                    });
                }
            });
        }

    }


    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_ON_DESTROY) {

            }
            return false;
        }
    });

}


"""
    }

}