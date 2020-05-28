package com.ldw.test


import com.ldw.bhttp.OkHttp
import com.ldw.bhttp.callback.Observer
import com.ldw.bhttp.entry.MyResponse
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //println(BHttp.getMessage())
        //OkHttp.postJson("http://192.168.1.3:8022//test/")
        OkHttp.postJson()
            .add("id", "11")
            .asResponse(String::class.java)
            .subscribe(object : Observer<MyResponse<String>> {
                override fun onSubscribe() {}
                override fun onNext(response: MyResponse<String>) {
                    val data = response.data
                    print(data)
                }
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }
}
