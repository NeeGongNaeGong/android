package com.ssafy.neegongnaegong.data.mapper.tag

import com.ssafy.neegongnaegong.data.mapper.tag.TagMapper.idToEnName
import com.ssafy.neegongnaegong.data.model.learningrecord.response.TagResponse
import com.ssafy.neegongnaegong.domain.model.learning.Tag

internal object LearningRecordTagMapper {
    fun TagResponse.toDomain() =
        Tag(
            id = id,
            koName = name,
            enName = idToEnName[id] ?: "English Name Not Found",
        )

    fun List<TagResponse>.toDomain() = map { it.toDomain() }
}
