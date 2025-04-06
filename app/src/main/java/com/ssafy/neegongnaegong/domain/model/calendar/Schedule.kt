package com.ssafy.neegongnaegong.domain.model.calendar

data class Schedule(
    val type: ScheduleType,
    val id: Long,
    val info: ScheduleInfo,
) {
    companion object {
        fun empty() = Schedule(
            type = ScheduleType.PERSONAL,
            id = 0L,
            info = ScheduleInfo.empty()
        )
    }
}
