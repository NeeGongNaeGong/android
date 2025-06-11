package com.ssafy.neegongnaegong.data.model.studies.request

import java.time.LocalDateTime

data class CreateVoteRequest(
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val state: Boolean = true,
    val items: List<String>,
    val isMultiple: Boolean,
    val isSecret: Boolean,
    val isNotify: Boolean,
    val isChoose: Boolean,
)
