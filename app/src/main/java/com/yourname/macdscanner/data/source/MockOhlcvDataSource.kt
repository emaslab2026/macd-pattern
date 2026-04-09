package com.yourname.macdscanner.data.source

import com.yourname.macdscanner.core.model.Candle
import com.yourname.macdscanner.core.model.Timeframe
import kotlin.random.Random

class MockOhlcvDataSource {
    fun getCandles(symbol: String, timeframe: Timeframe, count: Int, endTimeEpochMs: Long): List<Candle> {
        val random = Random((symbol + timeframe.value).hashCode())
        val intervalMs = when (timeframe) {
            Timeframe.M1 -> 60_000L
            Timeframe.M5 -> 300_000L
            Timeframe.M15 -> 900_000L
            Timeframe.H1 -> 3_600_000L
            Timeframe.H4 -> 14_400_000L
            Timeframe.D1 -> 86_400_000L
        }

        var price = 100.0 + random.nextDouble() * 40
        return (count downTo 1).map { i ->
            val openTime = endTimeEpochMs - i * intervalMs
            val drift = random.nextDouble(-1.5, 1.5)
            val open = price
            val close = (price + drift).coerceAtLeast(0.01)
            val high = maxOf(open, close) + random.nextDouble(0.0, 1.0)
            val low = minOf(open, close) - random.nextDouble(0.0, 1.0)
            price = close

            Candle(symbol, timeframe, openTime, open, high, low, close, random.nextDouble(100.0, 1000.0))
        }
    }
}
