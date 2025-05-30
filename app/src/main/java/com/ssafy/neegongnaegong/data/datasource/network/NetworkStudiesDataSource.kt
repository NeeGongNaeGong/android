package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import kotlinx.coroutines.flow.Flow

interface NetworkStudiesDataSource {
    suspend fun getStudiesList(request: GetStudiesListRequest): Flow<CursorSliceStudiesListResponse>

    suspend fun createStudies(request: CreateStudiesRequest): Flow<Unit>

    suspend fun getStudies(studyGroupId: Long): Flow<StudiesResponse>

    suspend fun updateStudies(
        studyGroupId: Long,
        request: UpdateStudiesRequest,
    ): Flow<Unit>

    suspend fun deleteStudies(studyGroupId: Int): Flow<Unit>

    suspend fun applyStudies(studyGroupId: Long): Flow<Unit>

    suspend fun cancelApplicationsStudies(studyGroupId: Int): Flow<Unit>
}
