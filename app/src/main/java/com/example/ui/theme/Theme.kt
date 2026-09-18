package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val JarvisColorScheme = darkColorScheme(
    primary = JarvisCyan,
    onPrimary = Color(0xFF041E24),
    primaryContainer = Color(0xFF0E3A4B),
    onPrimaryContainer = Color(0xFFC7F8FF),
    secondary = JarvisElectricBlue,
    onSecondary = Color(0xFF00344D),
    secondaryContainer = Color(0xFF1E3A5F),
    onSecondaryContainer = Color(0xFFD3E7FF),
    tertiary = JarvisPurple,
    onTertiary = Color(0xFF38104E),
    tertiaryContainer = Color(0xFF4C1D73),
    onTertiaryContainer = Color(0xFFF3D8FF),
    background = JarvisDarkBackground,
    onBackground = JarvisTextPrimary,
    surface = JarvisDarkSurface,
    onSurface = JarvisTextPrimary,
    surfaceVariant = JarvisDarkSurfaceVariant,
    onSurfaceVariant = JarvisTextSecondary,
    outline = JarvisDarkBorder,
    error = JarvisRuby,
    onError = Color.White
)

@Composable
fun JarvisAgentTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = JarvisColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) = JarvisAgentTheme(content = content)
