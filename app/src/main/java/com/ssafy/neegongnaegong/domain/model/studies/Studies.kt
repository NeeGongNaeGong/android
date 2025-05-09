package com.ssafy.neegongnaegong.domain.model.studies

data class Studies(
    val id: Long,
    val leader: StudyMember,
    val currentMembers: Int,
    val createdDate: String,
    val studyInfo: StudyInfo,
) {
    companion object {
        fun empty() =
            Studies(
                id = -1,
                leader = StudyMember(-1, ""),
                currentMembers = 1,
                createdDate = "",
                studyInfo = StudyInfo.empty(),
            )
    }
}
