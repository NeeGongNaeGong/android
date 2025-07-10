package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.google.gson.annotations.SerializedName
import com.ssafy.neegongnaegong.domain.model.studygroup.Role
import java.time.LocalDate

data class StudyGroupDetailResponse(
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
    @SerializedName("myGrouprole")
    val myGroupRole: Role,
) {
    data class Category(val id: Long, val name: String)

    data class Tag(val id: Long, val name: String)
}
