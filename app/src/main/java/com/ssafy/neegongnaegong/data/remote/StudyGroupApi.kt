package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemRequest
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemsRequest
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MyStudyGroupListResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

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

    @GET("$PREFIX/vote/{study-group-id}")
    suspend fun getStudyGroupVoteList(
        @Path("study-group-id") studyGroupId: Long,
        @Query("cursor-time") cursorTime: LocalDateTime?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<StudyGroupVoteListBySliceResponse>>

    @GET("$PREFIX/{study-group-id}/notices")
    suspend fun getStudyGroupNoticeList(
        @Path("study-group-id") studyGroupId: Long,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<StudyGroupNoticeListBySliceResponse>>

    @GET("$PREFIX/{study-group-id}/notices/{notice-id}")
    suspend fun getNoticeDetail(
        @Path("study-group-id") studyGroupId: Long,
        @Path("notice-id") noticeId: Long,
    ): Result<ApiResponse<StudyGroupNoticeDetailResponse>>

    @DELETE("$PREFIX/{study-group-id}/notices/{notice-id}")
    suspend fun deleteNoticeDetail(
        @Path("study-group-id") studyGroupId: Long,
        @Path("notice-id") noticeId: Long,
    ): Result<ApiResponse<Unit>>

    @GET("$PREFIX/vote/detail/{vote-id}")
    suspend fun getVoteDetail(
        @Path("vote-id") voteId: Long,
    ): Result<ApiResponse<StudyGroupVoteDetailResponse>>

    @DELETE("$PREFIX/{study-group-id}/posts/vote/{vote-id}")
    suspend fun deleteVoteDetail(
        @Path("study-group-id") studyGroupId: Long,
        @Path("vote-id") voteId: Long,
    ): Result<ApiResponse<Unit>>

    @POST("$PREFIX/{study-group-id}/posts/votes/participation/{vote-id}")
    suspend fun castVote(
        @Path("study-group-id") studyGroupId: Long,
        @Path("vote-id") voteId: Long,
        @Body voteItems: VoteItemsRequest,
    ): Result<ApiResponse<StudyGroupVoteDetailResponse>>

    @PUT("$PREFIX/{studyId}/posts/votes/{voteId}/choose")
    suspend fun addNewVoteOption(
        @Path("studyId") studyId: Long,
        @Path("voteId") voteId: Long,
        @Body voteItem: VoteItemRequest,
    ): Result<ApiResponse<StudyGroupVoteDetailResponse>>

    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/approve")
    suspend fun approveStudyGroupJoin(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?,
    ): Result<ApiResponse<Unit>>

    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/reject")
    suspend fun rejectStudyGroupJoin(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?,
    ): Result<ApiResponse<Unit>>

    @GET("$PREFIX/{study-group-id}")
    suspend fun getStudyGroupDetail(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<StudyGroupDetailResponse>>

    @GET("$PREFIX/my/list")
    suspend fun getMyStudyGroupList(
        @Query("cursor-value") cursorCreatedAt: String?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<MyStudyGroupListResponse>>

    @DELETE("$PREFIX/{study-group-id}/withdrawal")
    suspend fun leaveStudyGroup(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<Unit>>

    companion object {
        private const val PREFIX = "/api/study-groups"
    }
}
