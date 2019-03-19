package com.navercorp.navercodelab

import android.app.Application
import android.content.res.Configuration
import okhttp3.*
import java.io.IOException

class CodeLabApplication : Application() {
    val self : CodeLabApplication

    val httpClient :OkHttpClient;

    init {
        self = this
        httpClient = OkHttpClient()

    }

    override fun onCreate() {
        super.onCreate()
            httpClient.newBuilder().also {
            }.build()

        val request = Request.Builder().url("http://m.naver.com").build()
        httpClient.newCall(request).enqueue(object:Callback {
            override fun onResponse(call: Call, response: Response) {

            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}