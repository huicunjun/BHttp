package com.ldw.bhttp.compiler;

import com.ldw.bhttp.annotation.DefaultDomain;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class AnnotationProcessor extends AbstractProcessor {
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("AnnotationProcessor");

        try {
            generateHelloworld();
            yuansheng(roundEnv);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return true;
    }

    // 第一种生成方法：
    private void generateHelloworld() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello World")
                .addStatement("System.out.println($S)", "Hello World")
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .addMethod(main)
                .build();
        JavaFile javaFile = JavaFile.builder("bhttp.wrapper.generator", typeSpec)
                .build();

        javaFile.writeTo(filer);
    }

    private void yuansheng(RoundEnvironment roundEnv) {
        StringBuilder builder = new StringBuilder()
                .append("package bhttp.wrapper.generator;\n\n")//generator
                .append("public  class BHttp {\n\n") // open class
                .append("\tpublic static String getMessage() {\n") // open method
                .append("\t\treturn \"");


        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(DefaultDomain.class)) {
            String objectType = element.getSimpleName().toString();
            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");
        }


        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class


        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("bhttp.wrapper.generator.BHttp");
            OutputStream outputStream = source.openOutputStream();
            OutputStreamWriter osr = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);
          //  Writer writer = osr.w
            bufferedWriter.write(ss);

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(DefaultDomain.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    String ss = "package bhttp.wrapper.generator;\n" +
            "\n" +
            "import android.app.Activity;\n" +
            "import android.os.Handler;\n" +
            "import android.os.Looper;\n" +
            "import android.os.Message;\n" +
            "\n" +
            "import androidx.annotation.NonNull;\n" +
            "import androidx.annotation.Nullable;\n" +
            "import androidx.appcompat.app.AppCompatActivity;\n" +
            "import androidx.fragment.app.Fragment;\n" +
            "import androidx.fragment.app.FragmentActivity;\n" +
            "import androidx.lifecycle.Lifecycle;\n" +
            "import androidx.lifecycle.LifecycleEventObserver;\n" +
            "import androidx.lifecycle.LifecycleOwner;\n" +
            "\n" +
            "import com.ldw.bhttp.annotation.GET;\n" +
            "import com.ldw.bhttp.annotation.POST;\n" +
            "import com.ldw.bhttp.annotation.Query;\n" +
            "import com.ldw.bhttp.callback.Consumer;\n" +
            "import com.ldw.bhttp.callback.Observer;\n" +
            "import com.ldw.bhttp.httpsend.HttpSend;\n" +
            "import com.ldw.bhttp.param.Param;\n" +
            "import com.ldw.bhttp.parse.Parse;\n" +
            "import com.ldw.bhttp.ssl.SSLSocketFactoryImpl;\n" +
            "import com.ldw.bhttp.ssl.X509TrustManagerImpl;\n" +
            "import com.ldw.bhttp.utils.LogUtils;\n" +
            "\n" +
            "import org.jetbrains.annotations.NotNull;\n" +
            "\n" +
            "import java.io.IOException;\n" +
            "import java.lang.annotation.Annotation;\n" +
            "import java.lang.reflect.InvocationHandler;\n" +
            "import java.lang.reflect.Method;\n" +
            "import java.lang.reflect.ParameterizedType;\n" +
            "import java.lang.reflect.Proxy;\n" +
            "import java.lang.reflect.Type;\n" +
            "import java.util.Map;\n" +
            "import java.util.concurrent.ConcurrentHashMap;\n" +
            "import java.util.concurrent.TimeUnit;\n" +
            "\n" +
            "import javax.net.ssl.HostnameVerifier;\n" +
            "import javax.net.ssl.SSLSession;\n" +
            "import javax.net.ssl.SSLSocketFactory;\n" +
            "import javax.net.ssl.X509TrustManager;\n" +
            "\n" +
            "import okhttp3.Call;\n" +
            "import okhttp3.Callback;\n" +
            "import okhttp3.OkHttpClient;\n" +
            "import okhttp3.Response;\n" +
            "\n" +
            "/**\n" +
            " * @date 2020/5/26 19:10\n" +
            " * @user 威威君\n" +
            " */\n" +
            "public class BHttp<T> {\n" +
            "    // private static LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();\n" +
            "    private static final Map<Method, BHttp<?>> serviceMethodCache = new ConcurrentHashMap<>();\n" +
            "    private static OkHttpClient okHttpClient = null;\n" +
            "\n" +
            "\n" +
            "    private int state = 0;\n" +
            "    private static final int state_OK = 0;\n" +
            "    private static final int state_cancel = 1;\n" +
            "\n" +
            "    private static final int MSG_ON_DESTROY = 666;\n" +
            "\n" +
            "    private static OkHttpClient getOkHttpClient() {\n" +
            "        if (okHttpClient == null) {\n" +
            "            okHttpClient = getDefaultOkHttpClient();\n" +
            "        }\n" +
            "        return okHttpClient;\n" +
            "    }\n" +
            "\n" +
            "    private static OkHttpClient getDefaultOkHttpClient() {\n" +
            "        X509TrustManager trustAllCert = new X509TrustManagerImpl();\n" +
            "        SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);\n" +
            "        return new OkHttpClient.Builder()\n" +
            "                .connectTimeout(10, TimeUnit.SECONDS)\n" +
            "                .readTimeout(10, TimeUnit.SECONDS)\n" +
            "                .writeTimeout(10, TimeUnit.SECONDS)\n" +
            "                .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书\n" +
            "                .hostnameVerifier(new HostnameVerifier() {\n" +
            "                    @Override\n" +
            "                    public boolean verify(String hostname, SSLSession session) {\n" +
            "                        return true;\n" +
            "                    }\n" +
            "                }) //忽略host验证\n" +
            "                .build();\n" +
            "    }\n" +
            "\n" +
            "    public static void init(OkHttpClient okHttpClient) {\n" +
            "        if (BHttp.okHttpClient != null) {\n" +
            "            throw new RuntimeException(\"只能初始化一次 OkHttpClient\");\n" +
            "        }\n" +
            "        BHttp.okHttpClient = okHttpClient;\n" +
            "    }\n" +
            "\n" +
            "    public static void setDefaultDomain(@NotNull String s) {\n" +
            "        Param.setDefaultDomain(s);\n" +
            "    }\n" +
            "\n" +
            "    public Param getParam() {\n" +
            "        return param;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {\n" +
            "        @Override\n" +
            "        public boolean handleMessage(@NonNull Message msg) {\n" +
            "            if (msg.what == MSG_ON_DESTROY) {\n" +
            "\n" +
            "            }\n" +
            "            return false;\n" +
            "        }\n" +
            "    });\n" +
            "    private Param param = new Param();\n" +
            "    private Parse<?> parse;\n" +
            "    private Call call;\n" +
            "    Type returnType;\n" +
            "\n" +
            "    public BHttp(Method method, Object[] args, boolean isRetrofit) {\n" +
            "        if (isRetrofit)\n" +
            "            loadService(method, args);\n" +
            "    }\n" +
            "\n" +
            "    public BHttp() {\n" +
            "\n" +
            "    }\n" +
            "    //###########################################请求方法相关#################################################################\n" +
            "\n" +
            "    @NotNull\n" +
            "    public static Param postFrom(@NotNull String s) {\n" +
            "        return new Param();\n" +
            "    }\n" +
            "\n" +
            "    @NotNull\n" +
            "    public static BHttp postJson(@NotNull String s) {\n" +
            "        return new BHttp();\n" +
            "    }\n" +
            "\n" +
            "    @NotNull\n" +
            "    public BHttp add(String k, Object v) {\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    @NotNull\n" +
            "    public HttpSend<T> asObject(Class<T> tClass) {\n" +
            "        return new HttpSend<T>(param, tClass);\n" +
            "    }\n" +
            "\n" +
            "    @NotNull\n" +
            "    public HttpSend<T> asResponse(Class<T> tClass) {\n" +
            "        return new HttpSend<T>(param, tClass);\n" +
            "    }\n" +
            "   /* static class Factory<D> {\n" +
            "        @NotNull\n" +
            "        public Factory add(String k, Object v) {\n" +
            "            return this;\n" +
            "        }\n" +
            "\n" +
            "        @NotNull\n" +
            "        public <D> HttpSend<D> asObject(Class<?> tClass) {\n" +
            "\n" +
            "            return new HttpSend<D>();\n" +
            "        }\n" +
            "\n" +
            "        public  void subscribe(final Observer<D> observer) {\n" +
            "\n" +
            "\n" +
            "        }\n" +
            "    }*/\n" +
            "\n" +
            "\n" +
            "    //#############################################################################################################################\n" +
            "\n" +
            "\n" +
            "    //###########################################参数解析相关方法#################################################################\n" +
            "\n" +
            "    @SuppressWarnings(\"unchecked\")\n" +
            "    public static <T> T create(final Class<T> service) {\n" +
            "        validateServiceInterface(service);\n" +
            "        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},\n" +
            "                new InvocationHandler() {\n" +
            "                    @Override\n" +
            "                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)\n" +
            "                            throws Throwable {\n" +
            "                        Type returnType = method.getGenericReturnType();\n" +
            "                        BHttp<?> result = serviceMethodCache.get(method);\n" +
            "                        if (result != null) {\n" +
            "                            LogUtils.logd(\"缓存读取\");\n" +
            "                            result.reLoadParam(method, args);\n" +
            "                            return result;\n" +
            "                        }\n" +
            "                        synchronized (serviceMethodCache) {\n" +
            "                            result = serviceMethodCache.get(method);\n" +
            "                            if (result == null) {\n" +
            "                                result = new BHttp(method, args, true);\n" +
            "                                serviceMethodCache.put(method, result);\n" +
            "                            }\n" +
            "                        }\n" +
            "                        // System.out.println(new Gson().toJson(args) +\"XXXXXXXXXXXXX\");\n" +
            "                        return result;\n" +
            "\n" +
            "                    }\n" +
            "                });\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 基本的检查安全\n" +
            "     *\n" +
            "     * @param service\n" +
            "     * @param <T>\n" +
            "     */\n" +
            "    private static <T> void validateServiceInterface(Class<T> service) {\n" +
            "        if (!service.isInterface()) {\n" +
            "            throw new IllegalArgumentException(\"API declarations must be interfaces.\");\n" +
            "        }\n" +
            "        if (service.getInterfaces().length > 0) {\n" +
            "            throw new IllegalArgumentException(\"API interfaces must not extend other interfaces.\");\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private void loadService(Method method, Object[] args) {\n" +
            "        returnType = method.getGenericReturnType();\n" +
            "        ParameterizedType parameterizedType = (ParameterizedType) returnType;\n" +
            "        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();\n" +
            "        Type rawType = parameterizedType.getRawType();\n" +
            "        returnType = actualTypeArguments[0];\n" +
            "\n" +
            "\n" +
            "        Annotation[] annotations = method.getAnnotations();\n" +
            "        param = new Param();\n" +
            "        for (int i = 0; i < annotations.length; i++) {\n" +
            "            //System.out.println(annotations[i].annotationType() + \"XXXXXXXX\");\n" +
            "            if (annotations[i] instanceof GET) {\n" +
            "                parseHttpMethodAndPath(((GET) annotations[i]).value(), com.ldw.bhttp.param.Method.GET);\n" +
            "            } else if (annotations[i] instanceof POST) {\n" +
            "                parseHttpMethodAndPath(((POST) annotations[i]).value(), com.ldw.bhttp.param.Method.POST);\n" +
            "            }\n" +
            "        }\n" +
            "        Annotation[][] parameterAnnotations = method.getParameterAnnotations();\n" +
            "        for (int i = 0; i < parameterAnnotations.length; i++) {\n" +
            "            Annotation[] parameterAnnotation = parameterAnnotations[i];\n" +
            "            for (int j = 0; j < parameterAnnotation.length; j++) {\n" +
            "                Annotation annotation = parameterAnnotation[i];\n" +
            "                System.out.println(annotation.annotationType() + \"XXXXXXXXXXXX\");\n" +
            "                parseHttParam(annotation, args[i]);\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 解析参数\n" +
            "     *\n" +
            "     * @param annotation\n" +
            "     */\n" +
            "    private void parseHttParam(Annotation annotation, Object value) {\n" +
            "        if (annotation instanceof Query) {\n" +
            "            System.out.println(value + \"XXXXXXXXXXXXX\");\n" +
            "            param.addQuery(((Query) annotation).value(), value.toString(), ((Query) annotation).encoded());\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 解析请求方法跟地址\n" +
            "     *\n" +
            "     * @param value\n" +
            "     * @param method\n" +
            "     */\n" +
            "    void parseHttpMethodAndPath(String value, com.ldw.bhttp.param.Method method) {\n" +
            "        param.setMethod(method);\n" +
            "        param.setUrl(value);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 重新加载参数，避免重新反射查询不必要的值\n" +
            "     */\n" +
            "    private void reLoadParam(Method method, Object[] args) {\n" +
            "        param.reload();\n" +
            "        Annotation[][] parameterAnnotations = method.getParameterAnnotations();\n" +
            "        for (int i = 0; i < parameterAnnotations.length; i++) {\n" +
            "            Annotation[] parameterAnnotation = parameterAnnotations[i];\n" +
            "            for (int j = 0; j < parameterAnnotation.length; j++) {\n" +
            "                Annotation annotation = parameterAnnotation[i];\n" +
            "                System.out.println(annotation.annotationType() + \"XXXXXXXXXXXX\");\n" +
            "                parseHttParam(annotation, args[i]);\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "    //#############################################################################################################################\n" +
            "\n" +
            "\n" +
            "    //###############################################生命周期相关方法#################################################################\n" +
            "    private void addLifeLis(Lifecycle lifecycle) {\n" +
            "        lifecycle.addObserver(new LifecycleEventObserver() {\n" +
            "            @Override\n" +
            "            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {\n" +
            "                if (event == Lifecycle.Event.ON_DESTROY) {\n" +
            "                    state = state_cancel;\n" +
            "                    if (call != null) {\n" +
            "                        if (!call.isCanceled()) {\n" +
            "                            call.cancel();\n" +
            "                            LogUtils.logd(\"停止请求\");\n" +
            "                        }\n" +
            "                    }\n" +
            "                    //  handler.obtainMessage(MSG_ON_DESTROY);\n" +
            "                } else {\n" +
            "                    state = state_OK;\n" +
            "                }\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "    public BHttp<T> to(Activity activity) {\n" +
            "        if (activity instanceof AppCompatActivity) {\n" +
            "            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;\n" +
            "            return to(appCompatActivity.getLifecycle());\n" +
            "        } else if (activity instanceof FragmentActivity) {\n" +
            "            FragmentActivity fragmentActivity = (FragmentActivity) activity;\n" +
            "            return to(fragmentActivity.getLifecycle());\n" +
            "        } else {\n" +
            "            throw new RuntimeException(\"当前 Activity 或者 Fragment不支持 getLifecycle\");\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    public BHttp<T> to(androidx.lifecycle.Lifecycle lifecycle) {\n" +
            "        if (lifecycle != null) {\n" +
            "            addLifeLis(lifecycle);\n" +
            "        }\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    public BHttp<T> to(Fragment fragment) {\n" +
            "        to(fragment.getLifecycle());\n" +
            "        return this;\n" +
            "    }\n" +
            "    //############################################################################################################################\n" +
            "\n" +
            "\n" +
            "    public void subscribe(final Observer<T> observer) {\n" +
            "        //parse = new Parse<>(method);\n" +
            "        observer.onSubscribe();\n" +
            "        call = getOkHttpClient().newCall(param.getRequest());\n" +
            "        if (state == state_OK) {\n" +
            "            call.enqueue(new Callback() {\n" +
            "                @Override\n" +
            "                public void onFailure(@NotNull Call call, @NotNull IOException e) {\n" +
            "                    if (e.getMessage().contains(\"Close\")) {\n" +
            "                        return;\n" +
            "                    }\n" +
            "                    if (state == state_OK) {\n" +
            "                        handler.post(new Runnable() {\n" +
            "                            @Override\n" +
            "                            public void run() {\n" +
            "                                observer.onError(e);\n" +
            "                                observer.onComplete();\n" +
            "                            }\n" +
            "                        });\n" +
            "                    }\n" +
            "                    LogUtils.logd(e.getMessage());\n" +
            "                }\n" +
            "\n" +
            "                @Override\n" +
            "                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {\n" +
            "                    if (state != state_OK)\n" +
            "                        return;\n" +
            "                    parse = new Parse<>();\n" +
            "                    String s = response.body().string();\n" +
            "                    final T convert = (T) parse.convert(returnType, s);\n" +
            "                    LogUtils.logd(\"convert\");\n" +
            "                    LogUtils.logd(response);\n" +
            "                    handler.post(new Runnable() {\n" +
            "                        @Override\n" +
            "                        public void run() {\n" +
            "                            observer.onNext(convert);\n" +
            "                            observer.onComplete();\n" +
            "                        }\n" +
            "                    });\n" +
            "                    // onNext.accept();\n" +
            "                }\n" +
            "            });\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    public void subscribe(Consumer<T> onNext, Consumer<? super Throwable> onError) {\n" +
            "        call = getOkHttpClient().newCall(param.getRequest());\n" +
            "        if (state == state_OK) {\n" +
            "            call.enqueue(new Callback() {\n" +
            "                @Override\n" +
            "                public void onFailure(@NotNull Call call, @NotNull IOException e) {\n" +
            "                    if (e.getMessage().contains(\"close\")) {//\n" +
            "                        return;\n" +
            "                    }\n" +
            "                    if (state == state_OK) {\n" +
            "                        handler.post(new Runnable() {\n" +
            "                            @Override\n" +
            "                            public void run() {\n" +
            "                                onError.accept(e);\n" +
            "                            }\n" +
            "                        });\n" +
            "                    }\n" +
            "                    LogUtils.logd(e.getMessage());\n" +
            "                }\n" +
            "\n" +
            "                @Override\n" +
            "                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {\n" +
            "                    if (state != state_OK)\n" +
            "                        return;\n" +
            "                    parse = new Parse<>();\n" +
            "                    String s = response.body().string();\n" +
            "                    final T convert = (T) parse.convert(returnType, s);\n" +
            "                    LogUtils.logd(\"convert\");\n" +
            "                    LogUtils.logd(response);\n" +
            "                    handler.post(new Runnable() {\n" +
            "                        @Override\n" +
            "                        public void run() {\n" +
            "                            onNext.accept(convert);\n" +
            "                        }\n" +
            "                    });\n" +
            "                }\n" +
            "            });\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 开启Debug模式\n" +
            "     *\n" +
            "     * @param b\n" +
            "     */\n" +
            "    public static void setDebug(boolean b) {\n" +
            "        LogUtils.setDebug(b);\n" +
            "    }\n" +
            "\n" +
            "}\n";
}
