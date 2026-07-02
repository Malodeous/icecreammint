package xyz.malefic.icecreammint

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import xyz.malefic.icecreammint.screens.DemoScreen
import xyz.malefic.icecreammint.screens.HomeScreen
import xyz.malefic.icecreammint.screens.SettingsScreen
import xyz.malefic.icecreammint.theme.GlobalColor
import xyz.malefic.icecreammint.theme.GlobalText

@Composable
fun App(
    component: RootComponent,
    colorScheme: ColorScheme? = null,
) {
    val childStack by component.stack.subscribeAsState()
    val activeScreen = childStack.active.instance
    val colors = colorScheme ?: if (isSystemInDarkTheme()) GlobalColor.darkScheme else GlobalColor.lightScheme

    LaunchedEffect(activeScreen) {
        println("activeScreen changed: $activeScreen")
    }

    MaterialTheme(colorScheme = colors, typography = GlobalText.typography) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(width = 2.dp, color = MaterialTheme.colorScheme.onPrimary),
            )
            {
                Text(
                    "Ice Cream Mint",
                    Modifier.align(Alignment.TopCenter).padding(all = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(modifier = Modifier.align(Alignment.TopStart), onClick = {
                    component.navigateTo(RootComponent.Screen.Home)
                }) {
                    Icon(
                        RootComponent.Screen.Home.icon,
                        RootComponent.Screen.Home.title,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                SimpleDropdownMenu(component, Modifier.align(Alignment.TopEnd))
            }
            Box(
                Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                when (activeScreen) {
                    RootComponent.Screen.Home -> HomeScreen()
                    RootComponent.Screen.Demo -> DemoScreen()
                    RootComponent.Screen.Settings -> SettingsScreen()
                }
            }
        }
    }
}

@Composable
fun SimpleDropdownMenu(
    component: RootComponent,
    modifier: Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPage by remember { mutableStateOf("Home") }
    Box(modifier) {
        Button(
            onClick = { expanded = true },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(selectedPage, style = MaterialTheme.typography.labelLarge)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            component.topLevelScreens.forEach { page ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                page.icon,
                                page.title,
                                Modifier.padding(end = 8.dp),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                            Text(page.title, style = MaterialTheme.typography.bodyMedium)
                        }
                    },
                    onClick = {
                        component.navigateTo(page)
                        selectedPage = page.title
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        leadingIconColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}
