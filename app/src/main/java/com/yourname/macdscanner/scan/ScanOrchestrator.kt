package com.yourname.macdscanner.scan

import com.yourname.macdscanner.core.model.Signal
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.data.repository.WatchlistRepository

class ScanOrchestrator(
    private val watchlistRepository: WatchlistRepository,
    private val scanUseCase: ScanUseCase,
) {
    suspend fun run(timeframe: Timeframe, nowEpochMs: Long): List<Signal> {
        return watchlistRepository.getEnabled().flatMap { item ->
            scanUseCase.scanSymbol(item.symbol, timeframe, nowEpochMs)
        }
    }
}
