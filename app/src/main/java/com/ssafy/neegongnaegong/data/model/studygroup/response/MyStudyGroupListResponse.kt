package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDate
import java.time.LocalDateTime

data class MyStudyGroupListResponse(
    val content: List<MyStudyGroupResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
) {
    data class MyStudyGroupResponse(
        val id: Long,
        val leader: LeaderResponse,
        val name: String,
        val maxMembers: Int,
        val currentMembers: Int,
        val description: String,
        val profileImg: String,
        val isPublic: Boolean,
        val targetStudyTime: Int,
        val category: CategoryResponse,
        val createdDate: LocalDate,
        val tags: List<TagResponse>,
        val cursorCreatedAt: LocalDateTime,
        val cursorId: Long,
    ) {
        data class LeaderResponse(
            val id: Long,
            val name: String,
        )

        data class CategoryResponse(
            val id: Long,
            val name: String,
        )

        data class TagResponse(
            val id: Long,
            val name: String,
        )
    }
}
