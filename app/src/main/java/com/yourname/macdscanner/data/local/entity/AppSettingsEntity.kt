package com.yourname.macdscanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "timeframe") val timeframe: String,
    @ColumnInfo(name = "scan_interval_minutes") val scanIntervalMinutes: Long,
    @ColumnInfo(name = "notifications_enabled") val notificationsEnabled: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
