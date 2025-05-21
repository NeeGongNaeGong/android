package com.ssafy.neegongnaegong.presentation.util

import java.time.Instant
import java.time.LocalDateTime
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
 * @return
 */
fun LocalDateTime.toDateString(): String {
    return "%04d.%02d.%02d".format(year, monthValue, dayOfMonth)
}

/**
 * ISO 8691 2025-05-14T04:33:02.856Z 같은 시간을 '오전 8시 30분' 같은 형식으로 리턴해줍니다.
 * @return
 */
fun LocalDateTime.toTimeString(): String {
    val hour = this.hour
    val minute = this.minute

    val isAm = hour < 12
    val hour12 = if (hour % 12 == 0) 12 else hour % 12
    val amPm = if (isAm) "오전" else "오후"

    return "$amPm ${hour12}시 ${minute}분"
}

/**
 * IOS 8601 시간을 00시 00분 으로 리턴해줍니다.
 * @return
 */
fun LocalDateTime.toHourMinuteString(): String {
    val hour = this.hour
    val minute = this.minute
    val hour12 = if (hour % 12 == 0) 12 else hour % 12

    return "${hour12}시 ${minute}분"
}
