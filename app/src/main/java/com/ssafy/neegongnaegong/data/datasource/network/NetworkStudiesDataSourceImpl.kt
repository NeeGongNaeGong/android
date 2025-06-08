package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesApplicationsMembersRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesApplicationsMembersResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesMemberListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStudiesDataSourceImpl
    @Inject
    constructor(
        private val studiesApi: StudiesApi,
    ) : NetworkStudiesDataSource {
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

        override suspend fun createVote(
            studyId: Int,
            requestBody: CreateVoteRequest,
        ): Flow<Unit> = apiFlow { studiesApi.createVote(studyId, requestBody) }

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
    }
