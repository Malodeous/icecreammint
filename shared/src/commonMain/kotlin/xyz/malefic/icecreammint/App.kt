package xyz.malefic.icecreammint

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import xyz.malefic.icecreammint.screens.DemoScreen
import xyz.malefic.icecreammint.screens.HomeScreen
import xyz.malefic.icecreammint.screens.SettingsScreen
import xyz.malefic.icecreammint.screens.ToDoScreen
import xyz.malefic.icecreammint.theme.GlobalColor
import xyz.malefic.icecreammint.theme.GlobalText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    component: RootComponent,
    colorScheme: ColorScheme? = null,
) {
    val childStack by component.stack.subscribeAsState()
    val activeScreen = childStack.active.instance
    val selectedPage = remember { mutableStateOf("Home") }

    LaunchedEffect(activeScreen) {
        println("activeScreen changed: $activeScreen")
    }

    val appColorScheme =
        colorScheme
            ?: if (isSystemInDarkTheme()) {
                GlobalColor.darkScheme
            } else {
                GlobalColor.lightScheme
            }

    MaterialTheme(
        colorScheme = appColorScheme,
        typography = GlobalText.typography,
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(appColorScheme.primary)
                    .border(width = 2.dp, color = appColorScheme.onPrimary),
            )
            {
                Text(
                    "Ice Cream Mint",
                    modifier = Modifier.align(Alignment.TopCenter).padding(all = 12.dp),
                    fontSize = 20.sp,
                    color = appColorScheme.onPrimary,
                    fontFamily = FontFamily.Serif,
                )
                TextButton(modifier = Modifier.align(Alignment.TopStart), onClick = {
                    selectedPage.value = RootComponent.Screen.Home.title
                    component.navigateTo(RootComponent.Screen.Home)
                }) {
                    Icon(
                        RootComponent.Screen.Home.icon,
                        RootComponent.Screen.Home.title,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
                component.SimpleDropdownMenu(selectedPage, Modifier.align(Alignment.TopEnd))
            }
            Box(
                Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                when (activeScreen) {
                    RootComponent.Screen.Home -> HomeScreen()
                    RootComponent.Screen.Demo -> DemoScreen()
                    RootComponent.Screen.Settings -> SettingsScreen()
                    RootComponent.Screen.ToDo -> ToDoScreen()
                }
            }
        }
    }
}

@Composable
fun RootComponent.SimpleDropdownMenu(
    selectedPage: MutableState<String>,
    modifier: Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val pages = listOf("Home", "Demo", "Settings", "ToDo")
    Box(
        modifier.background(MaterialTheme.colorScheme.primary),
    ) {
        Button(
            onClick = { expanded = true },
            modifier.background(MaterialTheme.colorScheme.primary),
        ) {
            Text(selectedPage.value)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.secondary,
        ) {
            pages.forEach { page ->
                DropdownMenuItem(
                    text = {
                        Text(
                            page,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            RootComponent.Screen.valueOf(page).icon,
                            RootComponent.Screen.valueOf(page).title,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    onClick = {
                        val screen = RootComponent.Screen.valueOf(page)
                        navigateTo(screen)
                        selectedPage.value = page
                        expanded = false
                    },
                )
            }
        }
    }
}
