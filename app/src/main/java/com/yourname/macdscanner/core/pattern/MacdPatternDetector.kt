package com.yourname.macdscanner.core.pattern

import com.yourname.macdscanner.core.model.MacdPoint
import com.yourname.macdscanner.core.model.Signal
import com.yourname.macdscanner.core.model.SignalType
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.core.pattern.rules.BearishCrossoverRule
import com.yourname.macdscanner.core.pattern.rules.BullishCrossoverRule
import com.yourname.macdscanner.core.pattern.rules.HistogramContractionRule
import com.yourname.macdscanner.core.pattern.rules.HistogramExpansionRule
import com.yourname.macdscanner.core.pattern.rules.ZeroLineCrossRule

class MacdPatternDetector(
    private val bullish: BullishCrossoverRule = BullishCrossoverRule(),
    private val bearish: BearishCrossoverRule = BearishCrossoverRule(),
    private val zero: ZeroLineCrossRule = ZeroLineCrossRule(),
    private val expansion: HistogramExpansionRule = HistogramExpansionRule(),
    private val contraction: HistogramContractionRule = HistogramContractionRule(),
) {
    fun detect(symbol: String, timeframe: Timeframe, points: List<MacdPoint>, nowEpochMs: Long): List<Signal> {
        if (points.size < 2) return emptyList()
        val previous = points[points.lastIndex - 1]
        val current = points.last()

        val out = mutableListOf<Signal>()
        fun add(type: SignalType) {
            out += Signal(
                symbol = symbol,
                timeframe = timeframe,
                type = type,
                detectedAtEpochMs = nowEpochMs,
                candleOpenTimeEpochMs = current.candleOpenTimeEpochMs,
                macdValue = current.macd,
                signalValue = current.signal,
                histogramValue = current.histogram,
            )
        }

        if (bullish.matches(previous, current)) add(SignalType.BULLISH_CROSSOVER)
        if (bearish.matches(previous, current)) add(SignalType.BEARISH_CROSSOVER)
        if (zero.matches(previous, current)) add(SignalType.ZERO_LINE_CROSS)
        if (expansion.matches(previous, current)) add(SignalType.HISTOGRAM_EXPANSION)
        if (contraction.matches(previous, current)) add(SignalType.HISTOGRAM_CONTRACTION)

        return out
    }
}
