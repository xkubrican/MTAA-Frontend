package com.example.yourslovakiafrontend.api_handler

import com.google.gson.Gson
import fiit.mtaa.yourslovakia.models.AuthenticationRequest
import fiit.mtaa.yourslovakia.models.AuthenticationResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


object ApiHandler {
    val client = OkHttpClient()
    private val baseUrl = "https://yourslovakia.streicher.tech/"
    private var jwtToken = ""
    private var refreshToken = ""

    fun register(authenticationRequest: AuthenticationRequest) {
        val jsonBody = Gson().toJson(authenticationRequest)

        val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(baseUrl + "users/create_user")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (responseData == "true") {
                        println("Good response")
                    }
                } else {
                    println("Unsuccessful response")
                }
            }
        })
    }

    fun getToken(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val jsonBody = Gson().toJson(authenticationRequest)

        val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(
                baseUrl + "api/auth"
            )
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val authenticationResponse: AuthenticationResponse? =
                        Gson().fromJson(it.string(), AuthenticationResponse::class.java)
                    authenticationResponse?.let {
                        jwtToken = authenticationResponse.accessToken
                        refreshToken = authenticationResponse.refreshToken
                    }
                }
            }
        })
        return AuthenticationResponse(jwtToken, refreshToken)
    }

}
