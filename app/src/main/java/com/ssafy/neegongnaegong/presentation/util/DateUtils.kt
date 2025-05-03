package com.ssafy.neegongnaegong.presentation.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * 날짜를 상대적인 시간으로 표시하는 유틸리티 메서드
 * - 예시
 *      - `방금`
 *      - `3일전`
 *      - `1년 전`
 */
fun getRelativeTimeString(dateTimeString: String): String =
    runCatching {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val now = LocalDateTime.now()

        val seconds = ChronoUnit.SECONDS.between(dateTime, now)
        TimeUnit.getRelativeTimeLabel(seconds)
    }.getOrElse {
        // 날짜 파싱에 실패한 경우 원본 문자열 반환
        dateTimeString
    }



enum class TimeUnit(
    val seconds: Long,
    val label: String,
) {
    MINUTE(60, "분"),
    HOUR(3600, "시간"),
    DAY(86400, "일"),
    WEEK(604800, "주"),
    MONTH(2592000, "개월"),
    YEAR(31536000, "년"),
    ;

    companion object {
        fun getRelativeTimeLabel(seconds: Long): String =
            when {
                seconds < MINUTE.seconds -> "방금"
                seconds < HOUR.seconds -> "${seconds / MINUTE.seconds}${MINUTE.label} 전"
                seconds < DAY.seconds -> "${seconds / HOUR.seconds}${HOUR.label} 전"
                seconds < WEEK.seconds -> "${seconds / DAY.seconds}${DAY.label} 전"
                seconds < MONTH.seconds -> "${seconds / WEEK.seconds}${WEEK.label} 전"
                seconds < YEAR.seconds -> "${seconds / MONTH.seconds}${MONTH.label} 전"
                else -> "${seconds / YEAR.seconds}${YEAR.label} 전"
            }
    }
}