package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class RepeatRuleInfo(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate?,
) {
    fun toDisplayString() = "${endDate?.let { DateTimeFormatter.ofPattern("yyyy년 M월 d일(E)까지 ").format(it) } ?: ""}$repeatInterval${repeatType.toDisplayString()} ${"${repeatDayToDisplayString()} "}반복"

    fun repeatDayToDisplayString() = when (repeatType) {
        RepeatType.DAILY -> ""
        RepeatType.WEEKLY -> {
            listOf("월", "화", "수", "목", "금", "토", "일").filterIndexed { index, _ -> (repeatDay and (1 shl index)) != 0 }.joinToString(separator = ", ").plus("요일")
        }
        RepeatType.MONTHLY -> {
            (1..31).filterIndexed { index, _ -> (repeatDay and (1 shl index)) != 0 }.ifEmpty { listOf("") }.joinToString(separator = ", ").plus("일")
        }
    }
}
