package com.ssafy.neegongnaegong.domain.model.studygroup

import com.ssafy.neegongnaegong.data.mapper.TagMapper
import com.ssafy.neegongnaegong.domain.model.learning.Tag

data class TagInfo(
    val id: Long,
    val name: String,
)

fun TagInfo.toDomain(): Tag =
    Tag(
        id = this.id,
        koName = this.name,
        enName = TagMapper.idToEnName[this.id] ?: "English Name Not Found",
    )
