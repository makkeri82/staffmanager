package com.example.staffmanager.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val LightColorTheme = lightColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceContainerLowest = SurfaceLowest,
    background = Background,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant
)

val DarkColorTheme = darkColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceContainerLowest = SurfaceLowest,
    background = Background,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant
)

@Composable
fun EventAppTheme(
    content: @Composable () -> Unit
) {
    val theme = if (isSystemInDarkTheme()) {
        DarkColorTheme
    } else {
        LightColorTheme
    }
    MaterialTheme(
        colorScheme = LightColorTheme,
        typography = sespTypography(),
        content = content
    )
}