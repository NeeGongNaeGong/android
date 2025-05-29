package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDate

data class VoteHistoryInfo(
    val title: String,
    val endTime: LocalDate?,
    val participationMember: Int,
    val voted: Boolean,
)
