package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDate

data class StudyGroupDetailInfo(
    val id: Long,
    val name: String,
    val description: String,
    val profileImg: String,
    val isPublic: Boolean,
    val maxMembers: Int,
    val currentMembers: Int,
    val targetStudyTime: Int,
    val category: Category,
    val leaderId: Long,
    val leaderName: String,
    val createdDate: LocalDate,
    val tags: List<Tag>,
    val myGroupRole: Role,
) {
    data class Category(val id: Long, val name: String)

    data class Tag(val id: Long, val name: String)
}
