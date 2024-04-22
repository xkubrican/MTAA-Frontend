package com.example.yourslovakiafrontend.api_handler

import com.google.gson.Gson
import fiit.mtaa.yourslovakia.models.AuthenticationRequest
import fiit.mtaa.yourslovakia.models.AuthenticationResponse
import fiit.mtaa.yourslovakia.models.GenericPointOfInterestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


object ApiHandler {
    val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "https://yourslovakia.streicher.tech/"
    private var jwtToken = ""
    private var refreshToken = ""
    private lateinit var sseHandler: SSEHandler

    suspend fun register(authenticationRequest: AuthenticationRequest): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val jsonBody = gson.toJson(authenticationRequest)
                val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url(baseUrl + "users/create_user")
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    return@withContext response.isSuccessful
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext false
            }
        }

    suspend fun getToken(authenticationRequest: AuthenticationRequest): AuthenticationResponse? =
        withContext(Dispatchers.IO) {
            try {
                val jsonBody = gson.toJson(authenticationRequest)
                val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url(baseUrl + "api/auth")
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            return@withContext gson.fromJson(
                                responseBody,
                                AuthenticationResponse::class.java
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext null
        }

    suspend fun getPointsOfInterest(
        latitude: Double,
        longitude: Double,
        maxDistance: Long,
        monumentTypes: List<String>
    ): List<GenericPointOfInterestModel>? = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = (baseUrl + "points_of_interest").toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addQueryParameter("latitude", latitude.toString())
            urlBuilder.addQueryParameter("longitude", longitude.toString())
            urlBuilder.addQueryParameter("maxDistance", maxDistance.toString())
            monumentTypes.forEach { type ->
                urlBuilder.addQueryParameter("monumentTypes", type)
            }

            val request = Request.Builder()
                .url(urlBuilder.build().toString())
                .addHeader(
                    "Authorization",
                    "Bearer $jwtToken"
                )  // Include the JWT token in the Authorization header
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        return@withContext gson.fromJson(
                            responseBody,
                            Array<GenericPointOfInterestModel>::class.java
                        ).toList()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@withContext null
    }


    fun setTokens(jwt: String, refresh: String) {
        jwtToken = jwt
        refreshToken = refresh
        sseHandler = SSEHandler()
    }


}
