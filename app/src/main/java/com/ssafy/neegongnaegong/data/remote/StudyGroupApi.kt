package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import kotlinx.collections.immutable.PersistentList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

const val PREFIX = "/api/study-groups"

interface StudyGroupApi {
    // 해당 유저의 주간 태그별 학습시간을 반환하는 함수
    @GET("$PREFIX/{studyGroupId}/weekly-study-time/{targetUserId}")
    suspend fun getMemberStudyLogsByTag(
        @Path("studyGroupId") studyGroupId: Long,
        @Path("targetUserId") userId: Long,
    ): Result<ApiResponse<List<StudyLogByTagResponse>>>

    // 해당 유저의 주간 학습내용을 반환하는 함수
    @GET("$PREFIX/{studyGroupId}/feeds/{userId}")
    suspend fun getMemberStudyContents(
        @Path("studyGroupId") studyGroupId: Long,
        @Path("userId") userId: Long,
        @Query("lastCursorCreatedAt") lastCursorCreatedAt: LocalDateTime?,
        @Query("lastLearningRecordId") lastLearningRecordId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>>
}
