package com.yourname.macdscanner.core.model

enum class Timeframe(val value: String) {
    M1("1m"),
    M5("5m"),
    M15("15m"),
    H1("1h"),
    H4("4h"),
    D1("1d");

    companion object {
        fun fromValue(value: String): Timeframe = entries.firstOrNull { it.value == value } ?: H1
    }
}
