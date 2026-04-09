package com.yourname.macdscanner.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkerScheduler(private val context: Context) {
    fun schedulePeriodicScan(intervalMinutes: Long = 15L) {
        val safeIntervalMinutes = intervalMinutes.coerceAtLeast(MIN_PERIODIC_INTERVAL_MINUTES)
        val request = PeriodicWorkRequestBuilder<PeriodicScanWorker>(
            safeIntervalMinutes,
            TimeUnit.MINUTES,
        ).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            PeriodicScanWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request,
        )
    }

    companion object {
        private const val MIN_PERIODIC_INTERVAL_MINUTES = 15L
    }
}
