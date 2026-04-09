package com.yourname.macdscanner.feature.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.macdscanner.data.repository.WatchlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchlistViewModel(private val repository: WatchlistRepository) : ViewModel() {
    val watchlist = repository.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addSymbol(symbol: String) {
        val clean = symbol.trim().uppercase()
        if (clean.isBlank()) return
        viewModelScope.launch { repository.addSymbol(clean, "STOCK", System.currentTimeMillis()) }
    }

    fun removeSymbol(symbol: String) {
        viewModelScope.launch { repository.removeSymbol(symbol) }
    }

    class Factory(private val repository: WatchlistRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = WatchlistViewModel(repository) as T
    }
}
