package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole

data class StudiesMember(
    val userId: Long,
    val name: String,
    val profileImg: String,
    val groupRole: StudiesMemberRole,
)
