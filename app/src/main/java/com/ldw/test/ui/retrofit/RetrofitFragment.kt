package com.ldw.test.ui.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bhttp.wrapper.generator.BHttp
import com.ldw.test.ApiService
import com.ldw.test.R
import kotlinx.android.synthetic.main.activity_main.*

class RetrofitFragment : Fragment() {


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

    fun post() {
        for (i in 0..0) {
            BHttp.create(ApiService::class.java)
                .login("666", "777")
                .to(this)
                .subscribe({
                    ed.append(it.toString())
                }, {
                    ed.append(it.message.toString())
                })
        }
    }

}
