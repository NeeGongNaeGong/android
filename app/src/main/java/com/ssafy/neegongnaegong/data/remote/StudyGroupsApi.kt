package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.domain.studygroup.MemberWeeklyStudyContentBySliceInfo
import com.ssafy.neegongnaegong.domain.studygroup.StudyLogByTagInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PREFIX = "/api/studies-groups"

interface StudyGroupsApi {
    // 해당 유저의 주간 태그별 학습시간을 반환하는 함수
    @GET("$PREFIX/{studyGroupId}/weekly-study-time/{targetUserId}")
    suspend fun getMemberStudyLogsByTag(
        @Path("studyGroupId") studyGroupId: Int,
        @Path("targetUserId") userId: Int,
    ): List<StudyLogByTagInfo>

    // 해당 유저의 주간 학습내용을 반환하는 함수
    @GET("$PREFIX/{studyGroupId}/feeds/{userId}")
    suspend fun getMemberStudyContents(
        @Path("studyGroupId") studyGroupId: Long,
        @Path("userId") userId: Long,
        @Query("lastCursorCreatedAt") lastCursorCreatedAt: Long,
        @Query("lastLearningRecordId") lastLearningRecordId: Long,
        @Query("size") size: Int,
    ): List<MemberWeeklyStudyContentBySliceInfo>
}
