package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo

internal object StudyLogByTagInfoMapper {

    fun StudyLogByTagResponse.toDomain() = StudyLogByTagInfo(
        tagName = tagName,
        totalSeconds = totalSeconds
    )

    fun List<StudyLogByTagResponse>.toDomain() = map { it.toDomain() }
}
