package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DemoScreen() {
    var name by remember { mutableStateOf("") }
    var greetedName by remember { mutableStateOf<String?>(null) }
    var counter by remember { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize().padding(top = 64.dp),
        Arrangement.spacedBy(24.dp),
        Alignment.CenterHorizontally,
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name", style = MaterialTheme.typography.labelMedium) },
            textStyle = MaterialTheme.typography.bodyLarge,
        )

        Button(onClick = { greetedName = name.ifBlank { null } }) {
            Text("Greet", style = MaterialTheme.typography.labelLarge)
        }

        greetedName?.let {
            Text(
                "Hello, $it!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Counter: $counter",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
            Button(onClick = { counter-- }) {
                Text("-", style = MaterialTheme.typography.titleMedium)
            }
            Button(onClick = { counter++ }) {
                Text("+", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
