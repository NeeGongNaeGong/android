package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class StudyGroupDetailResponse(
    val id: Long,
    val name: String,
    val description: String,
    val profileImg: String,
    val isPublic: Boolean,
    val maxMembers: Int,
    val targetStudyTime: Int,
    val categoryName: String,
    val leaderId: Long,
    val leaderName: String,
    val createdDate: LocalDate,
    val tags: List<Tag>,
    @SerializedName("myGrouprole")
    val myGroupRole: String,
) {
    data class Tag(val id: Long, val name: String)
}
