package com.example.yourslovakiafrontend.api_handler

import com.example.yourslovakiafrontend.models.PointOfInterestAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import fiit.mtaa.yourslovakia.models.PointOfInterest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class SSEHandler {
    private val client = OkHttpClient()
    private val gson = GsonBuilder()
        .registerTypeAdapter(PointOfInterest::class.java, PointOfInterestAdapter())
        .create()

    fun startListening() {
        CoroutineScope(Dispatchers.IO).launch {
            val request =
                Request.Builder().url("https://yourslovakia.streicher.tech/notifications").build()
            val call = client.newCall(request)
            val response = call.execute()
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response) {
        response.use { res ->
            res.body?.source()?.let { source ->
                while (!source.exhausted()) {
                    val update = source.readUtf8Line()
                    update?.let {
                        handleUpdate(it)
                    }
                }
            }
        }
    }

    private fun handleUpdate(jsonUpdate: String) {
        val listType = object : TypeToken<List<PointOfInterest>>() {}.type
        val pointsOfInterest: List<PointOfInterest> = gson.fromJson(jsonUpdate, listType)
        println("${pointsOfInterest.size} updates objects.")
    }
}

