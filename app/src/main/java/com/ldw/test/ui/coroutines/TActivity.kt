package com.ldw.test.ui.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bhttp.wrapper.generator.BHttp
import com.ldw.test.R
import kotlinx.coroutines.suspendCancellableCoroutine

class TActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t)
    }

    suspend fun test(): String {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { print1("取消invokeOnCancellation xxxxxxxxx") }

            BHttp.get("")
            /* Thread(Runnable {
                 Thread.sleep(1000)
                 continuation.cancel(  RuntimeException("xx"))
                 continuation.resume("xx", onCancellation = {
                     print1("取消resume xxxxxxxxx")
                 })

                 print1("执行resume xxxxxxxxx")
             }).start()*/
        }
    }
    private fun print1(s: String) {
        Log.i("xxxxxxxxxxx",s)
    }

}
