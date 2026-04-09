package com.yourname.macdscanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.macdscanner.data.local.entity.SignalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SignalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(signals: List<SignalEntity>): List<Long>

    @Query("SELECT * FROM signals ORDER BY detected_at DESC LIMIT :limit")
    fun observeLatest(limit: Int): Flow<List<SignalEntity>>
}
