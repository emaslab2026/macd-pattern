package com.yourname.macdscanner.core.indicator

import com.yourname.macdscanner.core.model.Candle
import com.yourname.macdscanner.core.model.MacdPoint
import kotlin.math.max

class MacdCalculator(
    private val fastPeriod: Int = 12,
    private val slowPeriod: Int = 26,
    private val signalPeriod: Int = 9,
) {
    fun calculate(candles: List<Candle>): List<MacdPoint> {
        if (candles.isEmpty()) return emptyList()

        val closes = candles.map { it.close }
        val fastEma = ema(closes, fastPeriod)
        val slowEma = ema(closes, slowPeriod)
        val macdLine = closes.indices.map { i -> fastEma[i] - slowEma[i] }
        val signalLine = ema(macdLine, signalPeriod)

        return candles.indices.map { i ->
            val macd = macdLine[i]
            val signal = signalLine[i]
            MacdPoint(
                candleOpenTimeEpochMs = candles[i].openTimeEpochMs,
                close = candles[i].close,
                macd = macd,
                signal = signal,
                histogram = macd - signal,
            )
        }
    }

    private fun ema(values: List<Double>, period: Int): List<Double> {
        if (values.isEmpty()) return emptyList()
        val p = max(period, 1)
        val multiplier = 2.0 / (p + 1)
        val out = MutableList(values.size) { 0.0 }
        out[0] = values[0]
        for (i in 1 until values.size) {
            out[i] = ((values[i] - out[i - 1]) * multiplier) + out[i - 1]
        }
        return out
    }
}
