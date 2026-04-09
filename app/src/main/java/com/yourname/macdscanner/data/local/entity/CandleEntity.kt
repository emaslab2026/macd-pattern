package com.yourname.macdscanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "candles",
    indices = [
        Index(value = ["symbol", "timeframe", "open_time"], unique = true),
        Index(value = ["symbol", "timeframe"]),
    ],
)
data class CandleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "timeframe") val timeframe: String,
    @ColumnInfo(name = "open_time") val openTime: Long,
    @ColumnInfo(name = "open") val open: Double,
    @ColumnInfo(name = "high") val high: Double,
    @ColumnInfo(name = "low") val low: Double,
    @ColumnInfo(name = "close") val close: Double,
    @ColumnInfo(name = "volume") val volume: Double,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
