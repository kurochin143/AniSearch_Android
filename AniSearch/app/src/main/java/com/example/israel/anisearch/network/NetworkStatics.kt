package com.example.israel.anisearch.network

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

private const val IMAGE_REQUEST_CALL_TIMEOUT = 3000L
private const val IMAGE_REQUEST_READ_TIMEOUT = 3000L

class NetworkStatics {
    companion object {
        fun requestImage(url: String): Call {
            val okHttpClient = OkHttpClient.Builder()
                .callTimeout(IMAGE_REQUEST_CALL_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(IMAGE_REQUEST_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()
            val request = Request.Builder()
                .url(url)
                .build()

            return okHttpClient.newCall(request)
        }
    }
}