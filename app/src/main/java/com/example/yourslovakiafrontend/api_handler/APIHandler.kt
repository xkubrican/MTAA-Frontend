package com.example.yourslovakiafrontend.api_handler

import okhttp3.*
import java.io.IOException

val client = OkHttpClient()

fun run(url: String) {
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            println(response.body?.string())
        }
    })
}