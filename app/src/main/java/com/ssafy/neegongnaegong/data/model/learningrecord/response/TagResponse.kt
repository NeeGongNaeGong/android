package com.ssafy.neegongnaegong.data.model.learningrecord.response

import com.ssafy.neegongnaegong.domain.model.learning.Tag

data class TagResponse(
    val id: Long,
    val name: String,
) {
    fun toDomain(): Tag =
        Tag(
            id = this.id,
            koName = this.name,
            enName = this.name,
        )
}
