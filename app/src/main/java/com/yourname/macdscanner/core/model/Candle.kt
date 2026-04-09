package com.yourname.macdscanner.core.model

data class Candle(
    val symbol: String,
    val timeframe: Timeframe,
    val openTimeEpochMs: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
)
