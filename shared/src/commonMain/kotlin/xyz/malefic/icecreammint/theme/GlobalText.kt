package xyz.malefic.icecreammint.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

object GlobalText {
    val typography = Typography(
        displayLarge = TextStyle(fontFamily = FontFamily.Serif, fontSize = 57.sp),
        displayMedium = TextStyle(fontFamily = FontFamily.Serif, fontSize = 45.sp),
        displaySmall = TextStyle(fontFamily = FontFamily.Serif, fontSize = 36.sp),
        headlineLarge = TextStyle(fontFamily = FontFamily.Serif, fontSize = 32.sp),
        headlineMedium = TextStyle(fontFamily = FontFamily.Serif, fontSize = 28.sp),
        headlineSmall = TextStyle(fontFamily = FontFamily.Serif, fontSize = 24.sp),
        titleLarge = TextStyle(fontFamily = FontFamily.Serif, fontSize = 22.sp),
        titleMedium = TextStyle(fontFamily = FontFamily.Serif, fontSize = 16.sp),
        titleSmall = TextStyle(fontFamily = FontFamily.Serif, fontSize = 14.sp),
        bodyLarge = TextStyle(fontFamily = FontFamily.Serif, fontSize = 16.sp),
        bodyMedium = TextStyle(fontFamily = FontFamily.Serif, fontSize = 14.sp),
        bodySmall = TextStyle(fontFamily = FontFamily.Serif, fontSize = 12.sp),
        labelLarge = TextStyle(fontFamily = FontFamily.Serif, fontSize = 14.sp),
        labelMedium = TextStyle(fontFamily = FontFamily.Serif, fontSize = 12.sp),
        labelSmall = TextStyle(fontFamily = FontFamily.Serif, fontSize = 11.sp)
    )
}
