# BHttp·极简Http库

<img src="https://v1.jinrishici.com/all.svg">

### 简介

本项目是一个不依赖Retrofit和Rxjava，但实现了Retrofit的声明式Api，以及Rxjava的链式调用线程自动切换，
功能涵盖请求缓存，请求加密，公共参数，Token时效，自定义解析数据等。具有以下特点：

* 快：方法封装极简，链式调用一气呵成。
* 全：既可以使用单独的链式请求，也可以使用Retrofit的声明式接口写法。
* 轻：项目依赖少，仅仅依赖了Okhttp，Gson

### 注意
从Bhttp 0.5开始完全剔除Okhttp与Gson的依赖，需要开发者自行依赖。



## 开始使用
 [![](https://jitpack.io/v/huicunjun/BHttp.svg)](https://jitpack.io/#huicunjun/BHttp)
### 1.  Gradle依赖

```html
    //编译版本
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

```html
        BHttp.setDebug(true);//开启debug 打印网络请求日志
        BHttp.init(okHttpClient);//可根据需要传入开发者的okHttpClient  非必须
        BHttp.setDefaultDomain("http://192.168.1.3:8022/");//设置默认请求域名

        也支持在您的域名常量使用注解方式声明
        public class Url {
            @DefaultDomain
            public static String  host = "http://192.168.1.2:8022";
        }

```
### 3.  Retrofit式调用

```html
        public interface ApiService {
            @GET("http://192.168.1.3:8022//test/")
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
```html
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
```html
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
  
                
### 关于项目
本项目一直是个人自用网络请求库。之前未尝试单独剥离出来。观摩了 [RxHttp](https://github.com/liujingxing/okhttp-RxHttp "RxHttp")有感，发现请求三部曲非常棒，能做到上手成本低，功能全，于是乎有感而作！
