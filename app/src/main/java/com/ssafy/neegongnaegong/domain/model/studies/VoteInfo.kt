package com.ssafy.neegongnaegong.domain.model.studies

import java.time.LocalDateTime

data class VoteInfo(
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val state: Boolean,
    val items: List<String>,
    val multiple: Boolean,
    val secret: Boolean,
    val notify: Boolean,
    val choose: Boolean,
)
