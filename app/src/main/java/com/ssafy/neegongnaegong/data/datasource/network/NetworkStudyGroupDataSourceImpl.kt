package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
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
import com.ssafy.neegongnaegong.data.remote.StudyGroupApi
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeListRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkStudyGroupDataSourceImpl
    @Inject
    constructor(
        private val api: StudyGroupApi,
    ) : NetworkStudyGroupDataSource {
        override fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagResponse>> =
            apiFlow {
                api.getMemberStudyLogsByTag(request.studyGroupId, request.targetUserId)
            }

        override suspend fun getMemberStudyContents(request: MemberStudyContentsRequest): Flow<MemberWeeklyStudyContentBySliceResponse> =
            apiFlow {
                api.getMemberStudyContents(
                    request.studyGroupId,
                    request.userId,
                    request.cursorCreatedAt,
                    request.cursorId,
                    request.size,
                )
            }

        override suspend fun getStudyGroupVoteList(request: StudyGroupVoteListRequest): Flow<StudyGroupVoteListBySliceResponse> =
            apiFlow {
                api.getStudyGroupVoteList(
                    studyGroupId = request.studyGroupId,
                    cursorTime = request.cursorTime,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }

        override suspend fun getStudyGroupNoticeList(request: StudyGroupNoticeListRequest): Flow<StudyGroupNoticeListBySliceResponse> =
            apiFlow {
                api.getStudyGroupNoticeList(
                    studyGroupId = request.studyGroupId,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }

        override fun getStudyGroupNoticeDetail(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<StudyGroupNoticeDetailResponse> =
            apiFlow {
                api.getNoticeDetail(
                    studyGroupId = studyGroupId,
                    noticeId = noticeId,
                )
            }

        override fun deleteNoticeDetail(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<Unit> =
            apiFlow {
                api.deleteNoticeDetail(studyGroupId, noticeId)
            }

        override fun getStudyGroupVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailResponse> =
            apiFlow {
                api.getVoteDetail(
                    voteId = voteId,
                )
            }

        override fun deleteVoteDetail(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<Unit> =
            apiFlow {
                api.deleteVoteDetail(studyGroupId, noticeId)
            }

        override fun castVote(
            studyGroupId: Long,
            voteId: Long,
            voteItems: List<String>,
        ): Flow<StudyGroupVoteDetailResponse> =
            apiFlow {
                api.castVote(studyGroupId, voteId, VoteItemsRequest(voteItems))
            }

        override fun addNewVoteOption(
            studyGroupId: Long,
            voteId: Long,
            voteItem: String,
        ): Flow<StudyGroupVoteDetailResponse> =
            apiFlow {
                api.addNewVoteOption(
                    studyId = studyGroupId,
                    voteId = voteId,
                    voteItem = VoteItemRequest(voteItem),
                )
            }

        override fun approveStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                api.approveStudyGroupJoin(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override fun rejectStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                api.rejectStudyGroupJoin(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override fun getStudyGroupDetail(studyGroupId: Long): Flow<StudyGroupDetailResponse> =
            apiFlow {
                api.getStudyGroupDetail(studyGroupId)
            }

        override fun getMyStudyGroupList(
            cursorCreatedAt: String?,
            cursorId: Long?,
            size: Int,
        ): Flow<MyStudyGroupListResponse> =
            apiFlow {
                api.getMyStudyGroupList(
                    cursorCreatedAt = cursorCreatedAt,
                    cursorId = cursorId,
                    size = size,
                )
            }

        override fun leaveStudyGroup(studyGroupId: Long): Flow<Unit> =
            apiFlow {
                api.leaveStudyGroup(studyGroupId)
            }
    }
