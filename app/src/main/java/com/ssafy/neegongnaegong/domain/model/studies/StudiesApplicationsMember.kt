package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.presentation.group.join.component.StudiesJoinApplicationStatus

data class StudiesApplicationsMember(
    val status: StudiesJoinApplicationStatus,
    val memberId: Long,
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val cursorId: Long,
)
