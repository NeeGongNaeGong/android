package com.ssafy.neegongnaegong.data.mapper

import com.ssafy.neegongnaegong.data.model.learningrecord.response.TagResponse
import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.learning.Tag

object TagMapper {
    // id → 한글 이름
    val idToKoName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.koName }
    }

    // id → 영어 이름
    val idToEnName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.enName }
    }

    fun TagResponse.toDomain() = Tag(
        id = id,
        koName = name,
        enName = idToEnName[id] ?: "English Name Not Found",
    )
}
