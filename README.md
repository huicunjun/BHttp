# BHttp·极简Http库

<img src="https://v1.jinrishici.com/all.svg">

### 简介

本项目是一个不依赖Retrofit和Rxjava，但实现了Retrofit的声明式Api，以及Rxjava的链式调用线程自动切换，
功能涵盖请求缓存，请求加密，公共参数，Token时效，自定义解析数据等。具有以下特点：

* 快：方法封装极简，链式调用一气呵成。
* 全：既可以使用单独的链式请求，也可以使用Retrofit的声明式接口写法。
* 轻：项目依赖少，仅仅依赖了Okhttp，Gson





## 开始使用
 [![](https://jitpack.io/v/huicunjun/BHttp.svg)](https://jitpack.io/#huicunjun/BHttp)
### 1.  Gradle依赖

```html
implementation 'com.github.huicunjun:BHttp:lastversion'
```
### 2.  初始化BHttp

```html
        BHttp.setDebug(true);//开启debug
        BHttp.setDefaultDomain("http://192.168.1.3:8022/");//设置默认请求域名
```
### 3.  Retrofit式调用

```html
        public interface ApiService {
            @GET("http://192.168.1.3:8022//test/")
            BHttp<Response<String>> test(@Query(value = "id",encoded = true) String id);
        }

       BHttp.create(ApiService.class)
                .login("hello")
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
本项目一直是个人自用网络请求库。之前未尝试单独剥离出来。观摩了 [RxHttp](https://github.com/liujingxing/okhttp-RxHttp "RxHttp")有感，发现请求三部曲非常棒，能做到上手成本低，功能全，这点令我非常激动！于是乎有感而作！
