package com.ssafy.neegongnaegong.data.mapper.tag

import com.ssafy.neegongnaegong.data.mapper.tag.TagMapper.idToEnName
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.studygroup.TagInfo

internal object TagInfoMapper {
    fun TagInfo.toDomain() =
        Tag(
            id = id,
            koName = name,
            enName = idToEnName[id] ?: "English Name Not Found",
        )

    fun List<TagInfo>.toDomain() = map { it.toDomain() }
}
