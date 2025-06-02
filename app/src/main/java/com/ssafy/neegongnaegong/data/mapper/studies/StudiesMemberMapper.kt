package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.model.studies.response.GetMemberResponse
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember

internal object StudiesMemberMapper {
    fun GetMemberResponse.toDomain() =
        StudiesMember(
            userId = userId,
            name = name,
            profileImg = profileImg,
            groupRole = groupRole,
        )

    fun List<GetMemberResponse>.toDomain() = map { it.toDomain() }
}
