package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background), Arrangement.Top, Alignment.CenterHorizontally) {
        Text(
            "Welcome!",
            Modifier.padding(24.dp),
            MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge,
            fontFamily = FontFamily.Serif,
        )
    }
}
