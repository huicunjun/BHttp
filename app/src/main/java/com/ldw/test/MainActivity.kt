package com.ldw.test

//import com.bhttp.wrapper.generator.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bhttp.wrapper.generator.BHttp
import com.google.gson.Gson


import com.ldw.bhttp.callback.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // BHttp.setDefaultDomain("")
        BHttp.setDebug(true)
    }

    fun get(view: View) {
        for (i in 0..2) {
            BHttp.create(ApiService::class.java)
                .test("666")
                .to(this)
                .subscribe({
                    ed.append(it.toString())
                }, {
                    ed.append(it.message.toString())
                })
        }
    }

    fun post(view: View) {
        for (i in 0..999) {
            BHttp.postFrom("http://gdptdad.com:8080/api/user/login")
                .add("identifier", "2")
                .add("voucher", "2")
                .to(this)
                .asObject(LoginBean::class.java)
                .subscribe(
                    {
                        val stuname = it.data.user.student.stuname
                        val token = it.data.token
                        ed.append("$stuname  $i ")
                    }
                ) {
                    ed.append(it.message.toString())
                }
        }
    }
    fun response(view: View) {
        for (i in 0..3) {
            BHttp.postJson("http://192.168.1.3:8022//test/asResponse")
                .add("id", "666")
                .asIResponse(Student::class.java)
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
    fun asString(view: View) {
       if (ei.text.toString().length<1){
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
            .subscribe(object : Observer<IResponse<String?>> {
                override fun onSubscribe() {

                }

                override fun onNext(stringMyResponse: IResponse<String?>) {
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
