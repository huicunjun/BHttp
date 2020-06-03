package com.ldw.test

//import com.bhttp.wrapper.generator.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bhttp.wrapper.generator.BHttp
import com.google.gson.Gson
import com.ldw.bhttp.BaseBHttp.postPath


import com.ldw.bhttp.callback.Observer
import com.ldw.test.bean.LoginBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BHttp.setDefaultDomain("")
        BHttp.setDebug(true)
    }

    fun get(view: View) {
        for (i in 0..0) {
            BHttp.get("https://v1.jinrishici.com/all.json")
                .asString()
                .to(this)
                .subscribe({
                    ed.append(it.toString())
                }, {
                    ed.append(it.message.toString())
                })
        }
    }

    fun  postFrom(view: View) {
        for (i in 0..0) {
            BHttp.postFrom("http://gdptdad.com:8080/api/user/login")
                .add("identifier", "2")
                .add("voucher", "2")
                .to(this)
                .asString()
                .subscribe(
                    {
                        ed.append("$it  $i ")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }

    fun postJson(view: View) {
        for (i in 0..0) {
            BHttp.postJson("test/postjson")
                .add("id", 2)
                .add("img", "666")
                .to(this)
                .asString()
                .subscribe(
                    {
                        ed.append("$it  $i ")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }


    fun response(view: View) {
        for (i in 0..0) {
            //  BHttp.postJson("http://192.168.1.3:8022//test/asResponse")
            BHttp.postFrom("http://gdptdad.com:8080/api/user/login")
                .add("identifier", "2")
                .add("voucher", "2")
                .asMySimpleResponse(LoginBean::class.java)
                .subscribe(
                    {
                        ed.append("${it.data.token}  $i")
                        ed.append("\n")
                        ed.append("${it.data.user.nickname}  $i")
                        ed.append("\n")
                        // ed.append(it.data?.name)
                        // ed.append("${it.code}")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }

    fun putPath(view: View) {
        for (i in 0..0) {
            //  BHttp.postJson("http://192.168.1.3:8022//test/asResponse")
            BHttp.putPath("test/path/{id}/{pass}")
                .add("id", "1")
                .add("pass", "2")
                .asString()
                .subscribe(
                    {

                         ed.append(it)
                        // ed.append("${it.code}")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }
    fun deletePath(view: View) {
        for (i in 0..0) {
            //  BHttp.postJson("http://192.168.1.3:8022//test/asResponse")
            BHttp.deletePath("test/path/{id}/{pass}")
                .add("id", "1")
                .add("pass", "2")
                .asString()
                .subscribe(
                    {

                        ed.append(it)
                        // ed.append("${it.code}")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }
    fun asString(view: View) {
        if (ei.text.toString().length < 1) {
            ei.setText("http://www.gdptdad.com/")
        }
        var ss = ei.text.toString()
        for (i in 0..1) {
            BHttp.get(ss)
                //.add("id", "666")
                .asString()
                .subscribe(
                    {
                        ed.append("${it.toString()}  i")
                        ed.append("\n")
                        // ed.append(it.data?.name)
                        // ed.append("${it.code}")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }

    fun download(view: View) {
        BHttp.create(ApiService::class.java)
            .download("test")
            .to(this)
            .subscribe(object : Observer<MySimpleResponse<String?>> {
                override fun onSubscribe() {

                }

                override fun onNext(stringMyResponse: MySimpleResponse<String?>) {
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
