package com.yourname.macdscanner.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yourname.macdscanner.App
import com.yourname.macdscanner.core.model.Timeframe

class PeriodicScanWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val app = applicationContext as App
        val signals = app.scanOrchestrator.run(Timeframe.H1, System.currentTimeMillis())
        app.signalNotifier.notifySignals(signals)
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "periodic_macd_scan"
    }
}
