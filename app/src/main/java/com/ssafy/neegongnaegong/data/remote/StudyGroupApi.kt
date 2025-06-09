package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

const val PREFIX = "/api/study-groups"

interface StudyGroupApi {
    // 해당 유저의 주간 태그별 학습시간을 반환하는 함수
    @GET("$PREFIX/{study-group-id}/members/{user-id}/weekly-study-time")
    suspend fun getMemberStudyLogsByTag(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
    ): Result<ApiResponse<List<StudyLogByTagResponse>>>

    // 해당 유저의 주간 학습내용을 반환하는 함수
    @GET("$PREFIX/{study-group-id}/members/{user-id}/weekly-feeds")
    suspend fun getMemberStudyContents(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("cursor-created-at") cursorCreatedAt: LocalDateTime?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>>

    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/approve")
    suspend fun approveStudyGroupJoin(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?
    ): Result<ApiResponse<Unit>>


    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/reject")
    suspend fun rejectStudyGroupJoin(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?
    ): Result<ApiResponse<Unit>>
}
