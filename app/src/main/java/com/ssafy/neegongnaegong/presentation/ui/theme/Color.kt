package com.ssafy.neegongnaegong.presentation.ui.theme

import androidx.compose.ui.graphics.Color

data class NeeGongNaeGongColors(
    val primaryText: Color,
    val secondaryText: Color,
    val blue: Color,
    val mintBlue: Color,
    val mint: Color,
    val lightGreen: Color,
    val peach: Color,
    val gray4: Color,
    val gray3: Color,
    val gray2: Color,
    val gray1: Color,
    val background: Color
)

val LightColorScheme = NeeGongNaeGongColors(
    blue = Color(0xFF82ACCF),
    mintBlue = Color(0xFFB5E7E5),
    mint = Color(0xFFA5F7CB),
    lightGreen = Color(0xFFD2FC90),
    peach = Color(0xFFFFBBBB),
    primaryText = Color(0xFF222227),
    gray4 = Color(0xFF949494),
    gray3 = Color(0xFFD9D9D9),
    gray2 = Color(0xFFF4F4F4),
    gray1 = Color(0xFFF9F9F9),
    secondaryText = Color(0xFF949494),
    background = Color(0xFFFAFAFA)
)

val DarkColorScheme = NeeGongNaeGongColors(
    blue = Color(0xFF4A708E),
    mintBlue = Color(0xFF7FCBCB),
    mint = Color(0xFF6FCFA6),
    lightGreen = Color(0xFF9BBB5C),
    peach = Color(0xFFCC8888),
    primaryText = Color(0xFFEFEFEF),
    gray4 = Color(0xFF666666),
    gray3 = Color(0xFF555555),
    gray2 = Color(0xFF333333),
    gray1 = Color(0xFF222222),
    secondaryText = Color(0xFFAAAAAA),
    background = Color(0xFF121212)
)
