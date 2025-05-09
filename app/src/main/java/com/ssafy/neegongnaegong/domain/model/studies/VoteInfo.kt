package com.ssafy.neegongnaegong.domain.model.studies

data class VoteInfo(
    val title: String,
    val startTime: String,
    val endTime: String?,
    val state: Boolean,
    val items: List<String>,
    val multiple: Boolean,
    val secret: Boolean
)
