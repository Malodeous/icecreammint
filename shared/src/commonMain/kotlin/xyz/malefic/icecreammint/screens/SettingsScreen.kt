package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        Arrangement.spacedBy(16.dp),
        Alignment.Start,
    )
    {
        Text("General Settings", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)

        repeat(5) {
            Text("Setting Option ${it + 1}", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
