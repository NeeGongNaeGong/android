package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import kotlinx.coroutines.flow.Flow

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>

    suspend fun getStudiesList(): Flow<List<Studies>>

    suspend fun createStudies(studyInfo: StudyInfo): Flow<Unit>

    suspend fun getStudiesDetail(studyGroupId: Long): Flow<Studies>

    suspend fun updateStudies(
        studyGroupId: Long,
        studies: Studies,
    ): Flow<Unit>

    suspend fun deleteStudies(studyGroupId: Long): Flow<Unit>

    suspend fun applyStudies(studyGroupId: Long): Flow<Unit>

    suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit>
}
