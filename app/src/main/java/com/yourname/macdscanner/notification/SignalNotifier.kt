package com.yourname.macdscanner.notification

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.yourname.macdscanner.core.model.Signal

class SignalNotifier(private val context: Context) {
    fun notifySignals(signals: List<Signal>) {
        if (signals.isEmpty()) return
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) return

        val body = signals.take(3).joinToString(" • ") { "${it.symbol} ${it.timeframe.value} ${it.type.name}" }
        val notification = NotificationCompat.Builder(context, NotificationChannels.SIGNALS_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("MACD signals detected")
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .build()

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(1001, notification)
    }
}
