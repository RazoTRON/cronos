package com.cronos.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue,
    onPrimary = TextBlack,
    primaryVariant = Blue,
    secondary = Red,
    onSecondary = TextWhite,
    background = LightBlack,
    onBackground = TextWhite,
    surface = DarkGray,
    onSurface = TextWhite,
    error = Red,
    onError = TextWhite
)

object ExtendedColors
{
    val high = Black
    val mediumHigh = LightBlack
    val mediumLow = DarkGray
    val low = Gray
    val textHigh = TextLightGray
    val textMedium = TextGray
    val textLow = TextDarkGray
}

@Composable
fun CronosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = DarkTypography,
        shapes = Shapes,
        content = content
    )
}