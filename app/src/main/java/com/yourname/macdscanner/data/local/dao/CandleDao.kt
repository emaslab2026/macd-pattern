package com.yourname.macdscanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.macdscanner.data.local.entity.CandleEntity

@Dao
interface CandleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<CandleEntity>)

    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe ORDER BY open_time ASC LIMIT :limit")
    suspend fun getCandles(symbol: String, timeframe: String, limit: Int): List<CandleEntity>
}
