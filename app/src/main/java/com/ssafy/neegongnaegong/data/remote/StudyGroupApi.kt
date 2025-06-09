package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemRequest
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemsRequest
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("$PREFIX/{study-group-id}/notices/{notices-id}")
    suspend fun getNoticeDetail(
        @Path("study-group-id") studyGroupId: Long,
        @Path("notices-id") noticeId: Long,
    ): Result<ApiResponse<StudyGroupNoticeDetailResponse>>

    @GET("$PREFIX/vote/detail/{vote-id}")
    suspend fun getVoteDetail(
        @Path("vote-id") voteId: Long,
    ): Result<ApiResponse<StudyGroupVoteDetailResponse>>

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
}
