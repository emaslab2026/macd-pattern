package com.yourname.macdscanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "signals",
    indices = [
        Index(value = ["symbol", "timeframe", "detected_at"]),
        Index(value = ["symbol", "timeframe", "signal_type", "candle_open_time"], unique = true),
    ],
)
data class SignalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "timeframe") val timeframe: String,
    @ColumnInfo(name = "signal_type") val signalType: String,
    @ColumnInfo(name = "detected_at") val detectedAt: Long,
    @ColumnInfo(name = "candle_open_time") val candleOpenTime: Long,
    @ColumnInfo(name = "macd_value") val macdValue: Double,
    @ColumnInfo(name = "signal_line_value") val signalLineValue: Double,
    @ColumnInfo(name = "histogram_value") val histogramValue: Double,
)
