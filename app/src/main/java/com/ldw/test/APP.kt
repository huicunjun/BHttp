package com.ldw.test

import android.app.Application
import com.ldw.bhttp.BaseHttp

/**
 * @date 2020/5/27 7:42
 * @user 威威君
 */
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseHttp.setDebug(true)
        BaseHttp.setDefaultDomain("http://192.168.1.3:8022/")
    }
}