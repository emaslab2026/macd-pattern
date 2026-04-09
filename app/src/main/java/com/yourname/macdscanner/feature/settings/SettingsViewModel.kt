package com.yourname.macdscanner.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourname.macdscanner.worker.WorkerScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(private val scheduler: WorkerScheduler) : ViewModel() {
    private val _interval = MutableStateFlow(15L)
    val interval = _interval.asStateFlow()

    fun setInterval(minutes: Long) {
        _interval.value = minutes
        scheduler.schedulePeriodicScan(minutes)
    }

    class Factory(private val scheduler: WorkerScheduler) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(scheduler) as T
    }
}
