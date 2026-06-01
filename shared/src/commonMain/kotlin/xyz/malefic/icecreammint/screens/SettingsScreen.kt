package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxHeight()
                .width(500.dp)
                .border(width = 20.dp, color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start,
    )
    {
        Text("General", fontSize = 16.sp)
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
        Text("General")
    }
}
