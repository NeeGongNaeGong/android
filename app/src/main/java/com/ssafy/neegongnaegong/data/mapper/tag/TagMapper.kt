package com.ssafy.neegongnaegong.data.mapper.tag

import com.ssafy.neegongnaegong.domain.data.TagData

object TagMapper {
    // id → 한글 이름
    val idToKoName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.koName }
    }

    // id → 영어 이름
    val idToEnName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.enName }
    }
}
