package com.example.yourslovakiafrontend.api_handler

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
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

class SSEHandler(private val context: Context) {
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
        sendNotification(pointsOfInterest)
    }

    private fun sendNotification(poi: List<PointOfInterest>) {
        val notificationId = poi[0].id
        val builder = NotificationCompat.Builder(context, "POI_UPDATE_CHANNEL")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("New POI Update:")
            .setContentText("${poi.size} updated objects.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId.toInt(), builder.build())
    }
}

