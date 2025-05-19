package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.StudyMember
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import java.time.LocalDate

data class StudiesResponse(
    val id: Long,
    val leader: StudyMember,
    val name: String,
    val maxMembers: Int,
    val currentMembers: Int,
    val title: String,
    val description: String,
    val profileImg: String,
    val isPublic: Boolean,
    val targetStudyTime: Int,
    val category: Category,
    val createdDate: LocalDate,
    val tags: List<Tag>,
) {
    fun toDomain(): Studies =
        Studies(
            id = id,
            leader = leader,
            currentMembers = currentMembers,
            createdDate = createdDate.toString(),
            studyInfo =
                StudyInfo(
                    name = name,
                    maxMembers = maxMembers,
                    description = description,
                    profileImg = profileImg,
                    isPublic = isPublic,
                    targetStudyTime = targetStudyTime,
                    category = category,
                    tags = tags,
                ),
        )
}
