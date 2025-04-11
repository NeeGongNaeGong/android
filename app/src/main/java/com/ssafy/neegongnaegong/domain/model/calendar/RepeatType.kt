package com.ssafy.neegongnaegong.domain.model.calendar

enum class RepeatType(val value: String) {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONHTLY");

    fun toDisplayString() = when (this) {
        DAILY -> "일마다"
        WEEKLY -> "주마다"
        MONTHLY -> "개월마다"
    }
}
