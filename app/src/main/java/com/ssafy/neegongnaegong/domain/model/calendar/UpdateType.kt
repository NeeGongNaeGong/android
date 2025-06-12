package com.ssafy.neegongnaegong.domain.model.calendar

enum class UpdateType(value: String) {
    ALL("ALL"),
    DAILY("DAILY"),
    AFTER("AFTER"),
}

fun UpdateType.toDisplayText() =
    when (this) {
        UpdateType.ALL -> "연관된 모든 일정을 수정"
        UpdateType.DAILY -> "이 일정을 수정"
        UpdateType.AFTER -> "이 일정과 이후 반복 일정 모두 수정"
    }
