package com.ssafy.neegongnaegong.data.mapper.tag

import com.ssafy.neegongnaegong.data.model.studygroup.response.TagResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.TagInfo

internal object StudyTagMapper {
    fun TagResponse.toDomain() =
        TagInfo(
            id = id,
            name = name,
        )

    fun List<TagResponse>.toDomain() = map { it.toDomain() }
}
