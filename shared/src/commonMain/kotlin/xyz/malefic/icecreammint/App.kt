package xyz.malefic.icecreammint

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import xyz.malefic.icecreammint.screens.DemoScreen
import xyz.malefic.icecreammint.screens.HomeScreen
import xyz.malefic.icecreammint.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    component: RootComponent,
    colorScheme: ColorScheme? = null,
) {
    val childStack by component.stack.subscribeAsState()
    val activeScreen = childStack.active.instance

    val appColorScheme =
        colorScheme
            ?: if (isSystemInDarkTheme()) {
                darkColorScheme(
                    background = Color(214, 189, 255, 50),
                    primary = Color(214, 189, 255, 90),
                    onBackground = Color(0, 0, 0, 255),
                )
            } else {
                lightColorScheme(
                    background = Color(178, 131, 255, 50),
                    primary = Color(178, 131, 255, 90),
                    onBackground = Color(0, 0, 0, 255),
                )
            }

    MaterialTheme(
        colorScheme = appColorScheme,
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                title = {
                    Text("Ice Cream Mint", color = MaterialTheme.colorScheme.onBackground, fontFamily = FontFamily.Serif)
                },
                actions = {
                    component.topLevelScreens.forEach { screen ->
                        TextButton(onClick = {
                            component.navigateTo(screen)
                        }) {
                            Icon(screen.icon, screen.title, tint = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                },
                colors =
                    TopAppBarColors(
                        containerColor = appColorScheme.primary,
                        titleContentColor = appColorScheme.onPrimary,
                        actionIconContentColor = appColorScheme.onPrimary,
                        scrolledContainerColor = appColorScheme.primary,
                        navigationIconContentColor = appColorScheme.onPrimary,
                        subtitleContentColor = appColorScheme.onPrimary,
                    ),
            )
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
                when (activeScreen) {
                    RootComponent.Screen.Home -> HomeScreen()
                    RootComponent.Screen.Demo -> DemoScreen()
                    RootComponent.Screen.Settings -> SettingsScreen()
                }
            }
        }
    }
}
