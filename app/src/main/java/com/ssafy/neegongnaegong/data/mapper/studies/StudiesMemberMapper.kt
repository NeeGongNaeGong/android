package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.model.studies.response.GetMemberResponse
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole

internal object StudiesMemberMapper {
    fun GetMemberResponse.toDomain() =
        StudiesMember(
            userId = userId,
            name = name,
            profileImg = profileImg,
            groupRole = StudiesMemberRole.valueOf(groupRole),
        )

    fun List<GetMemberResponse>.toDomain() = map { it.toDomain() }
}
