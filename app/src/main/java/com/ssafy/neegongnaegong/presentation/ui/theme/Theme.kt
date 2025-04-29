package com.ssafy.neegongnaegong.presentation.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeeGongNaeGongTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    CompositionLocalProvider(
        LocalRippleConfiguration provides null,
        LocalTypography provides NeeGongNaeGongTheme.typography,
        LocalColors provides colorScheme,
        LocalSpacing provides NeeGongNaeGongTheme.paddingScheme,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

val LocalTypography = staticCompositionLocalOf { Typography() }
val LocalColors = staticCompositionLocalOf { LightColorScheme }
val LocalSpacing = staticCompositionLocalOf { Spacing() }

object NeeGongNaeGongTheme {

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colorScheme: NeeGongNaeGongColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val paddingScheme: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current
}

