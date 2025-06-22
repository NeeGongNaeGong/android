package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
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
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStudiesDataSourceImpl
    @Inject
    constructor(
        private val studiesApi: StudiesApi,
    ) : NetworkStudiesDataSource {
        override fun createVote(
            studyGroupId: Long,
            requestBody: CreateVoteRequest,
        ): Flow<Unit> = apiFlow { studiesApi.createVote(studyGroupId, requestBody) }

        override fun createNotice(
            studyGroupId: Long,
            requestBody: CreateNoticeRequest,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.createNotice(studyGroupId, requestBody)
            }

        override suspend fun getStudiesList(request: GetStudiesListRequest): Flow<CursorSliceStudiesListResponse> =
            apiFlow {
                studiesApi.getStudiesList(
                    cursorCreatedAt = request.cursorCreatedAt,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }

        override suspend fun createStudies(request: CreateStudiesRequest): Flow<Unit> =
            apiFlow {
                studiesApi.createStudies(request)
            }

        override suspend fun getStudies(studyGroupId: Long): Flow<StudiesResponse> =
            apiFlow {
                studiesApi.getStudies(studyGroupId)
            }

        override suspend fun updateStudies(
            studyGroupId: Long,
            request: UpdateStudiesRequest,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.updateStudies(studyGroupId, request)
            }

        override suspend fun deleteStudies(studyGroupId: Long): Flow<Unit> =
            apiFlow {
                studiesApi.deleteStudies(studyGroupId)
            }

        override suspend fun applyStudies(studyGroupId: Long): Flow<Unit> =
            apiFlow {
                studiesApi.applyStudies(studyGroupId)
            }

        override suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit> =
            apiFlow {
                studiesApi.cancelApplicationsStudies(studyGroupId)
            }

        override suspend fun getStudiesMembers(studyGroupId: Long): Flow<GetStudiesMemberListResponse> =
            apiFlow {
                studiesApi.getStudiesMembers(studyGroupId)
            }

        override suspend fun getStudiesApplications(
            request: GetStudiesApplicationsMembersRequest,
        ): Flow<GetStudiesApplicationsMembersResponse> =
            apiFlow {
                studiesApi.getStudiesApplications(
                    studyGroupId = request.studyGroupId,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }

        override suspend fun patchApproveStudiesApplications(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.patchApproveStudiesApplications(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override suspend fun patchRejectStudiesApplications(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.patchRejectStudiesApplications(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override suspend fun changeRoleStudiesMember(
            studyGroupId: Long,
            userId: Long,
            changeRole: String,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.changeRoleStudiesMember(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    changeRole = changeRole,
                )
            }

        override suspend fun expelStudiesMember(
            studyGroupId: Long,
            userId: Long,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.expelStudiesMember(
                    studyGroupId = studyGroupId,
                    userId = userId,
                )
            }

        override fun getStudiesFeeds(
            studyGroupId: Long,
            cursorCreatedAt: LocalDateTime?,
            cursorId: Long?,
            size: Int,
        ): Flow<GetStudiesFeedsResponse> =
            apiFlow {
                studiesApi.getStudiesFeeds(
                    studyGroupId = studyGroupId,
                    cursorCreatedAt = cursorCreatedAt,
                    cursorId = cursorId,
                    size = size,
                )
            }

        override fun getStudiesWeeklyRankings(
            studyGroupId: Long,
            cursorStudyTime: Long?,
            cursorUserId: Long?,
            firstPageRequestedAt: LocalDateTime?,
            size: Int,
        ): Flow<GetStudiesWeeklyRankingsResponse> =
            apiFlow {
                studiesApi.getStudiesWeeklyRankings(
                    studyGroupId = studyGroupId,
                    cursorStudyTime = cursorStudyTime,
                    cursorUserId = cursorUserId,
                    firstPageRequestedAt = firstPageRequestedAt,
                    size = size,
                )
            }
    }
