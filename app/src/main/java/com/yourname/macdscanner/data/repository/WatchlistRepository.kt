package com.yourname.macdscanner.data.repository

import com.yourname.macdscanner.data.local.dao.WatchlistDao
import com.yourname.macdscanner.data.local.entity.WatchlistSymbolEntity

class WatchlistRepository(private val dao: WatchlistDao) {
    fun observeAll() = dao.observeAll()
    suspend fun getEnabled() = dao.getEnabledSymbols()

    suspend fun addSymbol(symbol: String, marketType: String, now: Long) {
        dao.upsert(
            WatchlistSymbolEntity(
                symbol = symbol.uppercase(),
                marketType = marketType,
                enabled = true,
                createdAt = now,
                updatedAt = now,
            ),
        )
    }

    suspend fun removeSymbol(symbol: String) = dao.deleteBySymbol(symbol.uppercase())
}
