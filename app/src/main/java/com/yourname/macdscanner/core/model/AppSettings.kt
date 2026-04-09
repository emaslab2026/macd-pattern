package com.yourname.macdscanner.core.model

data class AppSettings(
    val timeframe: Timeframe,
    val scanIntervalMinutes: Long,
    val notificationsEnabled: Boolean,
) {
    companion object {
        val DEFAULT = AppSettings(
            timeframe = Timeframe.H1,
            scanIntervalMinutes = 15,
            notificationsEnabled = true,
        )
    }
}
