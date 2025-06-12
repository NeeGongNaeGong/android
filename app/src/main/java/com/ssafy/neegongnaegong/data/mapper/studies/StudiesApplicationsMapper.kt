package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesApplicationsMembersResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesApplicationsMemberResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesApplications
import com.ssafy.neegongnaegong.domain.model.studies.StudiesApplicationsMember
import com.ssafy.neegongnaegong.presentation.group.join.component.StudiesJoinApplicationStatus

internal object StudiesApplicationsMapper {
    fun StudiesApplicationsMemberResponse.toDomain() =
        StudiesApplicationsMember(
            status = StudiesJoinApplicationStatus.PENDING,
            memberId = memberId,
            userId = userId,
            name = name,
            profileImageUrl = profileImageUrl,
            cursorId = cursorId,
        )

    fun GetStudiesApplicationsMembersResponse.toDomain() =
        CursorStudiesApplications(
            content = content.map { it.toDomain() },
            hasNext = hasNext,
            cursorId = cursorId,
        )
}
