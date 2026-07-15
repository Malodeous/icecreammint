package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import xyz.malefic.icecreammint.theme.icons.TablerX
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class TaskItem(
    val id: Uuid = Uuid.random(),
    val text: String,
    val completed: Boolean = false,
    @Contextual val date: Instant? = null,
)

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    initialTasks: List<TaskItem> = emptyList(),
    onTasksChanged: (List<TaskItem>) -> Unit = {},
) {
    var tasks by remember { mutableStateOf(initialTasks) }
    var newText by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.secondary),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            TextField(
                value = newText,
                onValueChange = { newText = it },
                placeholder = { Text("Add new to-do item") },
                modifier =
                    Modifier.background(color = MaterialTheme.colorScheme.onPrimaryContainer).weight(1f),
                colors =
                    TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                shape = RectangleShape,
            )

            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()

            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val trimmed = newText.trim()
                    if (trimmed.isNotEmpty()) {
                        val newItem = TaskItem(text = trimmed)
                        tasks = tasks + newItem
                        onTasksChanged(tasks)
                        newText = ""
                    }
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = if (isHovered) 1f else 0.5f),
                    ),
                interactionSource = interactionSource,
            ) {
                Text("Add", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        Spacer(Modifier.height(12.dp))

        if (tasks.isEmpty()) {
            Text(
                "No tasks yet",
                modifier = Modifier.padding(start = 20.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(items = tasks, key = { it.id }) { item ->
                    TaskRow(
                        item = item,
                        onToggle = { toggled ->
                            tasks =
                                tasks.map { t ->
                                    if (t.id == toggled.id) {
                                        t.copy(completed = !t.completed)
                                    } else {
                                        t
                                    }
                                }
                            onTasksChanged(tasks)
                        },
                        onDelete = { toDelete ->
                            tasks = tasks.filter { it.id != toDelete.id }
                            onTasksChanged(tasks)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskRow(
    item: TaskItem,
    onToggle: (TaskItem) -> Unit,
    onDelete: (TaskItem) -> Unit,
) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), Arrangement.SpaceBetween) {
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
