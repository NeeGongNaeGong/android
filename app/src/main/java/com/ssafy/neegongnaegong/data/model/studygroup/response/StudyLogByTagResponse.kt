package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo

data class StudyLogByTagResponse(
    val tagId: Long,
    val tagName: String,
    val totalSeconds: Long
){
    fun toStudyLogByTagInfo() = StudyLogByTagInfo(
        tagName = tagName,
        totalSeconds = totalSeconds
    )
}
