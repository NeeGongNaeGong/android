package com.ssafy.neegongnaegong.domain.model.calendar

enum class DeleteType(value: String) {
    ALL("ALL"),
    DAILY("DAILY"),
    AFTER("AFTER");
}

fun DeleteType.toDisplayText() = when(this) {
    DeleteType.ALL -> "연관된 모든 일정을 삭제"
    DeleteType.DAILY -> "이 일정을 삭제"
    DeleteType.AFTER -> "이 일정과 이후 반복 일정 모두 삭제"
}
