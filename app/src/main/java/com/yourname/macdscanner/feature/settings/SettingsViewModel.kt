package com.yourname.macdscanner.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.macdscanner.core.model.AppSettings
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.data.repository.SettingsRepository
import com.yourname.macdscanner.worker.WorkerScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val scheduler: WorkerScheduler,
) : ViewModel() {
    val settings: StateFlow<AppSettings> = settingsRepository.observeSettings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppSettings.DEFAULT,
    )

    fun setInterval(minutes: Long) {
        viewModelScope.launch {
            settingsRepository.updateScanInterval(minutes, System.currentTimeMillis())
            scheduler.schedulePeriodicScan(minutes)
        }
    }

    fun setTimeframe(timeframe: Timeframe) {
        viewModelScope.launch {
            settingsRepository.updateTimeframe(timeframe, System.currentTimeMillis())
        }
    }

    class Factory(
        private val settingsRepository: SettingsRepository,
        private val scheduler: WorkerScheduler,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(settingsRepository, scheduler) as T
        }
    }
}
