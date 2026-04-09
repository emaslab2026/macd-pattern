package com.yourname.macdscanner.data.repository

import com.yourname.macdscanner.core.model.Candle
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.data.local.dao.CandleDao
import com.yourname.macdscanner.data.local.entity.CandleEntity
import com.yourname.macdscanner.data.source.MockOhlcvDataSource

class CandleRepository(
    private val dao: CandleDao,
    private val source: MockOhlcvDataSource,
) {
    suspend fun fetchAndStoreMock(symbol: String, timeframe: Timeframe, count: Int, endTimeEpochMs: Long): List<Candle> {
        val candles = source.getCandles(symbol, timeframe, count, endTimeEpochMs)
        dao.upsertAll(
            candles.map {
                CandleEntity(
                    symbol = it.symbol,
                    timeframe = it.timeframe.value,
                    openTime = it.openTimeEpochMs,
                    open = it.open,
                    high = it.high,
                    low = it.low,
                    close = it.close,
                    volume = it.volume,
                    source = "MOCK",
                    createdAt = endTimeEpochMs,
                )
            },
        )
        return candles
    }
}
