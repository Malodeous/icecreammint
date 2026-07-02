package xyz.malefic.icecreammint.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object GlobalColor {
    // Bubblegum Palette
    private val Pink = Color(243, 162, 190)
    private val LightPink = Color(255, 211, 211)
    private val LightestMint = Color(240, 249, 248)
    private val LightMint = Color(198, 230, 227)
    private val DarkMint = Color(129, 191, 183)

    // Additional Colors
    private val ErrorRed = Color(186, 26, 26)
    private val ErrorRedDark = Color(255, 180, 171)
    private val OutlineGray = Color(112, 121, 119)
    private val OutlineGrayDark = Color(137, 147, 145)

    // Text Colors
    private val DarkText = Color(40, 50, 50) // Dark grayish mint
    private val LightText = Color(240, 249, 248) // Lightest mint

    val lightScheme =
        lightColorScheme(
            primary = DarkMint,
            onPrimary = Color.Black,
            primaryContainer = LightMint,
            onPrimaryContainer = Color.Black,
            secondary = Pink,
            onSecondary = Color.Black,
            secondaryContainer = LightPink,
            onSecondaryContainer = Color.Black,
            background = LightestMint,
            onBackground = DarkText,
            surface = LightestMint,
            onSurface = DarkText,
            error = ErrorRed,
            onError = Color.White,
            outline = OutlineGray,
            surfaceVariant = LightMint,
            onSurfaceVariant = DarkText,
        )

    val darkScheme =
        darkColorScheme(
            primary = LightMint,
            onPrimary = Color.Black,
            primaryContainer = DarkMint,
            onPrimaryContainer = Color.White,
            secondary = LightPink,
            onSecondary = Color.Black,
            secondaryContainer = Pink,
            onSecondaryContainer = Color.White,
            background = Color(28, 31, 31),
            onBackground = LightText,
            surface = Color(28, 31, 31),
            onSurface = LightText,
            error = ErrorRedDark,
            onError = Color(105, 0, 5),
            outline = OutlineGrayDark,
            surfaceVariant = Color(63, 73, 71),
            onSurfaceVariant = LightMint,
        )
}
