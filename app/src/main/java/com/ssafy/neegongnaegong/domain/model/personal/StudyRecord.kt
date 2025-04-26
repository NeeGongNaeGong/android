package com.ssafy.neegongnaegong.domain.model.personal

import kotlinx.serialization.Serializable

@Serializable
data class StudyRecord(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val startTime: String = "2025-04-19T06:33:02.856Z",
    val endTime: String = "2025-04-19T08:33:02.856Z",
    val tags: List<String> = emptyList(),
)

