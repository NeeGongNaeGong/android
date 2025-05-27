package com.ssafy.neegongnaegong.data.mapper

import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.studygroup.TagInfo

typealias StudyTag = com.ssafy.neegongnaegong.data.model.studygroup.response.TagResponse
typealias LearningRecordTag = com.ssafy.neegongnaegong.data.model.learningrecord.response.TagResponse

object TagMapper {

    // id → 한글 이름
    val idToKoName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.koName }
    }

    // id → 영어 이름
    val idToEnName: Map<Long, String> by lazy {
        TagData.tags.associate { it.id to it.enName }
    }

    fun LearningRecordTag.toDomain() = Tag(
        id = id,
        koName = name,
        enName = idToEnName[id] ?: "English Name Not Found",
    )

    fun List<LearningRecordTag>.toDomain() = map { it.toDomain() }

    fun StudyTag.toDomain() = TagInfo(
        id = id,
        name = name
    )

    fun List<StudyTag>.toDomain() = map { it.toDomain() }
}
