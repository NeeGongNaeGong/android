package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.mapper.cursor.CursorMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo

internal object StudiesMapper {
    fun StudiesResponse.toDomain(): Studies =
        Studies(
            id = id,
            leader = leader,
            currentMembers = currentMembers,
            createdDate = createdDate.toString(),
            studyInfo =
                StudyInfo(
                    name = name,
                    maxMembers = maxMembers,
                    description = description,
                    profileImg = profileImg,
                    isPublic = isPublic,
                    targetStudyTime = targetStudyTime,
                    category = category,
                    tags = tags,
                ),
        )

    fun CursorSliceStudiesListResponse.toDomain() =
        CursorStudiesPage(
            content = content.map { it.toDomain() },
            hasNext = hasNext,
            nextCursor = nextCursor.toDomain(),
        )
}
