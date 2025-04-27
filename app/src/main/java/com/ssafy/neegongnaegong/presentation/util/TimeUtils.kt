package com.ssafy.neegongnaegong.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.util.Locale

/**
 * SystemClock.elapsedRealtime() 같은 Long 형 시간을 23:01:23 처럼 시,분,초 텍스트로 바꾸어줍니다.
 *
 * @param elapsedTime
 * @return 23:01:23
 */
fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = (elapsedTime / (1000 * 60 * 60)) % 24

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}


/**
 * ISO 8691 2025-05-14T04:33:02.856Z 같은 시간을 2025년 05월 14일 같은 String로 바꾸어줍니다.
 * 단 형식에 맞지 않는 문자열이 온다면 "잘못된 날짜 형식"을 리턴합니다.
 * @return
 */
fun String.toDateString(): String {
    return try {
        val instant = Instant.parse(this)
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        "%04d.%02d.%02d".format(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    } catch (e: Exception) {
        "잘못된 날짜 형식"
    }
}

/**
 * ISO 8691 2025-05-14T04:33:02.856Z 같은 시간을 '오전 8시 30분' 같은 형식으로 리턴해줍니다.
 * 단 형식에 맞지 않는 문자열이 온다면 "잘못된 날짜 형식"을 리턴합니다.
 * @return
 */
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

/**
 * IOS 8601 시간을 00시 00분 으로 리턴해줍니다.
 * 형식에 맞지 않는 문자열은 "잘못된 시간 형식" 을 리턴합니다.
 * @return
 */
fun String.toHourMinuteString(): String {
    return try {
        val instant = Instant.parse(this)
        val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
        val hour = localTime.hour
        val minute = localTime.minute

        val hour12 = if (hour % 12 == 0) 12 else hour % 12

        "${hour12}시 ${minute}분"
    } catch (e: Exception) {
        "잘못된 시간 형식"
    }
}

