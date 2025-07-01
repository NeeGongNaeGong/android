package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDate

data class MyStudyGroupInfo(
    val id: Long,
    val leader: LeaderInfo,
    val name: String,
    val maxMembers: Int,
    val currentMembers: Int,
    val description: String,
    val profileImg: String,
    val isPublic: Boolean,
    val targetStudyTime: Int,
    val category: CategoryInfo,
    val createdDate: LocalDate,
    val tags: List<TagInfo>,
) {
    data class LeaderInfo(
        val id: Long,
        val name: String,
    )

    data class CategoryInfo(
        val id: Long,
        val name: String,
    )

    data class TagInfo(
        val id: Long,
        val name: String,
    )
}
