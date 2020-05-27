package com.ldw.okhttp_bhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

import com.ldw.bhttp.BHttp
import com.ldw.bhttp.callback.Observer
import com.ldw.bhttp.parse.MyResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        t.setOnClickListener {
            BHttp.create(ApiService::class.java)
                .test("26233")
                .to(this)
                .subscribe(object : Observer<MyResponse<String?>> {
                    override fun onSubscribe() {

                    }

                    override fun onNext(stringMyResponse: MyResponse<String?>) {
                        //  Toast.makeText(this@MainActivity,stringMyResponse.data, Toast.LENGTH_SHORT).show()
                        ed.setText(Gson().toJson(stringMyResponse))
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }

                    override fun onComplete() {

                    }
                })
            /*BHttp.create(ApiService::class.java)
                .login("3")
                .to(this)
                .subscribe(object : Observer<MyResponse<Bean?>> {
                    override fun onSubscribe() {

                    }

                    override fun onNext(stringMyResponse: MyResponse<Bean?>) {
                      //  Toast.makeText(this@MainActivity,stringMyResponse.data, Toast.LENGTH_SHORT).show()
                       *//* val data = stringMyResponse.data
                        val code = stringMyResponse.code
                        ed.setText("$code")
                        ed.setText("\n")
                        ed.setText(stringMyResponse.data)*//*
                        val data = stringMyResponse.data
                        val code = stringMyResponse.code
                        ed.setText("$code")
                        ed.setText("\n")
                        ed.setText(Gson().toJson(stringMyResponse))
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }

                    override fun onComplete() {

                    }
                })*/
            /* .subscribe({
                 println(it.data)
                 Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
             }, {
                 Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
             })*/

        }
    }
}
