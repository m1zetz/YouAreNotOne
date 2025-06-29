package com.example.youarenotalone.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = pinkLight,
    secondary = pinkDark,
    tertiary = black,
    background = blueDark,
    surfaceContainerLowest = pinkLight,

    surfaceContainer = grayPurple,
    onSurface = grayPurpleDark,
    surfaceVariant = white,
    surfaceTint = grayPurpleLight,
    surfaceBright = grayPurpleDarkDark

)

private val LightColorScheme = lightColorScheme(
    primary = pinkLightLight,
    secondary = pinkLightLight,
    tertiary = blueLight,
    background = white,
    surfaceContainerLowest = pinkLight,

    surfaceContainer = _grayPurple,
    onSurface = _grayPurpleDark,
    surfaceVariant = white,
    surfaceTint = _grayPurpleLight,
    surfaceBright = _grayPurpleDarkDark

)

@Composable
fun YouAreNotAloneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}