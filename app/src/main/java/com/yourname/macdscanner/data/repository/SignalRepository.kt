package com.yourname.macdscanner.data.repository

import com.yourname.macdscanner.core.model.Signal
import com.yourname.macdscanner.data.local.dao.SignalDao
import com.yourname.macdscanner.data.local.entity.SignalEntity

class SignalRepository(private val dao: SignalDao) {
    fun observeLatest(limit: Int) = dao.observeLatest(limit)

    suspend fun insertNew(signals: List<Signal>): List<Signal> {
        if (signals.isEmpty()) return emptyList()
        val ids = dao.insertAll(
            signals.map {
                SignalEntity(
                    symbol = it.symbol,
                    timeframe = it.timeframe.value,
                    signalType = it.type.name,
                    detectedAt = it.detectedAtEpochMs,
                    candleOpenTime = it.candleOpenTimeEpochMs,
                    macdValue = it.macdValue,
                    signalLineValue = it.signalValue,
                    histogramValue = it.histogramValue,
                )
            },
        )
        return signals.filterIndexed { index, _ -> ids[index] != -1L }
    }
}
