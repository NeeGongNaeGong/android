package com.ssafy.neegongnaegong.data.model.studies.request

import java.time.LocalDateTime

data class CreateVoteRequest(
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val state: Boolean = true,
    val items: List<String>,
    val multiple: Boolean,
    val secret: Boolean,
    val notify: Boolean,
    val choose: Boolean,
)
