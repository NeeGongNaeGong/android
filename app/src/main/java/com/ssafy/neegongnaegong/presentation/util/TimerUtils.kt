package com.ssafy.neegongnaegong.presentation.util

import java.util.Locale

fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = (elapsedTime / (1000 * 60 * 60)) % 24

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}