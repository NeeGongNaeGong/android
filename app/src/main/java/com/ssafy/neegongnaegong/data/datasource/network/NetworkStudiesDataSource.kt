package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.StudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import kotlinx.coroutines.flow.Flow

interface NetworkStudiesDataSource {
    suspend fun getStudiesList(): Flow<StudiesListResponse>

    suspend fun createStudies(request: CreateStudiesRequest): Flow<Unit>

    suspend fun getStudies(studyGroupId: Int): Flow<StudiesResponse>

    suspend fun updateStudies(
        studyGroupId: Int,
        request: StudiesRequest,
    ): Flow<Unit>

    suspend fun deleteStudies(studyGroupId: Int): Flow<Unit>

    suspend fun applyStudies(studyGroupId: Int): Flow<Unit>

    suspend fun cancelApplicationsStudies(studyGroupId: Int): Flow<Unit>
}
