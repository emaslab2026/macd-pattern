package com.yourname.macdscanner.core.model

data class Signal(
    val symbol: String,
    val timeframe: Timeframe,
    val type: SignalType,
    val detectedAtEpochMs: Long,
    val candleOpenTimeEpochMs: Long,
    val macdValue: Double,
    val signalValue: Double,
    val histogramValue: Double,
)
