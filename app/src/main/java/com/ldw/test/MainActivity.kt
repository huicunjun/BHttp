package com.ldw.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.ldw.bhttp.BHttp
import com.ldw.bhttp.callback.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun get(view: View) {
        BHttp.create(ApiService::class.java)
            .test("2626633")
            .to(this)
            .subscribe({
                ed.append(it.toString())
            }, {
                ed.append(it.message.toString())
            })
    }

    fun post(view: View) {
        BHttp.postFrom("http://192.168.1.3:8022//test/")
            .add("id", "11")
            .asObject(Response::class.java)
            .subscribe(object : Observer<Response<*>> {
                override fun onSubscribe() {}
                override fun onNext(response: Response<*>) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    fun download(view: View) {
        BHttp.create(ApiService::class.java)
            .download("test")
            .to(this)
            .subscribe(object : Observer<Response<String?>> {
                override fun onSubscribe() {

                }

                override fun onNext(stringMyResponse: Response<String?>) {
                    //  Toast.makeText(this@MainActivity,stringMyResponse.data, Toast.LENGTH_SHORT).show()
                    ed.setText(Gson().toJson(stringMyResponse))
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {

                }
            })
    }

    fun clear(view: View) {
        ed.setText("")
    }


}
