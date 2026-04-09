package com.yourname.macdscanner.feature.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yourname.macdscanner.core.model.Timeframe

@Composable
fun ScannerScreen(viewModel: ScannerViewModel) {
    val timeframe by viewModel.timeframe.collectAsStateWithLifecycle()
    val status by viewModel.status.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Global timeframe")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Timeframe.entries.forEach { tf ->
                FilterChip(
                    selected = timeframe == tf,
                    onClick = { viewModel.selectTimeframe(tf) },
                    label = { Text(tf.value) },
                )
            }
        }

        Button(onClick = viewModel::scanNow) { Text("Scan now") }
        Text(status)
    }
}
