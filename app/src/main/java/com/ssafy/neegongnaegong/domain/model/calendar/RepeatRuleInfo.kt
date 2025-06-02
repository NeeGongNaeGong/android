package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class RepeatRuleInfo(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate?,
) {
    companion object {
        fun empty() = RepeatRuleInfo(
            repeatType = RepeatType.DAILY,
            repeatInterval = 1,
            repeatDay = 1,
            endDate = null,
        )
    }

    fun toDisplayString() = "${
        endDate?.let {
            DateTimeFormatter.ofPattern("yyyy년 M월 d일(E)까지 ").format(it)
        } ?: ""
    }$repeatInterval${repeatType.toDisplayString()} ${"${repeatDayToDisplayString()} "}반복"

    fun repeatDayToDisplayString() = when (repeatType) {
        RepeatType.DAILY -> ""
        RepeatType.WEEKLY -> {
            dayOfWeekOrder
                .filter {
                    repeatDay.isRepeatDaySelected(it.value)
                }
                .joinToString(separator = ", ") {
                    it.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                }
                .plus("요일")
        }

        RepeatType.MONTHLY -> {
            (1..31).filter { repeatDay.isRepeatDaySelected(it) }.ifEmpty { listOf("") }
                .joinToString(separator = ", ").plus("일")
        }
    }
}

fun Int.isRepeatDaySelected(value: Int): Boolean {
    return (this and (1 shl value)) != 0
}

fun Int.addRepeatDay(value: Int): Int {
    return this or (1 shl value)
}

fun Int.removeRepeatDay(value: Int): Int {
    val day = 1 shl value
    return if (this == day) this else this and day.inv()
}

fun Int.toggleRepeatDay(value: Int): Int {
    return if (isRepeatDaySelected(value)) {
        removeRepeatDay(value)
    } else {
        addRepeatDay(value)
    }
}
