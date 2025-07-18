package com.ssafy.neegongnaegong.presentation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R

val fontFamily =
    FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_regular, FontWeight.Normal),
    )
val letterSpacing = (-0.06).em

data class Typography(
    val titleLarge: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val titleMedium: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val titleSmall: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val bodyLarge: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val bodyMedium: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val bodySmall: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val labelLarge: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val labelMedium: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
    val labelSmall: TextStyle =
        TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = letterSpacing,
            fontFeatureSettings = "tnum",
        ),
)
