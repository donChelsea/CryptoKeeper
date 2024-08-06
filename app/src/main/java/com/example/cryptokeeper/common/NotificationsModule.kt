package com.example.cryptokeeper.common

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.cryptokeeper.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsModule @Inject constructor(private val context: Context) {

    private fun showNotification(
        title: String,
        text: String,
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context.applicationContext, "coin_events")
            .setContentTitle("title")
            .setContentText("text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(1, notification)
    }
}