package com.ssafy.neegongnaegong.domain.model.personal

data class StudyRecord(
    val title: String,
    val content: String,
    val startTime: String,
    val endTime: String,
    val tags: List<String>,
)
