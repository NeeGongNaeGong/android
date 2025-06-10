package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemRequest
import com.ssafy.neegongnaegong.data.model.studygroup.request.VoteItemsRequest
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.data.remote.StudyGroupApi
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeListInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListInfo
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

  override suspend fun getMemberStudyContents(
      request: MemberStudyContentsInfo,
  ): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>> =
      api.getMemberStudyContents(
          request.studyGroupId,
          request.userId,
          request.cursorCreatedAt,
          request.cursorId,
          request.size,
      )

  override suspend fun getStudyGroupVoteList(
      request: StudyGroupVoteListInfo,
  ): Result<ApiResponse<StudyGroupVoteListBySliceResponse>> =
      api.getStudyGroupVoteList(
          studyGroupId = request.studyGroupId,
          cursorTime = request.cursorTime,
          cursorId = request.cursorId,
          size = request.size,
      )

  override suspend fun getStudyGroupNoticeList(
      request: StudyGroupNoticeListInfo,
  ): Result<ApiResponse<StudyGroupNoticeListBySliceResponse>> =
      api.getStudyGroupNoticeList(
          studyGroupId = request.studyGroupId,
          cursorId = request.cursorId,
          size = request.size,
      )

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

  override fun getStudyGroupVoteDetail(voteId: Long): Flow<StudyGroupVoteDetailResponse> =
      apiFlow {
          api.getVoteDetail(
              voteId = voteId,
          )
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
          api.addNewVoteOption(studyId = studyGroupId, voteId = voteId, voteItem = VoteItemRequest(voteItem))
      }

  override fun approveStudyGroupJoin(
      studyGroupId: Long,
      userId: Long,
      notificationId: Long?
  ): Flow<Unit> = apiFlow {
      api.approveStudyGroupJoin(
          studyGroupId = studyGroupId,
          userId = userId,
          notificationId = notificationId
      )
  }

  override fun rejectStudyGroupJoin(
      studyGroupId: Long,
      userId: Long,
      notificationId: Long?
  ): Flow<Unit> = apiFlow {
      api.rejectStudyGroupJoin(
          studyGroupId = studyGroupId,
          userId = userId,
          notificationId = notificationId
      )
  }
}
