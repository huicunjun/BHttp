package com.ldw.test.ui.default

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bhttp.wrapper.generator.BHttp
import com.ldw.test.ApiService
import com.ldw.test.R
import com.ldw.test.Student
import com.ldw.test.bean.Banner
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.text.clear

class DefaultFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_retrofit, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // BHttp.setDefaultDomain("http://192.168.1.2:8022/")
        BHttp.setDebug(true)
        get.setOnClickListener {
            get()
        }
        post.setOnClickListener {
            post()
        }
        clear.setOnClickListener { ed.setText("") }
    }

    fun get() {
        for (i in 0..0) {
            BHttp.get("test")
                .add("id", "1")
                .add("pass", "2")
                .to(this)
                .asString()
                .subscribe({
                    ed.append(it.toString())
                }, {
                    ed.append(it.message.toString())
                })
        }
    }

    fun post() {
        for (i in 0..0) {
            BHttp.postJson("test/postjson")
                .add("id", "1")
                .add("img", "2")
                .add("pass", "3")
                .to(this)
                .asObject(Banner::class.java)
                .subscribe({
                    ed.append(it.toString())
                    //ed.append(it.img.toString())
                }, {
                    ed.append(it.message.toString())
                })
        }
    }
}
