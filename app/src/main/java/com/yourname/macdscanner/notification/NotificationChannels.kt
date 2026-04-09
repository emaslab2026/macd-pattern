package com.yourname.macdscanner.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannels {
    const val SIGNALS_CHANNEL_ID = "signals"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            SIGNALS_CHANNEL_ID,
            "MACD Signals",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "Notifications for detected MACD pattern signals"
        }
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}
