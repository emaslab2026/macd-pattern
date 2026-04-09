package com.yourname.macdscanner.scan

import com.yourname.macdscanner.core.indicator.MacdCalculator
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.core.pattern.MacdPatternDetector
import com.yourname.macdscanner.data.repository.CandleRepository
import com.yourname.macdscanner.data.repository.SignalRepository

class ScanUseCase(
    private val candleRepository: CandleRepository,
    private val signalRepository: SignalRepository,
    private val macdCalculator: MacdCalculator,
    private val detector: MacdPatternDetector,
) {
    suspend fun scanSymbol(symbol: String, timeframe: Timeframe, nowEpochMs: Long) =
        signalRepository.insertNew(
            detector.detect(
                symbol,
                timeframe,
                macdCalculator.calculate(
                    candleRepository.fetchAndStoreMock(symbol, timeframe, count = 100, endTimeEpochMs = nowEpochMs),
                ),
                nowEpochMs,
            ),
        )
}
