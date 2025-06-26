package com.ssafy.neegongnaegong.domain.model.studies

data class WeeklyRankingsMember(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val studyTimeSeconds: Long,
)
