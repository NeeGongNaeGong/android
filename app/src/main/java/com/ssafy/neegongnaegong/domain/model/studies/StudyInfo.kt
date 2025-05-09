package com.ssafy.neegongnaegong.domain.model.studies

data class StudyInfo(
    val name: String,
    val maxMembers: Int,
    val description: String,
    val profileImg: String? = null,
    val isPublic: Boolean,
    val targetStudyTime: Int,
    val category: Category? = null,
    val tags: List<Tag>,
) {
    companion object {
        fun empty() =
            StudyInfo(
                name = "",
                maxMembers = 10,
                description = "",
                profileImg = null,
                isPublic = true,
                targetStudyTime = 60 * 60 * 7,
                category = null,
                tags = emptyList(),
            )
    }
}
