package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesApplicationsMembersRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesApplicationsMembersResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesFeedsResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesMemberListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesWeeklyRankingsResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface NetworkStudiesDataSource {
    fun createVote(
        studyGroupId: Long,
        requestBody: CreateVoteRequest,
    ): Flow<Unit>

    fun createNotice(
        studyGroupId: Long,
        requestBody: CreateNoticeRequest,
    ): Flow<Unit>

    suspend fun getStudiesList(request: GetStudiesListRequest): Flow<CursorSliceStudiesListResponse>

    suspend fun createStudies(request: CreateStudiesRequest): Flow<Unit>

    suspend fun getStudies(studyGroupId: Long): Flow<StudiesResponse>

    suspend fun updateStudies(
        studyGroupId: Long,
        request: UpdateStudiesRequest,
    ): Flow<Unit>

    suspend fun deleteStudies(studyGroupId: Long): Flow<Unit>

    suspend fun applyStudies(studyGroupId: Long): Flow<Unit>

    suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit>

    suspend fun getStudiesMembers(studyGroupId: Long): Flow<GetStudiesMemberListResponse>

    suspend fun getStudiesApplications(request: GetStudiesApplicationsMembersRequest): Flow<GetStudiesApplicationsMembersResponse>

    suspend fun patchApproveStudiesApplications(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    suspend fun patchRejectStudiesApplications(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    suspend fun changeRoleStudiesMember(
        studyGroupId: Long,
        userId: Long,
        changeRole: String,
    ): Flow<Unit>

    suspend fun expelStudiesMember(
        studyGroupId: Long,
        userId: Long,
    ): Flow<Unit>

    fun getStudiesFeeds(
        studyGroupId: Long,
        cursorCreatedAt: LocalDateTime?,
        cursorId: Long?,
        size: Int,
    ): Flow<GetStudiesFeedsResponse>

    fun getStudiesWeeklyRankings(
        studyGroupId: Long,
        cursorStudyTime: Long?,
        cursorUserId: Long?,
        firstPageRequestedAt: LocalDateTime?,
        size: Int,
    ): Flow<GetStudiesWeeklyRankingsResponse>
}
