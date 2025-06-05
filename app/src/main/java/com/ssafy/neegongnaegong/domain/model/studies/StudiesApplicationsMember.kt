package com.ssafy.neegongnaegong.domain.model.studies

data class StudiesApplicationsMember(
    val memberId: Long,
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val cursorId: Long,
)
