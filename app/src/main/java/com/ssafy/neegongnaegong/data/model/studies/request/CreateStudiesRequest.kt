package com.ssafy.neegongnaegong.data.model.studies.request

import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo

data class CreateStudiesRequest(
    val name: String,
    val maxMembers: Int,
    val description: String,
    val profileImg: String?,
    val isPublic: Boolean,
    val targetStudyTime: Int,
    val category: Long,
    val tags: List<Long>,
) {
    companion object {
        fun fromDomain(studyInfo: StudyInfo) =
            CreateStudiesRequest(
                name = studyInfo.name,
                maxMembers = studyInfo.maxMembers,
                description = studyInfo.description,
                profileImg = studyInfo.profileImg,
                isPublic = studyInfo.isPublic,
                targetStudyTime = studyInfo.targetStudyTime,
                category = studyInfo.category?.id ?: 0,
                tags = studyInfo.tags.map { it.id },
            )
    }
}
