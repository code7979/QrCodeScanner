package com.abhiket.qrcodescanner.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimary,
    secondary = ColorSecondary,
    tertiary = ColorTertiary
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    secondary = ColorSecondary,
    tertiary = ColorTertiary,
    background = ColorWhite,
    surface = ColorWhite,
    onPrimary = ColorWhite,
    onSecondary = ColorWhite,
    onTertiary = ColorWhite,
    onBackground = ColorBlack,
    onSurface = ColorBlack,
)

@Composable
fun QrCodeScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set color of system statusBar same as ActionBar
            window.statusBarColor = ColorLiteGray.toArgb()
            // Set color of system navigationBar same as BottomNavigationView
            window.navigationBarColor = ColorLiteGray.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}