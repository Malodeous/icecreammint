package xyz.malefic.icecreammint.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import kotlinx.serialization.Serializable
import xyz.malefic.icecreammint.theme.icons.TablerAppWindow
import xyz.malefic.icecreammint.theme.icons.TablerX
import kotlin.uuid.Uuid

@Serializable
data class TaskItem(
    val id: Uuid = Uuid.random(),
    val text: String,
    val completed: Boolean = false,
    val dueDateTimeIso: String? = null,
)

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    initialTasks: List<TaskItem> = emptyList(),
    onTasksChanged: (List<TaskItem>) -> Unit = {},
) {
    var tasks by remember { mutableStateOf(initialTasks) }
    var newText by rememberSaveable { mutableStateOf("") }
    var selectedDueIso by rememberSaveable { mutableStateOf<String?>(null) }
    var showDueDialog by remember { mutableStateOf(false) }

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
                    Modifier
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        .weight(1f),
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

            Spacer(Modifier.width(4.dp))

            TextField(
                value = selectedDueIso?.substring(0, 10) ?: "",
                onValueChange = { newDate ->
                    val time = selectedDueIso?.substring(11) ?: "00:00"
                    selectedDueIso = if (newDate.isNotEmpty()) "$newDate'T'$time" else null
                },
                placeholder = { Text("Date") },
                modifier = Modifier.width(100.dp),
                colors =
                    TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                shape = RectangleShape,
                singleLine = true,
            )

            IconButton(
                onClick = { showDueDialog = true },
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    TablerAppWindow,
                    contentDescription = "Calendar picker",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Spacer(Modifier.width(4.dp))

            TextField(
                value = selectedDueIso?.substring(11) ?: "",
                onValueChange = { newTime: String ->
                    val date = selectedDueIso?.substring(0, 10) ?: "2026-07-15"
                    selectedDueIso = if (newTime.isNotEmpty()) "$date'T'$newTime" else null
                },
                placeholder = { Text("Time") },
                modifier = Modifier.width(80.dp),
                colors =
                    TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                shape = RectangleShape,
                singleLine = true,
            )

            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()

            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val trimmed = newText.trim()
                    if (trimmed.isNotEmpty()) {
                        val newItem = TaskItem(text = trimmed, dueDateTimeIso = selectedDueIso)
                        tasks = tasks + newItem
                        onTasksChanged(tasks)
                        newText = ""
                        selectedDueIso = null
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

        if (showDueDialog) {
            var tempDateStr by rememberSaveable {
                mutableStateOf(
                    selectedDueIso?.substring(0, 10) ?: "",
                )
            }
            var tempTimeStr by rememberSaveable {
                mutableStateOf(
                    selectedDueIso?.substring(11) ?: "",
                )
            }

            AlertDialog(
                onDismissRequest = { showDueDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedDueIso =
                            if (tempDateStr.isNotEmpty() && tempTimeStr.isNotEmpty()) {
                                "$tempDateStr'T'$tempTimeStr"
                            } else if (tempDateStr.isNotEmpty()) {
                                "$tempDateStr'T'00:00"
                            } else {
                                null
                            }
                        showDueDialog = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDueDialog = false
                    }) { Text("Cancel") }
                },
                text = {
                    Column {
                        Text("Enter due date and time")
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Date (yyyy-MM-dd):", style = MaterialTheme.typography.bodySmall)
                        TextField(
                            value = tempDateStr,
                            onValueChange = { tempDateStr = it },
                            placeholder = { Text("2026-07-15") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Time (HH:mm):", style = MaterialTheme.typography.bodySmall)
                        TextField(
                            value = tempTimeStr,
                            onValueChange = { tempTimeStr = it },
                            placeholder = { Text("14:30") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                },
            )
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

            Column {
                Text(item.text, style = MaterialTheme.typography.bodyLarge, color = textColor)
                if (!item.dueDateTimeIso.isNullOrEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Due: ${item.dueDateTimeIso.replace('T', ' ')}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
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
