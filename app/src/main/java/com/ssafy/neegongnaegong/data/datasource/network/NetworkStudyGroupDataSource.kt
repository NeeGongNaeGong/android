package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeListRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow

interface NetworkStudyGroupDataSource {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagResponse>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsRequest): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>>

    suspend fun getStudyGroupVoteList(request: StudyGroupVoteListRequest): Result<ApiResponse<StudyGroupVoteListBySliceResponse>>

    suspend fun getStudyGroupNoticeList(request: StudyGroupNoticeListRequest): Result<ApiResponse<StudyGroupNoticeListBySliceResponse>>

    fun getStudyGroupNoticeDetail(
        studyGroupId: Long,
        noticeId: Long,
    ): Flow<StudyGroupNoticeDetailResponse>

    fun getStudyGroupVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailResponse>

    fun castVote(
        studyGroupId: Long,
        voteId: Long,
        voteItems: List<String>,
    ): Flow<StudyGroupVoteDetailResponse>

    fun addNewVoteOption(
        studyGroupId: Long,
        voteId: Long,
        voteItem: String,
    ): Flow<StudyGroupVoteDetailResponse>

    fun approveStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    fun rejectStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>
}
