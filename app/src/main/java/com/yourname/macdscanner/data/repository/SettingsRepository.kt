package com.yourname.macdscanner.data.repository

import com.yourname.macdscanner.core.model.AppSettings
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.data.local.dao.SettingsDao
import com.yourname.macdscanner.data.local.entity.AppSettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dao: SettingsDao) {
    companion object {
        private const val MIN_SCAN_INTERVAL_MINUTES = 15L
    }
    fun observeSettings(): Flow<AppSettings> =
        dao.observeSettings().map { it?.toDomain() ?: AppSettings.DEFAULT }

    suspend fun getSettings(): AppSettings = dao.getSettings()?.toDomain() ?: AppSettings.DEFAULT

    suspend fun updateTimeframe(timeframe: Timeframe, now: Long) {
        val current = dao.getSettings()?.toDomain() ?: AppSettings.DEFAULT
        dao.upsert(current.copy(timeframe = timeframe).toEntity(now))
    }

    suspend fun updateScanInterval(minutes: Long, now: Long) {
        val current = dao.getSettings()?.toDomain() ?: AppSettings.DEFAULT
        val safeMinutes = minutes.coerceAtLeast(MIN_SCAN_INTERVAL_MINUTES)
        dao.upsert(current.copy(scanIntervalMinutes = safeMinutes).toEntity(now))
    }

    suspend fun ensureDefault(now: Long) {
        if (dao.getSettings() == null) dao.upsert(AppSettings.DEFAULT.toEntity(now))
    }

    private fun AppSettingsEntity.toDomain(): AppSettings = AppSettings(
        timeframe = Timeframe.fromValue(timeframe),
        scanIntervalMinutes = scanIntervalMinutes,
        notificationsEnabled = notificationsEnabled,
    )

    private fun AppSettings.toEntity(now: Long): AppSettingsEntity = AppSettingsEntity(
        timeframe = timeframe.value,
        scanIntervalMinutes = scanIntervalMinutes,
        notificationsEnabled = notificationsEnabled,
        updatedAt = now,
    )
}
