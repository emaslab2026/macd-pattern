package com.yourname.macdscanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourname.macdscanner.data.local.dao.CandleDao
import com.yourname.macdscanner.data.local.dao.SignalDao
import com.yourname.macdscanner.data.local.dao.WatchlistDao
import com.yourname.macdscanner.data.local.entity.CandleEntity
import com.yourname.macdscanner.data.local.entity.SignalEntity
import com.yourname.macdscanner.data.local.entity.WatchlistSymbolEntity

@Database(
    entities = [WatchlistSymbolEntity::class, CandleEntity::class, SignalEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun candleDao(): CandleDao
    abstract fun signalDao(): SignalDao
}
