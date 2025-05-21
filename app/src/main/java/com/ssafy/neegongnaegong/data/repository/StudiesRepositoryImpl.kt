package com.ssafy.neegongnaegong.data.repository

import android.util.Log
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val api: StudiesApi,
        private val dataSource: NetworkStudiesDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = api.getStudiesDel()

        override suspend fun getStudiesList(
            cursorCreatedAt: LocalDateTime?,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorStudiesPage> =
            withContext(ioDispatcher) {
                dataSource
                    .getStudiesList(
                        GetStudiesListRequest(
                            cursorCreatedAt = cursorCreatedAt,
                            cursorId = cursorId,
                            size = size,
                        ),
                    ).map { slice ->
                        Log.d("StudiesRepositoryImpl", "응답 수신: ${slice.content.size}개 항목")
                        CursorSliceStudiesListResponse(
                            content = slice.content,
                            cursorCreatedAt = slice.cursorCreatedAt,
                            hasNext = slice.hasNext,
                            cursorId = slice.cursorId,
                        ).toDomain()
                    }
            }

        override suspend fun createStudies(studyInfo: StudyInfo): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.createStudies(request = CreateStudiesRequest.fromDomain(studyInfo))
            }

        override suspend fun getStudiesDetail(studyGroupId: Long): Flow<Studies> =
            withContext(ioDispatcher) {
                dataSource.getStudies(studyGroupId).map { it.toDomain() }
            }

        override suspend fun updateStudies(
            studyGroupId: Long,
            studyInfo: StudyInfo,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.updateStudies(
                    studyGroupId = studyGroupId,
                    request = UpdateStudiesRequest.fromDomain(studyInfo),
                )
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
