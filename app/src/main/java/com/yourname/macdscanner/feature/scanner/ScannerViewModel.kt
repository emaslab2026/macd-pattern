package com.yourname.macdscanner.feature.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.macdscanner.core.model.AppSettings
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.data.repository.SettingsRepository
import com.yourname.macdscanner.notification.SignalNotifier
import com.yourname.macdscanner.scan.ScanOrchestrator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val orchestrator: ScanOrchestrator,
    private val notifier: SignalNotifier,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    val timeframe: StateFlow<Timeframe> = settingsRepository.observeSettings()
        .map(AppSettings::timeframe)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Timeframe.H1,
        )

    private val _status = MutableStateFlow("No scans yet")
    val status = _status.asStateFlow()

    fun selectTimeframe(tf: Timeframe) {
        viewModelScope.launch {
            settingsRepository.updateTimeframe(tf, System.currentTimeMillis())
        }
    }

    fun scanNow() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettings()
            val signals = orchestrator.run(settings.timeframe, System.currentTimeMillis())
            if (settings.notificationsEnabled) {
                notifier.notifySignals(signals)
            }
            _status.value = if (signals.isEmpty()) "No new signals" else "${signals.size} new signal(s)"
        }
    }

    class Factory(
        private val orchestrator: ScanOrchestrator,
        private val notifier: SignalNotifier,
        private val settingsRepository: SettingsRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScannerViewModel(orchestrator, notifier, settingsRepository) as T
        }
    }
}
