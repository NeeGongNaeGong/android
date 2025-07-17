package com.ssafy.neegongnaegong.presentation.util

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object CustomDateTimeFormatter {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E)")
    private val timeFormatter = DateTimeFormatter.ofPattern("a hh:mm")

    /**
     * LocalDateTime.now 등으로 시간을 얻고
     * 그걸 인자로 넣으면 yyyy년 MM월 dd일 (요일) 꼴로 반환해주는 함수
     * @param LocalDateTime
     * @return String - yyyy년 MM월 dd일 (요일)
     */
    fun convertLocalDateTimeToStringDate(time: LocalDateTime): String = time.atZone(ZoneId.systemDefault()).format(dateFormatter)

    /**
     * LocalDateTime.now 등으로 시간을 얻고
     * 그걸 인자로 넣으면 yyyy년 MM월 dd일 (요일) 꼴로 반환해주는 함수
     * @param LocalDateTime
     * @return String - yyyy년 MM월 dd일 (요일)
     */
    fun convertLocalDateTimeToStringTime(time: LocalDateTime): String = time.atZone(ZoneId.systemDefault()).format(timeFormatter)

    /**
     * yyyy년 MM월 dd일 (E) 꼴의 문자열을 인자로 넣으면 밀리초로 변환하는 함수
     * @param String - yyyy년 MM월 dd일 (E) 꼴
     * @return Long - 밀리초 값
     */
    fun convertStringDateToMillis(dateString: String): Long {
        val dateTime = LocalDate.parse(dateString, dateFormatter)

        // LocalDateTime을 ZonedDateTime으로 변환하여 시간대 정보 추가
        val zonedDateTime = dateTime.atStartOfDay(ZoneId.systemDefault())

        // Instant로 변환 후 밀리초 값 반환
        return zonedDateTime.toInstant().toEpochMilli()
    }

    /**
     * 오전/오후 hh:mm 꼴로 문자열을 넣으면
     * 시간과 분을 반환해주는 함수
     * @param String a hh:mm 꼴
     * @return List<Int> - 시간, 분
     */
    fun convertStringTimeToHourAndMinute(timeString: String): List<Int> {
        // 문자열을 LocalTime으로 파싱
        val localTime = LocalTime.parse(timeString, timeFormatter)

        // 시간(0-23)과 분(0-59) 추출
        val hour = localTime.hour
        val minute = localTime.minute
        return listOf(hour, minute)
    }

    fun convertLongToDateString(millis: Long): String {
        val instant = Instant.ofEpochMilli(millis)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return localDateTime.format(dateFormatter)
    }

    /**
     * 시간과 날짜를 주면 a hh:mm 꼴의 문자열로 반환해주는 함수
     * @param Int 시간
     * @param Int 분
     * @return String a hh:mm 꼴의 문자열
     */
    fun convertHourMinuteToAmPmFormat(
        hour: Int,
        minute: Int,
    ): String {
        // 날짜는 임의로 설정 (시간 형식만 중요하므로)
        val localDateTime = LocalDateTime.of(2025, 1, 1, hour, minute, 0)
        return localDateTime.format(timeFormatter)
    }

    /**
     * yyyy년 MM월 dd일 (E) 꼴의 문자열과 a hh:mm 꼴의 문자열을 받아서
     * 2025-05-09T03:58:41.360 이런 꼴의 LocalDateTime을 반환하는 함수
     * @param String yyyy년 MM월 dd일 (E)
     * @param String a hh:mm
     * @return String 2025-05-09T03:58:41.360
     */
    fun convertStringToLocalDateTime(
        dateStr: String,
        timeStr: String,
    ): LocalDateTime {
        // 날짜는 임의로 설정 (시간 형식만 중요하므로)
        val date = LocalDate.parse(dateStr, dateFormatter)
        val time = LocalTime.parse(timeStr, timeFormatter)
        val dateTime = LocalDateTime.of(date, time)
        return dateTime
    }

    /** DateDialog의 경우 UTC로 값을 받아서 그렇게 변환하기 위한 함수
     * @param LocalDateTime 기준의 Long 값
     * @return UTC 기준의 Long 값
     * Local 기준 5월 8일을 UTC 기준 5월8일에 해당하는 millis로 반환한다.
     */
    fun millisToUtc(time: Long): Long {
        val zoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
        val offsetMillis = zoneOffset.totalSeconds * 1000L

        return time + offsetMillis
    }

    /**
     * 초로 들어온 기간을 00H00M으로 바꾸기 위한 함수
     * @param Long 기간
     * @return String 00H00M 꼴
     */
    @SuppressLint("DefaultLocale")
    fun formatDurationToHM(seconds: Long): String {
        val totalMinutes = seconds / 60
        val totalHours = totalMinutes / 60
        val days = totalHours / 24
        val hours = totalHours % 24
        val minutes = totalMinutes % 60

        return if (days > 0) {
            String.format(
                Locale.getDefault(),
                "%02dD%02dH%02dM",
                days,
                hours,
                minutes,
            )
        } else if (hours > 0) {
            String.format(
                Locale.getDefault(),
                "%02dH%02dM",
                hours,
                minutes,
            )
        } else if (minutes > 0) {
            String.format(Locale.getDefault(), "%02dM", minutes)
        } else {
            String.format(Locale.getDefault(), "%02dS", seconds)
        }
    }
}
