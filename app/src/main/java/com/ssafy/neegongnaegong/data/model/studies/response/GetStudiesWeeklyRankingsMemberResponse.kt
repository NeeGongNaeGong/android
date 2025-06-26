package com.ssafy.neegongnaegong.data.model.studies.response

data class GetStudiesWeeklyRankingsMemberResponse(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val studyTimeSeconds: Long,
)
