package com.ssafy.neegongnaegong.domain.model.learning

import kotlinx.serialization.Serializable

@Serializable
data class LearningRecord(
    val id: Long,
    val title: String,
    val content: String,
    val startTime: String,
    val endTime: String,
    val tags: List<String>
) {
    companion object {
        fun default(): LearningRecord = LearningRecord(
            id = 0,
            title = "",
            content = "",
            startTime = "2025-02-02T06:33:02.856Z",
            endTime = "2025-02-02T08:33:02.856Z",
            tags = emptyList()
        )
    }
}
