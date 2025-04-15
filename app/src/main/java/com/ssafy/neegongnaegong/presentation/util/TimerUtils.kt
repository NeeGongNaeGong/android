package com.ssafy.neegongnaegong.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.util.Locale

fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = (elapsedTime / (1000 * 60 * 60)) % 24

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}


fun String.toDateString(): String {
    return try {
        val instant = Instant.parse(this)
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        "%04d.%02d.%02d".format(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    } catch (e: Exception) {
        "잘못된 날짜 형식"
    }
}

fun String.toTimeString(): String {
    return try {
        val instant = Instant.parse(this)
        val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
        val hour = localTime.hour
        val minute = localTime.minute

        val isAm = hour < 12
        val hour12 = if (hour % 12 == 0) 12 else hour % 12
        val amPm = if (isAm) "오전" else "오후"

        "$amPm ${hour12}시 ${minute}분"
    } catch (e: Exception) {
        "잘못된 날짜 형식"
    }
}


