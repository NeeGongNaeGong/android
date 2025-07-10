package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupDetailResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupDetailInfo

internal object StudyGroupDetailMapper {
    fun StudyGroupDetailResponse.toDomain() =
        StudyGroupDetailInfo(
            id = id,
            name = name,
            description = description,
            profileImg = profileImg,
            isPublic = isPublic,
            maxMembers = maxMembers,
            currentMembers = currentMembers,
            targetStudyTime = targetStudyTime,
            category = category.toDomain(),
            leaderId = leaderId,
            leaderName = leaderName,
            createdDate = createdDate,
            tags = tags.toDomain(),
            myGroupRole = myGroupRole,
        )

    fun StudyGroupDetailResponse.Category.toDomain() = StudyGroupDetailInfo.Category(id = id, name = name)

    fun StudyGroupDetailResponse.Tag.toDomain() = StudyGroupDetailInfo.Tag(id = id, name = name)

    fun List<StudyGroupDetailResponse.Tag>.toDomain() = map { it.toDomain() }
}
