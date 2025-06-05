package com.ssafy.neegongnaegong.data.repository

import android.util.Log
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesApplicationsMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studies.StudiesMemberMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.vote.VoteMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesApplicationsMembersRequest
import com.ssafy.neegongnaegong.data.model.studies.request.GetStudiesListRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesApplications
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkStudiesDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = TODO()

        override suspend fun createVote(
            studyId: Int,
            voteInfo: VoteInfo,
        ): Flow<Unit> = dataSource.createVote(studyId, voteInfo.toCreateRequest())

        override suspend fun getStudiesList(
            cursorCreatedAt: String?,
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

        override suspend fun deleteStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.deleteStudies(studyGroupId)
            }

        override suspend fun applyStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.applyStudies(studyGroupId)
            }

        override suspend fun cancelApplicationsStudies(studyGroupId: Long): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.cancelApplicationsStudies(studyGroupId)
            }

        override suspend fun getStudiesMembers(studyGroupId: Long): Flow<List<StudiesMember>> =
            withContext(ioDispatcher) {
                dataSource
                    .getStudiesMembers(studyGroupId)
                    .map { dto -> dto.memberRespons.map { it.toDomain() } }
            }

        override suspend fun getStudiesApplications(
            studyGroupId: Long,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorStudiesApplications> =
            withContext(ioDispatcher) {
                dataSource.getStudiesApplications(
                    request =
                        GetStudiesApplicationsMembersRequest(
                            studyGroupId = studyGroupId,
                            cursorId = cursorId,
                            size = size,
                        ),
                )
            }.map { it.toDomain() }
    }
