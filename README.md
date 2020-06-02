# BHttp·极简Http库

<img src="https://v1.jinrishici.com/all.svg">

### 简介

本项目不依赖Retrofit和Rxjava，但参考了Retrofit源码，实现了Retrofit的声明式Api，以及Rxjava的链式调用线程自动切换，
功能涵盖请求缓存，请求加密，公共参数，自定义请求，自定义解析器等。具有以下特点：

* 简：方法封装极简，使用成本极低，链式调用一气呵成。
* 全：请求缓存，请求加密，公共参数，自定义请求，自定义解析器等，功能涵盖多数业务场景。
* 强：链式请求，Retrofit的声明式接口写法均支持。
* 轻：项目依赖少，剔除OKHttp与Gson，开发者可自行选择依赖。

### 注意
从Bhttp 0.5开始完全剔除Okhttp与Gson的依赖，需要开发者自行依赖。



## 开始使用
 [![](https://jitpack.io/v/huicunjun/BHttp.svg)](https://jitpack.io/#huicunjun/BHttp)
### 1.  Gradle依赖

```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}

implementation 'com.github.huicunjun:BHttp:0.7'//BHttp核心依赖库
annotationProcessor 'com.github.huicunjun:bhttp-compiler:0.7'//Bhttp生成库 kotlin项目请使用kapt
implementation 'com.github.huicunjun:bhttp-annotation:0.2'//BHttp注解库

implementation group: 'com.squareup.okhttp3', name: 'okhttp', version: '4.7.2'//必须
implementation 'com.google.code.gson:gson:2.8.6' //必须
```
### 2.  初始化BHttp

```java
        BHttp.setDebug(true);//开启debug 打印网络请求日志
        BHttp.init(okHttpClient);//可根据需要传入开发者的okHttpClient  非必须

        //在您的域名常量使用注解方式声明
        public class Url {
            @DefaultDomain
            public static String  host = "https://gdptdad.com";
        }

        //在您的统一返回Response实体类添加@AsResponse注解
        @AsResponse
        public class Response<D> {
            public int code;
            public String msg;
            public D data;
        }

```
### 3.  Retrofit式调用

```java
        public interface ApiService {
            @GET("test")
            BHttp<Response<String>> test(@Query(value = "id",encoded = true) String id);
        }

       BHttp.create(ApiService.class)
                .test("hello")
                .to(this)//监听生命周期，页面销毁自动结束请求
                .subscribe(stringResponse -> {
                   //请求完成回调，这里是主线程，直接UI操作
                 }, throwable -> {
                   //请求时出现错误回调，这里是主线程        
                 });

```

### 4.  链式调用
```java
       BHttp.postJson("login")
                .add("id","123")
                .to(this)//监听生命周期，页面销毁自动结束请求
                .asResponse(String.class)
                .subscribe(stringResponse -> {
                   //请求完成回调，这里是主线程，直接UI操作
                 }, throwable -> {
                   //请求时出现错误回调，这里是主线程        
                 });

```
### 5.  监听请求所有状态
```java
      BHttp.create(ApiService.class)
                .test("hello")
                .to(this)//监听生命周期，页面销毁自动结束请求
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe() {
                        //请求前执行的逻辑，这里是主线程
                    }

                    @Override
                    public void onNext(@NonNull Response<String> stringResponse) {
                        //请求完成回调，这里是主线程，直接UI操作
                    }

                    @Override
                    public void onError(@NonNull Throwable e1) {
                        //请求时出现错误回调，这里是主线程
                    }

                    @Override
                    public void onComplete() {
                        //请求结束回调，这里是主线程（不管失败与否，最终都会执行改方法！）
                    }
                });

```
### 7.  优点  
参考Retrofit源码，相比于Retrofit,完全可以替代，更多的是Api写法上的超越。参考RXjava源码，相对于Rxjava，实现了基本的subscribe，线程切换，链式回调。

### 8.  缺点  
还未实现RxJava的流式操作，所以对于多条并行的请求，一个请求依赖另一个请求结果的，得回调地狱了！无线套娃。

                
### 关于项目
本项目一直是个人自用网络请求库。之前未尝试单独剥离出来。观摩了 [RxHttp](https://github.com/liujingxing/okhttp-RxHttp "RxHttp")，[RxHttpUtils](https://github.com/lygttpod/RxHttpUtils) ，[RxHttpUtils](https://github.com/lygttpod/RxHttpUtils) 等前辈的源码，他们在实现上各有千秋，本人取其精华，有感而作。
能力有限，欢迎有更好的实现方式的一起交流探讨！

> 更多功能，请下载Demo体验

### 联系
Emali:huicunjun@gmail.com
Wechat:
![image](https://github.com/huicunjun/BHttp/screen/wechat.jpg)

