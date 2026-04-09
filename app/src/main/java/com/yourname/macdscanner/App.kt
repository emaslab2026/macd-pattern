package com.yourname.macdscanner

import android.app.Application
import androidx.room.Room
import com.yourname.macdscanner.core.indicator.MacdCalculator
import com.yourname.macdscanner.core.pattern.MacdPatternDetector
import com.yourname.macdscanner.data.local.AppDatabase
import com.yourname.macdscanner.data.repository.CandleRepository
import com.yourname.macdscanner.data.repository.SignalRepository
import com.yourname.macdscanner.data.repository.WatchlistRepository
import com.yourname.macdscanner.data.source.MockOhlcvDataSource
import com.yourname.macdscanner.notification.NotificationChannels
import com.yourname.macdscanner.notification.SignalNotifier
import com.yourname.macdscanner.scan.ScanOrchestrator
import com.yourname.macdscanner.scan.ScanUseCase
import com.yourname.macdscanner.worker.WorkerScheduler

class App : Application() {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "macd_scanner.db").build()
    }

    val watchlistRepository by lazy { WatchlistRepository(db.watchlistDao()) }
    val candleRepository by lazy { CandleRepository(db.candleDao(), MockOhlcvDataSource()) }
    val signalRepository by lazy { SignalRepository(db.signalDao()) }

    private val macdCalculator by lazy { MacdCalculator() }
    private val detector by lazy { MacdPatternDetector() }

    val scanUseCase by lazy {
        ScanUseCase(candleRepository, signalRepository, macdCalculator, detector)
    }
    val scanOrchestrator by lazy { ScanOrchestrator(watchlistRepository, scanUseCase) }

    val signalNotifier by lazy { SignalNotifier(this) }
    val workerScheduler by lazy { WorkerScheduler(this) }

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
        workerScheduler.schedulePeriodicScan(15)
    }
}
