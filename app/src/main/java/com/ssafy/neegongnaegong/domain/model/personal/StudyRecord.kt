package com.ssafy.neegongnaegong.domain.model.personal

import kotlinx.serialization.Serializable

@Serializable
data class StudyRecord(
    val id: Long,
    val title: String,
    val content: String,
    val startTime: String,
    val endTime: String,
    val tags: List<String>
) {
    companion object {
        fun default(): StudyRecord = StudyRecord(
            id = 0,
            title = "",
            content = "",
            startTime = "2025-04-19T06:33:02.856Z",
            endTime = "2025-04-19T08:33:02.856Z",
            tags = emptyList()
        )
    }
}
