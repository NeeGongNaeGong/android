package com.ssafy.neegongnaegong.presentation.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


private val LightColorScheme = lightColorScheme(
    primary = LightColors.Blue,
    secondary = LightColors.MintBlue,
    background = LightColors.BackGround,
    surface = LightColors.White,
    onPrimary = LightColors.White,
    onSecondary = LightColors.Black,
    onBackground = LightColors.Black,
    onSurface = LightColors.Black,
    primaryContainer = LightColors.Gray1,
    secondaryContainer = LightColors.Gray4,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkColors.DarkBlue,
    secondary = DarkColors.DarkMintBlue,
    background = DarkColors.DarkBackGround,
    surface = DarkColors.DarkBlack,
    onPrimary = DarkColors.DarkWhite,
    onSecondary = DarkColors.DarkGray4,
    onBackground = DarkColors.DarkWhite,
    onSurface = DarkColors.DarkWhite,
    primaryContainer = DarkColors.DarkGray1,
    secondaryContainer = DarkColors.DarkGray4,
)

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000
)
annotation class NeeGongNaeGongPreviews

@Composable
fun NeeGongNaeGongTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
