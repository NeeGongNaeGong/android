package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MyStudyGroupListResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupDetailResponse
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
import java.time.LocalDateTime

interface NetworkStudyGroupDataSource {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagResponse>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsRequest): Flow<MemberWeeklyStudyContentBySliceResponse>

    suspend fun getStudyGroupVoteList(request: StudyGroupVoteListRequest): Flow<StudyGroupVoteListBySliceResponse>

    fun getStudyGroupNoticeDetail(
        studyGroupId: Long,
        noticeId: Long,
    ): Flow<StudyGroupNoticeDetailResponse>

    fun deleteNoticeDetail(
        studyGroupId: Long,
        noticeId: Long,
    ): Flow<Unit>

    suspend fun getStudyGroupNoticeList(request: StudyGroupNoticeListRequest): Flow<StudyGroupNoticeListBySliceResponse>

    fun getStudyGroupVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailResponse>

    fun deleteVoteDetail(
        studyGroupId: Long,
        voteId: Long,
    ): Flow<Unit>

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

    fun getStudyGroupDetail(studyGroupId: Long): Flow<StudyGroupDetailResponse>

    fun getMyStudyGroupList(
        cursorCreatedAt: LocalDateTime?,
        cursorId: Long?,
        size: Int,
    ): Flow<MyStudyGroupListResponse>

    fun leaveStudyGroup(studyGroupId: Long): Flow<Unit>
}
