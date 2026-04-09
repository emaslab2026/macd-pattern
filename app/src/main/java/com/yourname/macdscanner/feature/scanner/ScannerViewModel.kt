package com.yourname.macdscanner.feature.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.macdscanner.core.model.Timeframe
import com.yourname.macdscanner.notification.SignalNotifier
import com.yourname.macdscanner.scan.ScanOrchestrator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val orchestrator: ScanOrchestrator,
    private val notifier: SignalNotifier,
) : ViewModel() {
    private val _timeframe = MutableStateFlow(Timeframe.H1)
    val timeframe = _timeframe.asStateFlow()

    private val _status = MutableStateFlow("No scans yet")
    val status = _status.asStateFlow()

    fun selectTimeframe(tf: Timeframe) {
        _timeframe.value = tf
    }

    fun scanNow() {
        viewModelScope.launch {
            val signals = orchestrator.run(_timeframe.value, System.currentTimeMillis())
            notifier.notifySignals(signals)
            _status.value = if (signals.isEmpty()) "No new signals" else "${signals.size} new signal(s)"
        }
    }

    class Factory(
        private val orchestrator: ScanOrchestrator,
        private val notifier: SignalNotifier,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ScannerViewModel(orchestrator, notifier) as T
    }
}
