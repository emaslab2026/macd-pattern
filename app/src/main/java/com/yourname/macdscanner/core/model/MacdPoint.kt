package com.yourname.macdscanner.core.model

data class MacdPoint(
    val candleOpenTimeEpochMs: Long,
    val close: Double,
    val macd: Double,
    val signal: Double,
    val histogram: Double,
)
