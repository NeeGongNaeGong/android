package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val api: StudiesApi,
        private val dataSource: NetworkStudiesDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = api.getStudiesDel()

        override suspend fun getStudiesList(): Flow<List<Studies>> {
            TODO("Not yet implemented")
        }

        override suspend fun createStudies(studyInfo: StudyInfo): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.createStudies(request = CreateStudiesRequest.fromDomain(studyInfo))
            }

        override suspend fun getStudiesDetail(studyGroupId: Long): Flow<Studies> {
            TODO("Not yet implemented")
        }

        override suspend fun updateStudies(
            studyGroupId: Long,
            studies: Studies,
        ): Flow<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteStudies(studyGroupId: Long): Flow<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun applyStudies(studyGroupId: Long): Flow<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit> {
            TODO("Not yet implemented")
        }
    }
