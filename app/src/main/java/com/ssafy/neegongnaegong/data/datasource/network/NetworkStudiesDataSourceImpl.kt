package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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

        override suspend fun deleteStudies(studyGroupId: Int): Flow<Unit> =
            apiFlow {
                studiesApi.deleteStudies(studyGroupId)
            }

        override suspend fun applyStudies(studyGroupId: Int): Flow<Unit> =
            apiFlow {
                studiesApi.applyStudies(studyGroupId)
            }

        override suspend fun cancelApplicationsStudies(studyGroupId: Int): Flow<Unit> =
            apiFlow {
                studiesApi.cancelApplicationsStudies(studyGroupId)
            }
    }
