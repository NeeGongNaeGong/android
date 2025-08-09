package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.domain.model.studies.Category
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
)
