package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class VoteHistoryInfo(
    val id: Long,
    val title: String,
    val endTime: LocalDateTime?,
    val participationMember: Int,
    val voted: Boolean,
)
