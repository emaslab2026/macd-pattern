package com.yourname.macdscanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourname.macdscanner.feature.history.SignalHistoryScreen
import com.yourname.macdscanner.feature.history.SignalHistoryViewModel
import com.yourname.macdscanner.feature.scanner.ScannerScreen
import com.yourname.macdscanner.feature.scanner.ScannerViewModel
import com.yourname.macdscanner.feature.settings.SettingsScreen
import com.yourname.macdscanner.feature.settings.SettingsViewModel
import com.yourname.macdscanner.feature.watchlist.WatchlistScreen
import com.yourname.macdscanner.feature.watchlist.WatchlistViewModel

class MainActivity : ComponentActivity() {
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationsPermissionIfNeeded()
        val app = application as App

        setContent { AppRoot(app) }
    }

    private fun requestNotificationsPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppRoot(app: App) {
    val tabs = remember { listOf("Watchlist", "Scanner", "History", "Settings") }
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("MACD Pattern Scanner") }) },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {},
                        label = { Text(label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            when (selectedTab) {
                0 -> {
                    val vm: WatchlistViewModel = viewModel(
                        factory = WatchlistViewModel.Factory(app.watchlistRepository),
                    )
                    WatchlistScreen(vm)
                }

                1 -> {
                    val vm: ScannerViewModel = viewModel(
                        factory = ScannerViewModel.Factory(app.scanOrchestrator, app.signalNotifier),
                    )
                    ScannerScreen(vm)
                }

                2 -> {
                    val vm: SignalHistoryViewModel = viewModel(
                        factory = SignalHistoryViewModel.Factory(app.signalRepository),
                    )
                    SignalHistoryScreen(vm)
                }

                else -> {
                    val vm: SettingsViewModel = viewModel(
                        factory = SettingsViewModel.Factory(app.workerScheduler),
                    )
                    SettingsScreen(vm)
                }
            }
        }
    }
}
