package com.ssafy.neegongnaegong.domain.repository

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import kotlinx.coroutines.flow.Flow

interface StudyGroupRepository {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>>

    fun getMemberStudyContents(request: StudyMemberInfo): Flow<PagingData<StudyContentInfo>>

    fun getVoteList(request: Long): Flow<PagingData<VoteHistoryInfo>>

    fun getNoticeList(request: Long): Flow<PagingData<NoticeHistoryInfo>>

    fun getNoticeDetail(
        studyGroupId: Long,
        noticeId: Long,
    ): Flow<StudyGroupNoticeDetailInfo>

    fun getVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailInfo>

    fun castVote(
        studyGroupId: Long,
        voteId: Long,
        voteItems: List<String>,
    ): Flow<StudyGroupVoteDetailInfo>

    fun addNewVoteOption(
        studyGroupId: Long,
        voteId: Long,
        voteItem: String,
    ): Flow<StudyGroupVoteDetailInfo>

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
