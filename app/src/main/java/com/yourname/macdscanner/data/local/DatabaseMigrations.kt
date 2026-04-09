package com.yourname.macdscanner.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS app_settings (
                    id INTEGER NOT NULL,
                    timeframe TEXT NOT NULL,
                    scan_interval_minutes INTEGER NOT NULL,
                    notifications_enabled INTEGER NOT NULL,
                    updated_at INTEGER NOT NULL,
                    PRIMARY KEY(id)
                )
                """.trimIndent(),
            )
            db.execSQL(
                """
                INSERT OR IGNORE INTO app_settings (
                    id,
                    timeframe,
                    scan_interval_minutes,
                    notifications_enabled,
                    updated_at
                ) VALUES (1, '1h', 15, 1, 0)
                """.trimIndent(),
            )
        }
    }
}
