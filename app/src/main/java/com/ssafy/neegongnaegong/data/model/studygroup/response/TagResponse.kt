package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.domain.model.studygroup.TagInfo

data class TagResponse(
    val id: Long,
    val name: String
) {
    fun toTagInfo() =
        TagInfo(
            id = id,
            name = name
        )

}
