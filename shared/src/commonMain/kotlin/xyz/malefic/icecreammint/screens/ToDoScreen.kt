package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import xyz.malefic.icecreammint.RootComponent
import xyz.malefic.icecreammint.theme.icons.TablerX
import kotlin.uuid.Uuid

@Serializable
data class ToDoItem(
    val id: Uuid = Uuid.random(),
    val text: String,
    val completed: Boolean = false,
)

@Composable
fun ToDoScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    initialToDos: List<ToDoItem> = emptyList(),
    onToDosChanged: (List<ToDoItem>) -> Unit = {},
) {
    var ToDos by remember { mutableStateOf(initialToDos) }
    var newText by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                value = newText,
                onValueChange = { newText = it },
                placeholder = { Text("Add new to-do item") },
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = {
                    val trimmed = newText.trim()
                    if (trimmed.isNotEmpty()) {
                        val newItem = ToDoItem(text = trimmed)
                        ToDos = ToDos + newItem
                        onToDosChanged(ToDos)
                        newText = ""
                    }
                },
            ) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (ToDos.isEmpty()) {
            Text("No tasks yet", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(items = ToDos, key = { it.id }) { item ->
                    ToDoRow(
                        item = item,
                        onToggle = { toggled ->
                            val updatedToDos =
                                ToDos.map { t ->
                                    if (t.id == toggled.id) {
                                        t.copy(completed = !t.completed)
                                    } else {
                                        t
                                    }
                                }
                            ToDos = updatedToDos
                            onToDosChanged(ToDos)
                        },
                        onDelete = { toDelete ->
                            ToDos = ToDos.filter { it.id != toDelete.id }
                            onToDosChanged(ToDos)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun ToDoRow(
    item: ToDoItem,
    onToggle: (ToDoItem) -> Unit,
    onDelete: (ToDoItem) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val textColor =
                if (item.completed) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }

            Checkbox(checked = item.completed, onCheckedChange = { onToggle(item) })
            Spacer(Modifier.width(8.dp))
            Text(item.text, style = MaterialTheme.typography.bodyLarge, color = textColor)
        }
        IconButton(onClick = { onDelete(item) }) {
            Icon(
                TablerX,
                "Delete",
                tint = MaterialTheme.colorScheme.error,
            )
        }
    }
}
