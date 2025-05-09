package com.ssafy.neegongnaegong.data.model.studies.request

data class CreateVoteRequest(
    val title: String,
    val startTime: String,
    val endTime: String?,
    val state: Boolean = true,
    val items: List<String>,
    val multiple: Boolean,
    val secret: Boolean
)
