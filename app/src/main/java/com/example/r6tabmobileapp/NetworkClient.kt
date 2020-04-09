package com.example.r6tabmobileapp

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream

class NetworkClient {
    fun get(url : String) : InputStream {
        val request = Request.Builder().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body
        return body!!.byteStream()
    }
}