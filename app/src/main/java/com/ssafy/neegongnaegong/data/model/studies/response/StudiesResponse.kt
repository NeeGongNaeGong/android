package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.domain.model.studies.StudyMember
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
    val category: Unit, // TODO : 카테고리 타입
    val createdDate: LocalDate,
    val tags: List<Unit>, // TODO : 태그 지정
)
