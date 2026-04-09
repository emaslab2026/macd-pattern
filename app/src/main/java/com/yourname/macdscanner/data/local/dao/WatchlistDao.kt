package com.yourname.macdscanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.macdscanner.data.local.entity.WatchlistSymbolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist_symbols ORDER BY symbol")
    fun observeAll(): Flow<List<WatchlistSymbolEntity>>

    @Query("SELECT * FROM watchlist_symbols WHERE enabled = 1 ORDER BY symbol")
    suspend fun getEnabledSymbols(): List<WatchlistSymbolEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: WatchlistSymbolEntity)

    @Query("DELETE FROM watchlist_symbols WHERE symbol = :symbol")
    suspend fun deleteBySymbol(symbol: String)
}
